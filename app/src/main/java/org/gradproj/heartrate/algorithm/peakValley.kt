package org.gradproj.heartrate.algorithm

// 피그-밸리 알고리즘에서 벨리를 먼저 구하고 도출한 프레임 값을 이용하여 수축, 이완기 최대값을 각각 구해야 함
class peak {
    var xi :Array<Double?> = Array(size){null}
    init{
         peakFinder(i1,i2)
         s1()
         s2()
         s3()
    }

    fun xiFinder(){

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
    fun peakFinder(i1: Int, i2: Int) : Boolean {
        if (diffR[i1]!! > this.s1(i1,i2))
        {
            return true
        }
        else return false
    }
}// 실제 피크가 나오는 알고리즘



class valley {
    var xi :Array<Double?> = Array(size){null}
    init{
        valleyFinder(i1,i2)
        s1()
        s1Finder(this.s1())
        s2()
        s3()
    }

    fun xiFinder(){

    }

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

    fun s1Finder(i1: Double): Double {
            var here : Double = i1
              for (i in 0..size-1){
                  if (diffR[i]!! <= here ){
                      standardValley = i
                      here = diffR[i]!! }

              }
            return here
    } // 실제 밸리를 구하는 알고리즘

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
    }
}
