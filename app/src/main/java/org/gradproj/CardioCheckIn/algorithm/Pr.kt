package org.gradproj.CardioCheckIn.algorithm


// 손가락을 인식하는 수식이 구현된 클래스
class Pr// pr 계산
    (r: Array<Double?>, g: Array<Double?>, b: Array<Double?>) {
    var fingerInfor: Double? = null

    init {
        fingerInfor = r[t - 1]!! / (r[t - 1]!! + g[t - 1]!! + b[t - 1]!!)
        finger()
    }


    fun finger(): Boolean{
        return fingerInfor!! >= 0.95
    }
} // red 채널이 95퍼센트가 넘으면 true를 반환