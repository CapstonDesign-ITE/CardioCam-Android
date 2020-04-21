package org.gradproj.heartrate.algorithm


/**
 * 분포계산 클래스
 * 이 형태의 주석으로 초록색으로 변하면 add되었다는 뜻
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