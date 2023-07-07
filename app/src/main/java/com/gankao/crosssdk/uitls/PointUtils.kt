package com.gankao.crosssdk.uitls

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 05-30-2023 周二 16:01
 *
 */
object PointUtils {

    // 计算点P到线段SE((x1, y1), (x2, y2))的最短距离
    fun distance(x: Double, y: Double, x1: Double, y1: Double, x2: Double, y2: Double): Double {
        // 计算向量v = E - S
        val vx = x2 - x1
        val vy = y2 - y1
        // 计算向量u = P - S
        val ux = x - x1
        val uy = y - y1
        // 计算向量v的模长|v|
        val vLength = Math.sqrt(vx * vx + vy * vy)
        // 计算投影长度proj_v(u)
        val projLength = (ux * vx + uy * vy) / vLength
        // 如果投影长度小于0，则点P到线段SE的最短距离为点P到起点S的距离
        if (projLength < 0) {
            return Math.sqrt(ux * ux + uy * uy)
        }
        // 如果投影长度大于|v|，则点P到线段SE的最短距离为点P到终点E的距离
        return if (projLength > vLength) {
            Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2))
        }
        // 否则，点P到线段SE的最短距离为投影长度
        else Math.abs((ux * vy - uy * vx) / vLength)

    }
}