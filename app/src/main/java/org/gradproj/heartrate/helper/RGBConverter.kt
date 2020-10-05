package org.gradproj.heartrate.helper

class RGBConverter {
    fun getRChannel(Y:Double, V:Double): Double {
        var rChannel = 0.0
        rChannel = (PERCENT_Y *(Y-16) + PERCENT_V_TO_R * (V-128))
        return rChannel
    }
    fun getGChannel(Y:Double, V:Double, U:Double): Double {
        var gChannel = 0.0
        gChannel = (PERCENT_Y*(Y - 16) - PERCENT_V_TO_G*(V - 128) - PERCENT_U_TO_G*(U - 128))
        return gChannel
    }
    fun getBChannel(Y:Double, U:Double): Double {
        var bChannel = 0.0
        bChannel = PERCENT_Y*(Y - 16) + PERCENT_U*(U - 128)
        return bChannel
    }

    companion object {
        const val PERCENT_Y = 1.164
        const val PERCENT_V_TO_R = 1.596
        const val PERCENT_V_TO_G = 0.813
        const val PERCENT_U = 2.018
        const val PERCENT_U_TO_G = 0.391
    }
}