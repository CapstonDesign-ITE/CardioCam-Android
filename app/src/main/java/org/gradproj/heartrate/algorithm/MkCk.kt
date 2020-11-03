package org.gradproj.heartrate.algorithm
// 단순 mkck와 mk값을 구해서 wck를 구하는 클래스
class MkCk() {
    private var redLuma:Double = 0.0

    // TODO i 를 대입하는 생성자 사용하지 않음
//    constructor(i: Int, redLuma:Double) {
//        this.redLuma = redLuma
//        mk(i)
//        ck(i)
//        mkCk(i)
//    }

    fun mkCk(i: Int) : Double {
        return if(diffR[i]!! > 15){
            this.ck(i)
        } else 0.0
    }
    fun mk(i: Int): Int {
        return if(diffR[i]!! > 15) {
            1
        } else 0
    }

    // TODO 함수의 필요성 의문
    private fun ck(i: Int): Double {
        return redLuma // 이거 레드 채널 광도일듯?
    }
}