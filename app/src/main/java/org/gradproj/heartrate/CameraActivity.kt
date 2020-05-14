package org.gradproj.heartrate

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import org.gradproj.heartrate.fragment.CameraFragment


class CameraActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            changeFragment(CameraFragment())
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

    private fun changeFragment(cameraFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_content, cameraFragment)
            .commit()
    }
}
