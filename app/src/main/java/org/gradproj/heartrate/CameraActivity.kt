package org.gradproj.heartrate

import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.TextureView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import org.gradproj.heartrate.helper.PermissionHelper
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity(), LifecycleOwner, CameraXConfig.Provider{

    val permissionHelper : PermissionHelper = PermissionHelper(this);

    val cameraManager : CameraManager? = null
    val cameraDevice : CameraDevice? = null
    var previewed = false

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder : TextureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewFinder = findViewById(R.id.textureView)

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
