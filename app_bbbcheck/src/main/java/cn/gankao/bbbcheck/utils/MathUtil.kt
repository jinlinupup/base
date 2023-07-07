package cn.gankao.bbbcheck.utils

import java.math.BigDecimal

/**
 * @ClassName MathUtil
 * @Description TODO
 * @Author jinlin
 * @Date 2022/8/2 16:24
 * @Version 1.0
 */
object MathUtil {
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    fun div(v1: Float, v2: Float, scale: Int): Float {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(v1.toDouble().toString())
        val b2 = BigDecimal(v2.toDouble().toString())
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toFloat()
    }

    /**
     * @param x1 第一个点的x
     * @param y1 第一个点的y
     * @param x2 第二个点的x
     * @param y2 第二个点的y
     * @return 两点的最短距离
     */
    fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        return Math.sqrt(Math.pow((x1 - x2).toDouble(), 2.0) + Math.pow((y1 - y2).toDouble(), 2.0))
    }
}