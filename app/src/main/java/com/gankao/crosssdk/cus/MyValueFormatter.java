package com.gankao.crosssdk.cus;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-21-2023 周三 10:01
 */
public class MyValueFormatter implements IValueFormatter {
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return ""+ (int) value;
    }
}
