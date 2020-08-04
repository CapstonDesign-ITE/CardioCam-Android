package org.gradproj.heartrate.algorithm

//wck를 계산하기 위한 알고리즘
class diffK {
    var MkCk : mkCk = mkCk()
    var mkSum : Int = 0
    var mkCkSum : Double = 0.0
    init{
        mkFinder()
        mkCkFinder()
    }
    fun mkFinder(): Int {
        for (i in 0..29){
            mkSum += MkCk.mk(i)
        }
        return mkSum
    }
    fun mkCkFinder() :Double {
        for (i in 0..29){
            mkCkSum += MkCk.mkCk(i)
        }
        return mkCkSum
    }
}
