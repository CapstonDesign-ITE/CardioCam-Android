package org.gradproj.CardioCheckIn.algorithm

/**
 * wck를 계산
 * diffK.kt => CalWkCk.kt
 */
class CalWkCk() {

    var mkCk : MkCk = MkCk()
    var mkSum : Int = 0
    var mkCkSum : Double = 0.0
  
    constructor(i:Int) : this() {
        mkFinder(i)
        mkCkFinder(i)
        wck(i)
    }
    fun mkFinder(t1:Int): Double {
        for (i in 0..t1){
            mkSum += mkCk.mk(i)
        }
        return mkSum as Double
    }
    fun mkCkFinder(t1:Int) :Double {
        for (i in 0..t1){
            mkCkSum += mkCk.mkCk(i)
        }
        return mkCkSum
    }
    fun wck (i:Int) : Double {
        if (this.mkFinder(i) != 0.0){
        return this.mkCkFinder(i) / this.mkFinder(i)
        }
        else {return 0.0}
    }
}
