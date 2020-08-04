package org.gradproj.heartrate.helper

import android.content.Context
import android.graphics.ImageFormat
import android.os.Parcel
import android.os.Parcelable
import android.renderscript.*

class ImageAnalyser (val mContext : Context, val width : Int, val height: Int) {

    init {
        val numPixels = width*height
    }

    val rs = RenderScript.create(mContext)
    val yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs))

    val yuvType = Type.Builder(rs, Element.U8(rs)).setX(1280)
    val inData = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT)

    val rgbaType = Type.Builder(rs, Element.RGBA_8888(rs)).setX(1280).setY(720)
    val outData = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT)

}