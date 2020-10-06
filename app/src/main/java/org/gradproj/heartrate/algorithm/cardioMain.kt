package org.gradproj.heartrate.algorithm

import android.graphics.Camera
import android.util.Log

//메인
val size : Int = 30 // 프레임 사이즈
var t : Int = 0 // 프레임 번호
var tMin: Int = 0 // 최소값을 갖는 프레임
var tMax: Int = 0 // 최대값을 갖는 프레임
var r : Array<Double?> = Array(size) { null} // 빨강영역 프레임 별 강도
var g : Array<Double?> = Array(size) { null} // 초록영역 프레임 별 강도
var b : Array<Double?> = Array(size) { null} // 파랑영역 프레임 별 강도
var diffR : Array<Double?> =  Array(size) { null} // diffR 값
var maxR : Double = 0.0 // diffR 중 최대
var minR : Double = 0.0 // diffR 중 최소
var hI : Array<Double?> = Array (size){null} //diffR의 분포
var s : Double= 0.0 // pr 값이 저장된 곳
var k : Int = 0
var standardValley : Int = 0 // 수축- 확장 최대를 구하기 위한 기준 벨리
var syDi : Array<Double?> = Array(size) {null}

val x : Int = 1280
val y : Int = 720

const val TAG_ALGORITHM = "algorithm test"

class cardioMain {
    var pr : Pr = Pr()
    var sPre : Double = 0.0
    var FLPre : Double = 0.0
    var ISO :Int =550
    var Diff : diff = diff()
    var AssessmentScore : assessmentScore = assessmentScore()
    var camera : Camera = Camera()

    var startOrNot :Boolean = pr.finger()
    fun cardioFun() {
        if (startOrNot) {
            Diff.cal2()
            AssessmentScore

            while (s <= 0.85) {
                sPre = s
                Log.d(TAG_ALGORITHM, s.toString())
                //TODO camera.flashlight 값 Y (luma)로 변경
                var FL: Double = camera.flashlight
                var Feedback = s - sPre

                if (FL - FLPre > 0.85) {
                    FLPre = FL
                    var OffsetFL: Double = Feedback * 0.05
                    FL = FLPre + OffsetFL
                    camera.flashlight = FL
                } else {
                    var OffsetISO: Double = Feedback * 5
                    ISO = ISO + OffsetISO
                    camera.ISO = ISO
                }
            }
        }
        // 임계값이 0.85를 넘으면 프로그램 실행
        if (s>=0.85){
            val Imcy : imcy = imcy()
            val ButterWorth : butterWorth = butterworth()
            syDi = ButterWorth (Imcy.cal())
            val sdf : SDF = SDF()

// SYDI 형상추출부분

            val h1 : Array<Double?> = Array(3){null}
            val t1 : Array<Int?> = Array(4){null}
            val s1 : Array<Double?> = Array(4){null}

            h1[1] = sdf.DN()
            h1[0] = sdf.DP()
            t1[0] = t
            t1[1] = standardValley - t
            h1[2] = sdf.SP()
            t1[2] = t -standardValley
            h1[3] = syDi[size-1]
            t1[3] = size - 1 - t1[2]!!
            s1[0] = h1[0] / t1 [0]!!
            s1[1] = h1[1] - h1[0] / t1 [1]!!
            s1[2] = h1[1] - h1[2] / t1 [2]!!
            s1[3] = h1[3] -h1[2] / t1 [3]!!
        }
    }
}