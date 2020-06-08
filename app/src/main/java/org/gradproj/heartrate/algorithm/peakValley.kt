package org.gradproj.heartrate.algorithm

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
}



class valley {
    var xi :Array<Double?> = Array(size){null}
    init{
        valleyFinder(i1,i2)
        s1()
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
                    min1 = diffR[i]
                }
            }
        }
        for (i in i1-i2-1..i1-1){
            if (min2 != null) {
                if (min2 > diffR[i]!!){
                    min2 = diffR[i]
                }
            }
        }
        return (min1!! + min2!!) / 2
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
    fun valleyFinder(i1: Int, i2: Int) : Boolean {
        if (diffR[i1]!! < this.s1(i1,i2))
        {
            return true
        }
        else return false
    }
}
