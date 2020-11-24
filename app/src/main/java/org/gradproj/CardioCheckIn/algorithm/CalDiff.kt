package org.gradproj.CardioCheckIn.algorithm

//diffR에 대한 계산과 diffR의 분포인 hI를 계산 - 평가점수 파트 이부분 comp부분을 바꾸면 사용가능
/**
 * file name diff.kt -> CalDiff.kt
*/

class CalDiff(r: Array<Double?>, g: Array<Double?>, b: Array<Double?>) {
    private var maxFinder : MaxValueFinder = MaxValueFinder(r, g, b)
    private var minFinder : MinValueFinder = MinValueFinder(r, g, b)
    private var Aver : CalAvg = CalAvg()

    init{
        cal2()
        cal3()
        cal4()
      }

    fun cal2(){
        diffR[0] = maxR - minR

        hI[0] = 0.0
        for (i in 1..29) {
            t++
            maxFinder.compR()
            minFinder.compR()
            diffR[i]= maxR- minR

            cal()
        }
    }

    fun cal() {
       for(i in 0..29)
       {
           if (diffR[i]==null) break
           else {
               hI[i] = Aver.aver1()* Aver.aver1() + Aver.aver2()
           }
       }
    }//단순계산을 위한 기는ㅇ

    fun cal3(){
        diffR[0] = maxR - minR

        hI[0] = 0.0
        for (i in 1..29) {
            t++
            maxFinder.compG()
            minFinder.compG()
            diffR[i]= maxR- minR

            cal()
        }
    }//g

    fun cal4(){
        diffR[0] = maxR - minR

        hI[0] = 0.0
        for (i in 1..29) {
            t++
            maxFinder.compB()
            minFinder.compB()
            diffR[i]= maxR- minR

            cal()
        }
    }//b
}
