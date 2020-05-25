package org.gradproj.heartrate.algorithm

import android.graphics.Camera

//메인

var t : Int = 0
var tMin: Int = 0
var tMax: Int = 0
var r : Array<Double?> = Array(30) { null}
var g : Array<Double?> = Array(30) { null}
var b : Array<Double?> = Array(30) { null}
var diffR : Array<Double?> =  Array(30) { null}
var maxR : Double = 0.0
var minR : Double = 0.0
var hI : Array<Double?> = Array (30){null}
var s : Double= 0.0
var k : Int = 0


val x : Int = 1280
val y : Int = 720

fun main() {
    var pr : Pr = Pr()
    var sPre : Double = 0.0
    var FLPre : Double = 0.0
    var ISO :Int =550

    var AssessmentScore : assessmentScore = assessmentScore()
    var camera : Camera = Camera()

    var startOrNot :Boolean = pr.finger()
if (startOrNot) {
    while (s <= 0.85) {

        sPre= s
        var FL :Double = camera.flashlight
        assessmentScore()
        var Feedback = s - sPre

        if (FL-FLPre > 0.85)
        {
            FLPre = FL
            var OffsetFL:Double = Feedback * 0.05
            FL = FLPre + OffsetFL
            camera.flashlight = FL
        }
        else{
            var OffsetISO : Double = Feedback * 5
            ISO = ISO + OffsetISO
            camera.ISO = ISO
        }




    }


 }

}
/**
 * 알고리즘에 카메라에 대한 클래스를 사용하는 것 같은데 내가 구현하는 부분에 안나와있어서
 * 우선 논문에 나온 그대로 작성함
 */

