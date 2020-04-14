package org.gradproj.heartrate

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.TextureView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import org.gradproj.heartrate.helper.PermissionHelper
import java.lang.Math.*
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

typealias LumaListener = (luma:Double) -> Unit

class CameraActivity : AppCompatActivity(), LifecycleOwner{

    val TAG : String = "CAMERA_ACTIVITY"
    val permissionHelper : PermissionHelper = PermissionHelper(this)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraExecutor = Executors.newSingleThreadExecutor()
        viewFinder = findViewById(R.id.textureView)
        viewFinder.post{
            //Build UI control
            // Bind use cases
        }

        if (permissionHelper.permissionCheckable){
            Toast.makeText(this, "권한 허용 확인", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, "권한 허가 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        cameraExe
    }

    private fun flashControl(){
        // flash light control
//        val flashEnable : Boolean = camera.cameraInfo.hasFlashUnit()
//        val flashMode : Boolean = CameraInfo.IMPLEMENTATION_TYPE_CAMERA2.
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
