package org.gradproj.heartrate.algorithm


/**
 * 분포계산 클래스
 * 이 형태의 주석으로 초록색으로 변하면 add되었다는 뜻
 * 푸시완료했으니까 확인하러가보자고
 * 그리고 지금 이거 내가 계속 치ㅣ까 수정되서 옆에보면 파일 이름 파란색으로바꼈자나
 * 파일 수정한건 파란색 추가한건 초록색 추가안된건 빨간색
 */

class aver() {


    init {
        aver1()
        aver2()
    }

    fun aver1(): Double {
        var sum: Double = 0.0
        var k: Int = 0
        for (i in 1..30) {
            if (diffR[i-1] != null) {
                sum += diffR[i-1]!!
            } else {
                k = i
                break
            }

        }
        return sum / k.toDouble()
    }

    fun aver2(): Double {
        var sum: Double = 0.0
        var k: Int = 0
        for (i in 1..30) {
            if (diffR[i-1] != null) {
                sum += diffR[i-1]!!
            } else {
                k = i
                break
            }

        }
        return sum* sum / k.toDouble()
    }
}