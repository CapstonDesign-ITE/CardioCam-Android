package org.gradproj.heartrate.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import org.gradproj.heartrate.R
import org.gradproj.heartrate.helper.LuminosityAnalyzer
import org.gradproj.heartrate.helper.permissionListenHelper
import java.lang.Math.max
import java.lang.Math.min
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs

typealias LumaListener = (luma: Double) -> Unit

class CameraFragment : Fragment() {
    private lateinit var container : ConstraintLayout
    private lateinit var viewFinder : PreviewView
    private lateinit var broadcastManager: LocalBroadcastManager

    private var displayId : Int = -1
    private var lensFacing : Int = CameraSelector.LENS_FACING_BACK
    private var preview : Preview? = null
    private var imageCapture : ImageCapture? = null
    private var imageAnalyzer : ImageAnalysis? = null
    private var camera : Camera? = null

    private lateinit var cameraControl : CameraControl
    private lateinit var cameraInfo : CameraInfo

    private lateinit var cameraExecutor : ExecutorService
    private var cameraStarted = false

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.layout_camera_preview, container, false)

        val btnTorchOn = rootView.findViewById<Button>(R.id.btn_start_video)
        btnTorchOn.setOnClickListener {
            toggleTorch()
        }

        return rootView
    }

    private val displayListener = object : DisplayManager.DisplayListener{
        override fun onDisplayAdded(displayId: Int)  = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int)= view?.let{ view ->
            if(displayId == this@CameraFragment.displayId) {
                Log.d(TAG, "Rotation changed : ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        } ?: Unit
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        cameraStarted = false

        cameraExecutor.shutdown()
        displayManager.unregisterDisplayListener(displayListener)

        if(cameraInfo.torchState.value == TorchState.ON){
            cameraControl.enableTorch(false)
        }
        super.onDestroy()
    }

//    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        container = view as ConstraintLayout
        viewFinder = container.findViewById(R.id.preview)
        cameraExecutor = Executors.newSingleThreadExecutor()

        broadcastManager = LocalBroadcastManager.getInstance(view.context)

        displayManager.registerDisplayListener(displayListener, null)

        viewFinder.post {
            displayId = viewFinder.display.displayId

            updateCameraUi()
            bindCameraUseCases()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateCameraUi()
    }

    private fun bindCameraUseCases(){
        val metrics = DisplayMetrics().also{
            viewFinder.display.getRealMetrics(it)
        }

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = viewFinder.display.rotation

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()

            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }
            cameraProvider.unbindAll()

            try {
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider(camera?.cameraInfo))
            }catch (exc : Exception){
                Log.e(TAG,"Use case binding failed",exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun aspectRatio(width:Int, height: Int):Int{
        val previewRatio = max(width,height).toDouble()/ min(width,height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return  AspectRatio.RATIO_16_9
    }

    private fun updateCameraUi(){
        container.findViewById<ConstraintLayout>(R.id.camera_layout)?.let {
            container.removeView(it)
        }

        val controls = View.inflate(requireContext(), R.layout.layout_camera_preview, container)
        controls.findViewById<Button>(R.id.btn_start_video)
    }

    private fun toggleTorch(){
        cameraInfo = camera?.cameraInfo!!
        cameraControl = camera?.cameraControl!!

        if(cameraInfo.torchState.value == TorchState.ON){
            cameraControl.enableTorch(false)
            cameraStarted = true
            Toast.makeText(requireContext(), "심박 데이터 추출이 완료되었습니다",Toast.LENGTH_SHORT)
                .show()
        }else{
            cameraControl.enableTorch(true)
            Toast.makeText(requireContext(), "심박 데이터 추출이 완료될 때까지\n손을 떼지 말아주세요",Toast.LENGTH_SHORT)
                .show()
            cameraStarted = false
        }
    }

    companion object{
        private const val TAG = "CameraFragment"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }
}
