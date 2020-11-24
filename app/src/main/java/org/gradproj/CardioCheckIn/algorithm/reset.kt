package org.gradproj.CardioCheckIn.algorithm
//R,B,G 제외한 모든 함수 초기화

class reset() {
    init {
        white()
        white2()
    }

    fun white() {
         t  = 0 // 프레임 번호
         tMin = 0 // 최소값을 갖는 프레임
         tMax = 0 // 최대값을 갖는 프레임
         diffR =  Array(frameSize) { null} // diffR 값
         maxR  = 0.0 // diffR 중 최대
         minR  = 0.0 // diffR 중 최소
         hI  = Array (frameSize){null} //diffR의 분포
         s = 0.0 // pr 값이 저장된 곳
         k = 0
         standardValley  = 0 // 수축- 확장 최대를 구하기 위한 기준 벨리
         syDi  = Array(frameSize) {null}
    }
    fun white2() {
        t  = 0 // 프레임 번호
        tMin = 0 // 최소값을 갖는 프레임
        tMax = 0 // 최대값을 갖는 프레임
        diffR =  Array(frameSize) { null} // diffR 값
        maxR  = 0.0 // diffR 중 최대
        minR  = 0.0 // diffR 중 최소
        k = 0
        standardValley  = 0 // 수축- 확장 최대를 구하기 위한 기준 벨리
        syDi  = Array(frameSize) {null}
    }
}