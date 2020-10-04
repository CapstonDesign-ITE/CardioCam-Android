package org.gradproj.heartrate.algorithm
 // 수축, 이완 특성 계산식 아직 t,h,s에대한 특성을 구하지 않음
class SDF {

    var Valley : valley = valley()
    var Peak : peak = peak()

    init{
        DN()
        DP()
        SP()


    }

    fun DP ():Double {
        return Peak.peakFinder(0, standardValley)
    }


    fun SP ():Double  {
        return Peak.peakFinder(standardValley, size-1)
    }

    fun DN(): Double {

        return  Valley.valleyFinder()
    }
}
