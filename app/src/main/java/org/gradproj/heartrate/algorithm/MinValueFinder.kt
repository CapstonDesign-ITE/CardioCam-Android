package org.gradproj.heartrate.algorithm


/**
 * r이 최솟값을 갖는 프레임을찾는 클래스 - 평가점수 파트
 *
 * minFinder.kt -> MinValueFinder.kt
 */

class MinValueFinder (private val r: Array<Double?>, private val g: Array<Double?>, private val b: Array<Double?>){

    var minSub : Double = 0.0

    init{
        compR()
        compG()
        compB()

    }
        fun compR()
        { for(i in 0..t) {
            minSub = r[i]!!
            if (minR < minSub) {
                minR = minSub
                tMin = i
            }
        }
        }

    fun compG()
    { for(i in 0..t) {
        minSub = g[i]!!
        if (minR < minSub) {
            minR = minSub
            tMin = i
        }
    }
    }
    fun compB()
    { for(i in 0..t) {
        minSub = b[i]!!
        if (minR < minSub) {
            minR = minSub
            tMin = i
        }
    }
    }

}


