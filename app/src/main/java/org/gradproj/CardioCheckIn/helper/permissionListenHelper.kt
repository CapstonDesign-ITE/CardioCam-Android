package org.gradproj.CardioCheckIn.helper

import com.gun0912.tedpermission.PermissionListener

class permissionListenHelper(var granted: Boolean) : PermissionListener {

    override fun onPermissionGranted() {

    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        TODO("Not yet implemented")
    }
}