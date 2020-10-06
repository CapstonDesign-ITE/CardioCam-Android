//package org.gradproj.heartrate.algorithm
//
//import androidx.camera.core.Camera
//
//class FingerFinder(val camera : Camera){
//    /**
//     * Android Camera2 에서는 camera ISO 참조 불가능 LuminosityAnalyzer 이용하여 우회
//     *
//     * camera flash light or camera ISO
//     * CardioCam Algorithm1
//     */
//
//    val size : Int = 30 // 프레임 사이즈
//    var t : Int = 0 // 프레임 번호
//    var tMin: Int = 0 // 최소값을 갖는 프레임
//    var tMax: Int = 0 // 최대값을 갖는 프레임
//    var r : Array<Double?> = Array(size) { null} // 빨강영역 프레임 별 강도
//    var g : Array<Double?> = Array(size) { null} // 초록영역 프레임 별 강도
//    var b : Array<Double?> = Array(size) { null} // 파랑영역 프레임 별 강도
//    var diffR : Array<Double?> =  Array(size) { null} // diffR 값
//    var maxR : Double = 0.0 // diffR 중 최대
//    var minR : Double = 0.0 // diffR 중 최소
//    var hI : Array<Double?> = Array (size){null} //diffR의 분포
//    var s : Double= 0.0 // pr 값이 저장된 곳
//    var k : Int = 0
//    var standardValley : Int = 0 // 수축- 확장 최대를 구하기 위한 기준 벨리
//
//    val x : Int = 1280
//    val y : Int = 720
//
//    fun passFlashlightScore() : Boolean {
////        var flashlightISO = camera?.cameraInfo.torchState.value
//        var pr : Pr = Pr()
//                var sPre : Double = 0.0
//                var FLPre : Double = 0.0
//                var ISO :Int =550
//
//                var passable : Boolean = false
//
//                var AssessmentScore : assessmentScore = assessmentScore()
//
//                var startOrNot :Boolean = pr.finger()
////                if (startOrNot) {
////                    while (s <= 0.85) {
////                        if (camera.cameraInfo.hasFlashUnit()) {
////                            sPre = s
//////                    var FL: Double = camera.cameraInfo.
////                            var Feedback = s - sPre
////
////                            if (FL - FLPre > 0.85) {
////                                FLPre = FL
////                                var OffsetFL: Double = Feedback * 0.05
////                                FL = FLPre + OffsetFL
//////                        camera.flashlight = FL
////                            } else {
////                                var OffsetISO: Double = Feedback * 5
//////                        ISO = ISO + OffsetISO
//////                        camera.ISO = ISO
////                            }
////                        }
////            }
////            passable = true
////        }
//        return passable
//    }
//}
