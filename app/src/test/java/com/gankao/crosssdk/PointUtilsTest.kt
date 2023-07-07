package com.gankao.crosssdk

import com.gankao.crosssdk.uitls.PointUtils
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 05-30-2023 周二 16:04
 *
 */
class PointUtilsTest {
    @Test
    fun testDistance() {
        val x = 7.0
        val y = 6.0 // 点P的坐标
        val x1 = 0.0
        val y1 = 0.0
        val x2 = 4.0
        val y2 = 4.0 // 线段SE的起点和终点坐标
        val distance: Double = PointUtils.distance(x, y, x1, y1, x2, y2) // 计算点P到线段SE的最短距离
        assertEquals(0.0, distance, 0.001) // 验证计算结果是否正确
    }
}