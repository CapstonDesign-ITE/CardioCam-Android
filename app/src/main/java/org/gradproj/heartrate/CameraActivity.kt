package org.gradproj.heartrate

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


class CameraActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_camera_preview)

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
}
