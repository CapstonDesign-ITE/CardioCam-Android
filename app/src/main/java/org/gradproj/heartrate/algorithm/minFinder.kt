package org.gradproj.heartrate.algorithm

// r이 최솟값을 갖는 프레임을찾는 클래스
class minFinder (t : Int){

    var minSub : Double = 0.0

    init{
        comp()

    }
        fun comp()
        { for(i in 0..t) {
            minSub = r[i-1]!!
            if (minR <= minSub) {
                minR = minSub
                tMin = i
            }
        }
        }


}


