package org.gradproj.heartrate

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.TextureView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraXConfig
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import org.gradproj.heartrate.helper.PermissionHelper
import java.lang.Math.*
import java.util.concurrent.Executors
import kotlin.math.absoluteValue

class CameraActivity : AppCompatActivity(), LifecycleOwner, CameraXConfig.Provider{

    val TAG : String = "CAMERA_ACTIVITY"
    val permissionHelper : PermissionHelper = PermissionHelper(this)

    var previewed = false

    // Initialize background executor
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder : TextureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewFinder = findViewById(R.id.textureView)
        viewFinder.post{
            //Build UI control
            // Bind use cases
        }

        if (permissionHelper.permissionCheckable){
            viewFinder.post{startCamera()}
        } else{
            Toast.makeText(this, "권한 허가 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            finish()
        }

        viewFinder.addOnLayoutChangeListener { view, i, i2, i3, i4, i5, i6, i7, i8 ->
            updateTransform()
        }


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
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - AspectRatio.RATIO_4_3.absoluteValue) <= abs(previewRatio - AspectRatio.RATIO_16_9)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    // initializer cameraX
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    // camera open
    private fun startCamera(){

        val preview = Preview.Builder().build()

    }
    private fun updateTransform() {

    }
}
