package org.gradproj.heartrate.algorithm

//평가점수 an을 계산해주는 클래스
class assessmentScore {
    var S : Double = diffR[0]!!

    init{

       An()
        an = S
    }
    fun An() {
        for( i in 1..30 ){

            if (diffR[i-1] == null) {
                break
            }
            if (hI[i-1]!! < 0.0) {
                S += i * i * (hI[i-1]!!.div((x * y)))*-1
            }
            else {
                S += i * i * (hI[i-1]!!.div(x * y))
            }
        }
    }
}