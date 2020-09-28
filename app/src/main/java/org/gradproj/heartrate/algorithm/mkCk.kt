package org.gradproj.heartrate.algorithm
// 단순 mkck와 mk값을 구해서 wck를 구하는 클래스
class mkCk(i:Int) {

    init{
        mk(i)
        ck(i)
        mkCk(i)
    }
    fun mkCk(i: Int) : Double {
        if(diffR[i]!! > 15){

          return this.ck(i)}

        else return 0.0

    }
    fun mk(i: Int): Int {
        return if(diffR[i]!! > 15) {
            1
        } else 0
    }
    fun ck(i: Int): Double {

        return camera.flashlihgt // 이거 레드 채널 광도일듯?

    }
}