package org.gradproj.heartrate.algorithm


// 손가락을 인식하는 수식이 구현된 클래스
class Pr {
    var fingerInfor :Double? = null

    init{

         fingerInfor = PrCal(t)
        finger()
    }
    fun PrCal( t: Int) : Double {


        return r[t-1]!! / (r[t-1]!! + g[t-1]!! + b[t-1]!!) // pr계산
    }

    fun finger(): Boolean{
        if (fingerInfor!! >= 0.95){
            return true
        }
        else return false
    }
} // red 채널이 95퍼센트가 넘으면 true를 반환