package org.gradproj.heartrate.algorithm

//평가점수 an을 계산해주는 클래스 - 평가점수 파트
class assessmentScore {
    var an : Double = diffR[0]!!

    init{
       An()
        s = an
    }
    fun An() {
        for( i in 1..30 ){

            if (diffR[i-1] == null) {
                break
            }
            if (hI[i-1]!! < 0.0) {
                 an += i * i * (hI[i-1]!!.div((x * y)))*-1
            }
            else {
                an += i * i * (hI[i-1]!!.div(x * y))
            }
        }
    }
}