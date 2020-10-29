package org.gradproj.heartrate.algorithm

import android.graphics.Camera
import android.util.Log
import uk.me.berndporr.iirj.Butterworth

//메인
val size : Int = 30 // 프레임 사이즈
var t : Int = 0 // 프레임 번호
var tMin: Int = 0 // 최소값을 갖는 프레임
var tMax: Int = 0 // 최대값을 갖는 프레임
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

class cardioMain (){
    var sPre : Double = 0.0
    var FLPre : Double = 0.0
    var ISO :Int =550
    var Diff : diff = diff()
    var AssessmentScore : assessmentScore = assessmentScore()
    var Reset : reset = reset()

    var luminosity :Double = 0.0
    var startOrNot :Boolean = true

    val butterworth : Butterworth = Butterworth()

//    var r : Array<Double?> = Array(size) { null} // 빨강영역 프레임 별 강도
//    var g : Array<Double?> = Array(size) { null} // 초록영역 프레임 별 강도
//    var b : Array<Double?> = Array(size) { null} // 파랑영역 프레임 별 강도

    constructor(r: Array<Double?>, g: Array<Double?>, b: Array<Double?>, luma: Double) : this() {
        val pr : Pr = Pr(r,g,b)
        startOrNot = pr.finger()

        // todo order 가 뭐냐 (넣는 배열....의 개수..?)
        butterworth.bandPass(5, 30.0, 5.15, 9.7)

        cardioFunR() // R에 대한 결과 값 계산
        cardioFunG() // G에 대한 결과 값 계산
        cardioFunB() // B에 대한 결과 값 계산 - 셋 다 h,t,s 값에 대한 어레이더블값 리턴
        // 실행할때 cardioFunR부터 실행후 R G B 순으로 실행 추출하시오
    }// 생성자 호출과 동시에 시작

    fun cardioFunR() : Array<Double?> {
        var hR : Array<Double?> = Array(3){null}
        var tR : Array<Int?> = Array(4){null}
        var sR : Array<Double?> = Array(4){null} // 형상추출 R

        Reset.white()
//        if (startOrNot) {
            Diff.cal2()
            AssessmentScore
//
//            while (s <= 0.85) {
//                sPre = s
//                Log.d(TAG_ALGORITHM, s.toString())
//                //TODO camera.flashlight 값 Y (luma)로 변경
//                var FL: Double = luminosity
//                var Feedback = s - sPre
//
//                if (FL - FLPre > 0.85) {
//                    FLPre = FL
//                    var OffsetFL: Double = Feedback * 0.05
//                    FL = FLPre + OffsetFL
//                    camera.flashlight = FL
//                } else {
//                    var OffsetISO: Double = Feedback * 5
//                    ISO = ISO + OffsetISO
//                    camera.ISO = ISO
//                }
//            }
//        }
        // 임계값이 0.85를 넘으면 프로그램 실행
//        if (s>=0.85){
        val Imcy : imcy = imcy()
        var dataDiff : Double = 0.0
    // Band-Pass Filter
        for( i in 0..29){
            dataDiff =Imcy.DiffK.wck(i)

        }
        syDi = arrayOf(butterworth.filter(dataDiff))


        val sdf = SDF()

        // SYDI 형상추출부분
        hR[1] = sdf.DN()
        hR[0] = sdf.DP()
        tR[0] = t
        tR[1] = standardValley - t
        hR[2] = sdf.SP()
        tR[2] = t -standardValley
        hR[3] = syDi[size-1]
        tR[3] = size - 1 - tR[2]!!
        sR[0] = hR[0]!! / tR [0]!!
        sR[1] = hR[1]!! - hR[0]!! / tR [1]!!
        sR[2] = hR[1]!! - hR[2]!! / tR [2]!!
        sR[3] = hR[3]!! - hR[2]!! / tR [3]!!
//        }
        return hR; //tR ; sR
    }


    fun cardioFunG(): Array<Double?> {

        var hG : Array<Double?> = Array(3){null}
        var tG : Array<Int?> = Array(4){null}
        var sG : Array<Double?> = Array(4){null}// 형상추출 G

            Reset.white2()
            Diff.cal3()

            val Imcy : imcy = imcy()
        //todo
//            val butterWorth : Butterworth = Butterworth()
//            syDi = butterWorth(Imcy)
            val sdf : SDF = SDF()

// SYDI 형상추출부분

            hG[1] = sdf.DN()
            hG[0] = sdf.DP()
            tG[0] = t
            tG[1] = standardValley - t
            hG[2] = sdf.SP()
            tG[2] = t -standardValley
            hG[3] = syDi[size-1]
            tG[3] = size - 1 - tG[2]!!
            sG[0] = hG[0]!! / tG [0]!!
            sG[1] = hG[1]!! - hG[0]!! / tG [1]!!
            sG[2] = hG[1]!! - hG[2]!! / tG [2]!!
            sG[3] = hG[3]!! - hG[2]!! / tG [3]!!

            return hG; // tG; sG
    }


    fun cardioFunB(): Array<Double?> {

        var hB : Array<Double?> = Array(3){null}
        var tB : Array<Int?> = Array(4){null}
        var sB : Array<Double?> = Array(4){null}// 형상추출 B

        Reset.white2()
        Diff.cal4()

        val Imcy : imcy = imcy()

        //todo
//            syDi = butterworth (Imcy.cal())
        val sdf : SDF = SDF()

// SYDI 형상추출부분

        hB[1] = sdf.DN()
        hB[0] = sdf.DP()
        tB[0] = t
        tB[1] = standardValley - t
        hB[2] = sdf.SP()
        tB[2] = t -standardValley
        hB[3] = syDi[size-1]
        tB[3] = size - 1 - tB[2]!!
        sB[0] = hB[0]!! / tB [0]!!
        sB[1] = hB[1]!! - hB[0]!! / tB [1]!!
        sB[2] = hB[1]!! - hB[2]!! / tB [2]!!
        sB[3] = hB[3]!! - hB[2]!! / tB [3]!!

        return hB; //tB ; sB
    }
}

