package org.gradproj.heartrate

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.TextureView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.lang.Math.*
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue


typealias LumaListener = (luma:Double) -> Unit

class CameraActivity : AppCompatActivity(){

    val TAG : String = "CAMERA_ACTIVITY"

    var previewed = false

    // Initialize background executor
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder : TextureView

    private var camera: Camera? = null
    private var displayId : Int = -1
    private var preview :Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    private lateinit var cameraExecutor : ExecutorService

    private val displayManager by lazy {
        this.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_camera_preview)

        cameraExecutor = Executors.newSingleThreadExecutor()
        viewFinder = findViewById(R.id.textureView)
        viewFinder.post{
            //Build UI control
            // Bind use cases
            }

        val permissionListener: PermissionListener =
            object : PermissionListener {
                override fun onPermissionGranted() {
                    Toast.makeText(this@CameraActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
                }
                override fun onPermissionDenied(deniedPermissions: List<String?>) {
                    Toast.makeText(
                        this@CameraActivity, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
                }
            }
        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("권한 허용 거부시 기능을 정상적으로 이용할 수 없습니다.")
            .setPermissions(Manifest.permission.CAMERA)
            .check()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        updateCameraUI()
    }

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = viewFinder.let { view ->
            if (displayId == this@CameraActivity.displayId) {
                Log.d(TAG, "Rotation changed: ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        cameraExe
    }

    private fun flashControl(){
        val flashEnable : Boolean? = camera?.cameraInfo?.hasFlashUnit()
        if (flashEnable!!){
            preview
        }
//        if(flashMode == Im) imageCapture.flashMode = ImageCapture.FLASH_MODE_OFF
//        else imageCapture.flashMode = ImageCapture.FLASH_MODE_ON
    }

    private fun bindCameraUseCases(){
        val metrics = DisplayMetrics().also {
            viewFinder.display.getRealMetrics(it)
        }
        Log.d(TAG, "${metrics.widthPixels} x ${metrics.heightPixels}" )

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        // initial rotation
        val rotation = viewFinder.display.rotation

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(applicationContext)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()

            // preview
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
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer{luma ->
                        //Todo 코드 추가하기
                    })
                }
            cameraProvider.unbindAll()

            try {
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture,imageAnalyzer)

//                preview?.setSurfaceProvider { viewFinder.create }
            }catch (e:Exception){
                Log.e(TAG, "Use case binding failed",e)
            }
        }, ContextCompat.getMainExecutor(applicationContext))
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - AspectRatio.RATIO_4_3.absoluteValue) <= abs(previewRatio - AspectRatio.RATIO_16_9)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private class LuminosityAnalyzer(listener: LumaListener? = null) : ImageAnalysis.Analyzer{
        private val frameRateWindow = 8;
        private val frameTimestamps = ArrayDeque<Long>(5)
        private val listeners = ArrayList<LumaListener>().apply {
            listener?.let { add(it) }
        }
        private var lastAnalyzedTimestamp = 0L
        var framesPerSecond: Double = -1.0
        private set

        fun onFrameAnalyzed(listener: LumaListener) = listeners.add(listener)

        private fun ByteBuffer.toByteArray(): ByteArray{
            rewind()
            val data = ByteArray(remaining())
            get(data)
            return data
        }

        override fun analyze(image: ImageProxy) {
            if (listeners.isEmpty()){
                image.close()
                return
            }

            // keep track of frames analyzed
            val currentTime = System.currentTimeMillis()
            frameTimestamps.push(currentTime)

            // Compute the FPS using a moving average
            while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
            val timestampFirst = frameTimestamps.peekFirst() ?: currentTime
            val timestampLast = frameTimestamps.peekLast() ?: currentTime
            framesPerSecond = 1.0 / (timestampFirst - timestampLast) /
                    frameTimestamps.size.coerceAtLeast(1).toDouble() * 1000.0

            lastAnalyzedTimestamp = frameTimestamps.first

            val buffer = image.planes[0].buffer

            // image from callback object
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0XFF }

            // avg luminance for the image
            val luma = pixels.average()
            listeners.forEach { it(luma) }

            image.close()
        }
    }
}
