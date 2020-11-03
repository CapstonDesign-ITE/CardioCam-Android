package org.gradproj.heartrate.algorithm

// r이 최댓값을 갖는 프레임을찾는 클래스 - 평가점수 파트 여기 r 값을 g나 b로 바꾸면 그대로 사용가능
class maxFinder (private val r: Array<Double?>, private val g: Array<Double?>, private val b: Array<Double?>) {
    var maxSub : Double= 0.0
    
    init{
         compR()
         compG()
         compB()

    }
    fun compR(){
        for(i in 0..t) {
            maxSub = r[i]!!
        if (maxR < maxSub) {
            maxR = maxSub
            tMax = i
        }

        }
    }
    fun compG(){
        for(i in 0..t) {
            maxSub = g[i]!!
            if (maxR < maxSub) {
                maxR = maxSub
                tMax = i
            }

        }
    }
    fun compB(){
        for(i in 0..t) {
            maxSub = b[i]!!
            if (maxR < maxSub) {
                maxR = maxSub
                tMax = i
            }

        }
    }
}

