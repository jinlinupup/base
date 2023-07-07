package com.gankao.crosssdk.cus;

import com.gankao.crosssdk.base.ChatBean;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter
{


    private final  List<ChatBean> chart;

    public DayAxisValueFormatter(List<ChatBean> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int pos = (int) value;


        return  chart.get(pos).getName();
    }
}
