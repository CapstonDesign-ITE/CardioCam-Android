package org.gradproj.heartrate.algorithm

// r이 최댓값을 갖는 프레임을찾는 클래스 - 평가점수 파트
class maxFinder (t: Int) {
    var maxSub : Double= 0.0
    
    init{
         comp()

    }
        fun comp(){
            for(i in 0..t) {
                maxSub =r[i-1]!!
            if (maxR < maxSub) {
                maxR = maxSub
                tMax = i
            }

            }
    }


}

