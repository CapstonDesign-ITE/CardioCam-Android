package org.gradproj.heartrate.helper

import android.graphics.BitmapFactory
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import org.gradproj.heartrate.algorithm.CardioMain
import org.gradproj.heartrate.fragment.LumaListener
import java.nio.ByteBuffer
import java.util.ArrayDeque
import java.util.ArrayList

class LuminosityAnalyzer (listener: LumaListener? = null) : ImageAnalysis.Analyzer {

    val converter = RGBConverter()
    private val frameRateWindow = 8
    private val frameTimestamps = ArrayDeque<Long>(5)
    private val listeners = ArrayList<LumaListener>().apply { listener?.let { add(it) } }
    private var lastAnalyzedTimestamp = 0L
    var framesPerSecond: Double = 30.0

    fun onFrameAnalyzed(listener: LumaListener) = listeners.add(listener)

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array

    }

    override fun analyze(image: ImageProxy) {
        if (listeners.isEmpty()) {
            image.close()
            return
        }

        // Keep track of frames analyzed
        val currentTime = System.currentTimeMillis()
        frameTimestamps.push(currentTime)

        while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
        val timestampFirst = frameTimestamps.peekFirst() ?: currentTime
        val timestampLast = frameTimestamps.peekLast() ?: currentTime

        // 30fps
        framesPerSecond = 1.0 /
                ((timestampFirst - timestampLast) / frameTimestamps.size.coerceAtLeast(1).toDouble())*1000.0

        lastAnalyzedTimestamp = frameTimestamps.first

        // set buffer & buffer byte array
        val bufferY = image.planes[0].buffer // in buffer set Y
        val bufferU = image.planes[1].buffer // buffer set U
        val bufferV = image.planes[2].buffer // buffer set V
        val dataY = bufferY.toByteArray()
        val dataV = bufferV.toByteArray()
        val data = bufferU.toByteArray()

//        val bitmapFile = BitmapFactory.decodeByteArray(data, 0, data.size)

        // Convert the data into an array of pixel values ranging 0-255
        val pixelV = bufferV.toByteArray().map { it.toInt() and 0xFF }
        val pixelY = dataY.map { it.toInt() and 0xFF }
        val pixels:ArrayList<Double> = ArrayList()

        //todo index 에러
//        pixelY.forEachIndexed { index, value ->
//            pixels.add(converter.setRChannel(value, pixelV[index-1]))
//        }

        // YUV 값을 토대로 R 값 추출
        val redAvg = pixels.average()
        Log.d("yuv to Red channel", redAvg.toString())

        // Compute average luminance for the image
        val luminance = pixelY.average()
        Log.w("luminous analyzer fps",framesPerSecond.toString() +"luma : "+ redAvg)

        listeners.forEach { it(luminance)}

        image.close()
    }

}

