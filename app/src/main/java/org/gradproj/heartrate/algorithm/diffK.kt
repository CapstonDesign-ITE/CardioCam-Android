package org.gradproj.heartrate.algorithm

class diffK {
    var MkCk : mkCk = mkCk()
    var mkSum : Int = 0
    var mkCkSum : Int = 0
    init{
        mkFinder()
        mkCkFinder()
    }
    fun mkFinder() {
        for (i in 0..29){
            mkSum += MkCk.mk(i)
        }
    }
    fun mkCkFinder() {
        for (i in 0..29){
            mkCkSum += MkCk.mkCk(i)
        }
    }
}
