package org.gradproj.heartrate.helper

import android.Manifest
import android.app.Activity
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


class PermissionHelper(val activity: Activity) : TedPermission() {
    var permissionCheckable : Boolean = false

    init{
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(
                    activity, "Permission Granted", Toast.LENGTH_SHORT
                ).show()
                permissionCheckable = true
            }

            override fun onPermissionDenied(deniedPermissions: List<String?>) {
                Toast.makeText(
                    activity, "Permission Denied", Toast.LENGTH_SHORT
                ).show()
                permissionCheckable = false
            }
        }

        with(activity)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("권한을 허용해야 모든 기능을 사용할 수 있습니다.")
            .setPermissions(Manifest.permission.CAMERA)
            .check()

    }
}