package org.gradproj.heartrate.algorithm

//diffR에 대한 계산과 diffR의 분포인 hI를 계산

class diff() {

    var MaxFinder : maxFinder = maxFinder(t)
    var MinFinder : minFinder = minFinder(t)
    var Aver : aver = aver()
    init{
        diffR[0] = maxR - minR

        hI[0] = 0.0

        for (i in 1..29) {
            MaxFinder.comp()
            MinFinder.comp()
            diffR[i]= maxR- minR

            cal()

        }

      }
    fun cal() {
       for(i in 1..30)
       {
           if (diffR[i]==null) break
           else {
               hI[i - 1] = Aver.aver1()* Aver.aver1() + Aver.aver2()
           }
       }
    }//단순계산을 위한 기는ㅇ

}
