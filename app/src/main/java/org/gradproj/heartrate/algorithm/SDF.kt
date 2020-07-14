package org.gradproj.heartrate.algorithm
 // 수축, 이완 특성 계산식 아직 t,h,s에대한 특성을 구하지 않음
class SDF {

    var Valley : valley = valley()
    var Peak : peak = peak()

    init{
        DN()
        DP(0, standardValley-1)
        SP(standardValley, size-1)


    }

    fun DP (i1:Int,i2:Int):Double {
        return Peak.peakFinder(i1,i2)
    }


    fun SP (i1:Int,i2:Int):Double  {
        return Peak.peakFinder(i1,i2)
    }

    fun DN(): Double {

        return  Valley.s1Finder(Valley.s1(0,size-1))
    }
}
