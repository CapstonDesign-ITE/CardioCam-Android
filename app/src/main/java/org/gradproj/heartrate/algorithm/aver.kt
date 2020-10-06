package org.gradproj.heartrate.algorithm


/**
 * 분포계산 클래스
 * 분포계산만을 위한 클래스임 - 평가점수 파트
 */

class aver() {
    init {
        aver1()
        aver2()
    }

    fun aver1(): Double {
        var sum: Double = 0.0
        var k: Int = 0
        for (i in 1..30) {
            if (diffR[i-1] != null) {
                sum += diffR[i-1]!!
            } else {
                k = i
                break
            }

        }
        return sum / k.toDouble()
    }

    fun aver2(): Double {
        var sum: Double = 0.0
        var k: Int = 0
        for (i in 1..30) {
            if (diffR[i-1] != null) {
                sum += diffR[i-1]!!
            } else {
                k = i
                break
            }

        }
        return sum* sum / k.toDouble()
    }
}