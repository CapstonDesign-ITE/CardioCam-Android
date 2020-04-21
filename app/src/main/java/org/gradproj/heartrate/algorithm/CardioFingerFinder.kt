package org.gradproj.heartrate.algorithm

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
var an : Double= 0.0

var x : Int = 1280
var y : Int = 720

fun main() {
    var pr : Pr = Pr()

    var startOrNot :Boolean = pr.finger()
if (startOrNot) {
    while (an <= 0.85) {


    }

 }
}

