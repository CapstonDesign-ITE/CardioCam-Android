package org.gradproj.heartrate.algorithm

//diffR에 대한 계산과 diffR의 분포인 hI를 계산 - 평가점수 파트 이부분 comp부분을 바꾸면 사용가능

class diff() {
    var MaxFinder : maxFinder = maxFinder()
    var MinFinder : minFinder = minFinder()
    var Aver : aver = aver()

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
            MaxFinder.compR()
            MinFinder.compR()
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
            MaxFinder.compG()
            MinFinder.compG()
            diffR[i]= maxR- minR

            cal()
        }
    }//g

    fun cal4(){
        diffR[0] = maxR - minR

        hI[0] = 0.0
        for (i in 1..29) {
            t++
            MaxFinder.compB()
            MinFinder.compB()
            diffR[i]= maxR- minR

            cal()
        }
    }//b
}
