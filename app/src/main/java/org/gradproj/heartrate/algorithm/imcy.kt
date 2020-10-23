package org.gradproj.heartrate.algorithm

class imcy {

    var DiffK: diffK = diffK()
    var saveDif: Array<Double?> = Array(size) {null}

    init{

        cal()

    }

    fun cal () : Array<Double?> {

        for( i in 0..29){
       saveDif[i] = DiffK.wck(i)}
        return saveDif
    }

}