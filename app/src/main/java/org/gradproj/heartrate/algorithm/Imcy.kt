package org.gradproj.heartrate.algorithm

class Imcy {
    var diffK: CalWkCk = CalWkCk()
    private lateinit var saveDif : Array<Double>

    init{
        cal()
    }

    fun cal () : Array<Double> {
        for( i in 0..29){
       saveDif[i] = diffK.wck(i)}
        return saveDif
    }
}