
package com.gankao.crosssdk.cus;

import android.util.Log;

import com.gankao.crosssdk.base.ChatBean;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.List;

/**
 * This IValueFormatter is just for convenience and simply puts a "%" sign after
 * each value. (Recommeded for PieChart)
 *
 * @author Philipp Jahoda
 */
public class Day15PercentFormatter implements IValueFormatter
{

    protected DecimalFormat mFormat;

    public Day15PercentFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    // IValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        Log.e("getFormattedValue", "getFormattedValue: "+dataSetIndex);
        return ((ChatBean)entry.getData()).getName() + " "+ mFormat.format(value) + " %";
    }


    public int getDecimalDigits() {
        return 1;
    }
}
