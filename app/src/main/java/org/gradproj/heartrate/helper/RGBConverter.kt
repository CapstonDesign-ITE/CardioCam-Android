package org.gradproj.heartrate.helper

class RGBConverter {

    fun getRChannel(Y:Int, V:Int): Int {
        var rChannel = 0
        rChannel = (Companion.PERCENT_Y *(Y-16) + Companion.PERCENT_V_TO_R * (V-128)).toInt()

        return rChannel
    }

    companion object {
        const val PERCENT_Y = 1.164
        const val PERCENT_V_TO_R = 1.596
        const val PERCENT_U = 2.018
    }
}