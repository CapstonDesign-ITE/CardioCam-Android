package org.gradproj.heartrate.algorithm

// 피그-밸리 알고리즘에서 벨리를 먼저 구하고 도출한 프레임 값을 이용하여 수축, 이완기 최대값을 각각 구해야 함
class peak() {

    var xi :Array<Double?> = Array(size){null}

    constructor (i1:Int, i2: Int) : this() {
        s1(i1,i2)
        s2(i1,i2)
        s3(i1,i2)
        peakFinder(i1,i2)
        localPeakFinder(i1,i2)
    }


    fun s1(i1: Int, i2 : Int): Double {
        var max1 : Double? = diffR[i1-1]
        var max2 : Double? = diffR[i1-1]
        for (i in i1-1..i1+i2-1){
            if (max1 != null) {
                if (max1 < diffR[i]!!){
                        max1 = diffR[i]
                }
            }
        }
        for (i in i1-i2-1..i1-1){
            if (max2 != null) {
                if (max2 < diffR[i]!!){
                    max2 = diffR[i]
                }
            }
        }
            return (max1!! + max2!!) / 2
    }

    fun s2(i1: Int, i2 : Int): Double{
        var sum1 : Double? = null
        var sum2 : Double? = null
        for (i in i1-1..i1+i2-1){
            sum1 =+ diffR[i]!!
            }
        for (i in i1-i2-1..i1-1){
            sum2 =+ diffR[i]!!

        }
            return (sum1!! + sum2!!)/2
        }


    fun s3(i1: Int, i2 : Int): Double{
        var sum1 : Double? = null
        var sum2 : Double? = null
        for (i in i1..i1+i2-1){
            sum1 =+ diffR[i]!!
        }
        for (i in i1-i2-1..i1-2){
            sum2 =+ diffR[i]!!

        }
            return ( (diffR[i1-1]!! - sum1!!/i2) + (diffR[i1-1]!! - sum2!!/i2) )/2
    }
    fun peakFinder(i1: Int, i2: Int) : Double {
        var saveFrame : Int = 0
        var saveMax : Double = 0.0
        for (i in i1-1..i2-1){
        if (diffR[i]!! > this.s1(0,size-1))
         {
            saveMax = diffR[i]!!
             saveFrame = i+1
         }
        }
        if (saveMax == 0.0) {
            saveMax = diffR[i1-1]!!
            for (i in i1-1..i2-1) {
                if (diffR[i]!! >= saveMax) {
                    saveMax = diffR[i]!!
                    saveFrame = i+1
                }
            }
        }
        return saveMax

    }
    fun localPeakFinder(i1: Int,i2: Int) : Array<Double?> {
        var saveLocalPeak : Array<Double?> = Array(size){null}
        var count : Int = 0
        for (i in i1-1..i2-1){
           if (diffR[i]!! > diffR[i-1]!! && diffR[i]!! > diffR[i+1]!!){
               saveLocalPeak[count] = i as Double
               count++
           }
        }
        return saveLocalPeak
    } //로컬피크를 구할때 사용
}// 실제 피크가 나오는 알고리즘



class valley() {
    var xi :Array<Double?> = Array(size){null}

    constructor( i1:Int, i2: Int,i3:Double) : this(){

        valleyFinder(i1,i2) // 해당 구간에 지역벨리가 있는지 찾는 함수
        valleyFinder(i3) // 근사치로 구한 벨리를 기준으로 실제 벨리를 구한다.
        s1(i1,i2)
        s2(i1,i2)
        s3(i1,i2)
        localVelleyFinder(i1,i2)
    }


    fun xiFinder(){

    } // 비워둔것 안씀.

    fun s1(i1: Int, i2 : Int): Double {
        var min1 : Double? = diffR[i1-1]
        var min2 : Double? = diffR[i1-1]
        for (i in i1-1..i1+i2-1){
            if (min1 != null) {
                if (min1 > diffR[i]!!){
                    if (diffR[i]!! > 0)
                    {min1 = diffR[i]}
                }
            }
        }
        for (i in i1-i2-1..i1-1){
            if (min2 != null) {
                if (min2 > diffR[i]!!){
                    if (diffR[i]!! > 0)
                    {min2 = diffR[i]}
                }
            }
        }
        return (min1!! + min2!!) / 2
    }

    fun valleyFinder(i1: Double): Double {
        var here : Double = i1
        for (i in 0..size-1){
            if (diffR[i]!! <= here ){
                standardValley = i
                here = diffR[i]!! }

        }

        if(here == i1){
            here = diffR[0]!!
            for (i in 0..size-1){
                if(diffR[i]!! <= here){
                    standardValley = i
                    here = diffR[i]!!
                }
            }
        }
        return here
    } // 실제 밸리를 구하는 알고리즘, 위 공식으로 구하면 근사치가 나옴

    fun s2(i1: Int, i2 : Int): Double{
        var sum1 : Double? = null
        var sum2 : Double? = null
        for (i in i1-1..i1+i2-1){
            sum1 =+ diffR[i]!!
        }
        for (i in i1-i2-1..i1-1){
            sum2 =+ diffR[i]!!

        }
        return (sum1!! + sum2!!)/2
    }


    fun s3(i1: Int, i2 : Int): Double{
        var sum1 : Double? = null
        var sum2 : Double? = null
        for (i in i1..i1+i2-1){
            sum1 =+ diffR[i]!!
        }
        for (i in i1-i2-1..i1-2){
            sum2 =+ diffR[i]!!

        }
        return ( (diffR[i1-1]!! - sum1!!/i2) + (diffR[i1-1]!! - sum2!!/i2) )/2
    }

    fun valleyFinder(i1: Int, i2: Int) : Boolean {
        if (diffR[i1]!! < this.s1(i1,i2))
        {
            return true
        }
        else return false
    } // 해당 지점에 벨리가 있는지 찾아내는 함수

    fun localVelleyFinder(i1: Int,i2: Int) : Array<Double?> {
        var saveLocalVelley : Array<Double?> = Array(size){null}
        var count : Int = 0
        for (i in i1-1..i2-1){
            if (diffR[i]!! < diffR[i-1]!! && diffR[i]!! < diffR[i+1]!!){
                saveLocalVelley[count] = i as Double
                count++
            }
        }
        return saveLocalVelley
    } //로컬벨리를 구할때 사용
}
