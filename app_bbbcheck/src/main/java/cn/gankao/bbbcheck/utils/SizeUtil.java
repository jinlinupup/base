
package cn.gankao.bbbcheck.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SizeUtil {

    public static float[] getBitmapdpisize(Bitmap bitmap) {
        float densityDPI = 11.811f;
        float real_width = (bitmap.getWidth()) / densityDPI;//像素轉換成物理
        float real_height = (bitmap.getHeight()) / densityDPI;
        float[] f_screen = {real_width, real_height};
        return f_screen;
    }

    public static float[] getC_width(Context context, float bijiwidth, float bijiheight) {

        float width = SizeUtil.getScreenWidth(context);
        float height = SizeUtil.getScreenHeight(context);// - Util.getDimensRes(R.dimen.dp100);
        return getC_width( bijiwidth, bijiheight, width, height);
    }


    public static float[] getC_width(float bijiwidth, float bijiheight, float windrowwidth, float windowheight) {
        float width = windrowwidth;
        float height = windowheight;

        float biji_width = bijiwidth;
        float biji_height = bijiheight;

        float c_width = 0;
        float c_height = 0;
        if (width / height < biji_width / biji_height) {
            c_width = (int) (width);
            c_height = (int) (width * biji_height / biji_width);
        } else {
            c_width = (int) (height * biji_width / biji_height);
            c_height = (int) (height);
        }
        float[] f_screen = {c_width, c_height};
        return f_screen;
    }

    public static boolean strIsNull(String str) {
        if (null != str && !"".equals(str))
            return false;
        return true;
    }

    public static boolean strIsNegativeInteger(String str) {
        if (strIsNull(str))
            return false;
        Pattern p = Pattern.compile("-[1-9]\\d*");
        Matcher m = p.matcher(str);
        if (m.matches())
            return true;
        return false;
    }

    public static boolean strIsPositiveInteger(String str) {
        if (strIsNull(str))
            return false;
        Pattern p = Pattern.compile("[1-9]\\d*");
        Pattern p1 = Pattern.compile("\\+[1-9]\\d*");
        Matcher m = p.matcher(str);
        Matcher m1 = p1.matcher(str);
        if (m.matches() || m1.matches())
            return true;
        return false;
    }

    public static boolean strIsPositiveFloat(String str) {
        if (strIsNull(str))
            return false;
        if (strIsPositiveInteger(str) || strIsNegativeInteger(str))
            return true;
        Pattern p = Pattern.compile("[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*");
        Pattern p1 = Pattern.compile("\\+([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)");
        Matcher m = p.matcher(str);
        Matcher m1 = p1.matcher(str);
        if (m.matches() || m1.matches())
            return true;
        return false;
    }

    public static int getIntByStr(Object num) {

        if (num != null && strIsPositiveFloat(num.toString())) {
            if (strIsPositiveFloat(num.toString())) {
                return (int)Float.parseFloat(num.toString());
            } else {
                return Integer.valueOf(num.toString());
            }
        }
        return 0;
    }


    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 毫米转px
     * @param value
     * @param dpi
     * @return
     */
    public static float mmToPx(float value,float dpi) {
        float inch = value / 25.4f;
        float c_value = (inch * dpi);

        return c_value;
    }

}
