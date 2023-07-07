package com.gankao.crosssdk

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gankao.crosssdk.base.ChatBean
import com.gankao.crosssdk.base.WrongBean
import com.gankao.crosssdk.cus.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 05-20-2023 周六 17:25
 *
 */
class AsyncActivity : AppCompatActivity() {
    private lateinit var chart: BarChart
    private lateinit var bcWrongNumber: BarChart
    private lateinit var pcWork15day: PieChart
    private lateinit var rcAccuracy: RecyclerView

    private val chatList: MutableList<ChatBean> by lazy {
        ArrayList<ChatBean>().apply {
            add(ChatBean(70, "语文"))
            add(ChatBean(80, "数学"))
            add(ChatBean(90, "英语"))
            add(ChatBean(60, "物理"))
            add(ChatBean(50, "化学"))
            add(ChatBean(30, "生物"))
        }
    }
    private val wrongList: MutableList<WrongBean> by lazy {
        ArrayList<WrongBean>().apply {
            add(WrongBean(70, 30, "语文"))
            add(WrongBean(80, 30, "数学"))
            add(WrongBean(90, 30, "英语"))
            add(WrongBean(60, 30, "物理"))
            add(WrongBean(50, 30, "化学"))
            add(WrongBean(30, 30, "生物"))
        }
    }

    private val pcList: MutableList<ChatBean> by lazy {
        ArrayList<ChatBean>().apply {
            add(ChatBean(20, "语文"))
            add(ChatBean(25, "数学"))
            add(ChatBean(30, "英语"))
            add(ChatBean(40, "物理"))
            add(ChatBean(50, "化学"))
            add(ChatBean(60, "生物"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async)
        chart = findViewById(R.id.bc_report_video)
        rcAccuracy = findViewById(R.id.rcAccuracy)
        bcWrongNumber = findViewById(R.id.bc_wrong_number)
        pcWork15day = findViewById(R.id.pc_work_15day)
        initBcChart()
        initBc2Chart()
        initPcChart()
        initRcAccuracyAdapter()

        setData()
        setWrongData()
        set15DayData()
    }

    private fun initRcAccuracyAdapter(){
        rcAccuracy?.apply {
            layoutManager = LinearLayoutManager(this@AsyncActivity)

        }
    }

    private fun initBcChart() {
        //取消缩放、点击、高亮效果
        chart.legend.isEnabled = false
        chart.setScaleEnabled(false)
        chart.isClickable = false
        chart.isHighlightPerDragEnabled = false
        chart.isHighlightPerTapEnabled = false
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)

        chart.description.isEnabled = false

        chart.setMaxVisibleValueCount(10)
        chart.setPinchZoom(false)

        chart.setDrawGridBackground(false)
        val xAxisFormatter: IAxisValueFormatter = DayAxisValueFormatter(chatList)

        val xAxis: XAxis = chart.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day

        xAxis.labelCount = 7
        xAxis.valueFormatter = xAxisFormatter
        xAxis.textColor = Color.WHITE

        val custom: IAxisValueFormatter = MyAxisValueFormatter()

        val leftAxis: YAxis = chart.getAxisLeft()
        leftAxis.setLabelCount(8, false)
        leftAxis.textColor = Color.WHITE
        leftAxis.valueFormatter = custom
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        leftAxis.axisMaximum = 100f


        val rightAxis: YAxis = chart.getAxisRight()
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(8, false)
        rightAxis.textColor = Color.WHITE
        rightAxis.valueFormatter = custom
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis.axisMaximum = 100f


//        val l: Legend = chart.getLegend()
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
//        l.form = LegendForm.SQUARE
//        l.formSize = 9f
//        l.textSize = 11f
//        l.xEntrySpace = 4f
    }

    private fun initBc2Chart() {

        bcWrongNumber.setDrawValueAboveBar(false)
        bcWrongNumber.isHighlightFullBarEnabled = false

        //取消缩放、点击、高亮效果
        bcWrongNumber.legend.isEnabled = false
        bcWrongNumber.setScaleEnabled(false)
        bcWrongNumber.isClickable = false
        bcWrongNumber.isHighlightPerDragEnabled = false
        bcWrongNumber.isHighlightPerTapEnabled = false
        bcWrongNumber.setDrawBarShadow(false)

        bcWrongNumber.description.isEnabled = false

        bcWrongNumber.setMaxVisibleValueCount(10)
        bcWrongNumber.setPinchZoom(false)

        bcWrongNumber.setDrawGridBackground(false)
        val xAxisFormatter: IAxisValueFormatter = WrongAxisValueFormatter(wrongList)

        val xAxis: XAxis = bcWrongNumber.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day

        xAxis.labelCount = 7
        xAxis.valueFormatter = xAxisFormatter
        xAxis.textColor = Color.WHITE

        val custom: IAxisValueFormatter = MyAxisValueFormatter()

        val leftAxis: YAxis = bcWrongNumber.getAxisLeft()
        leftAxis.setLabelCount(8, false)
        leftAxis.textColor = Color.WHITE
        leftAxis.valueFormatter = custom
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
//        leftAxis.axisMaximum = 100f


        val rightAxis: YAxis = bcWrongNumber.getAxisRight()
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(8, false)
        rightAxis.textColor = Color.WHITE
        rightAxis.valueFormatter = custom
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
//        rightAxis.axisMaximum = 100f
    }

    private fun initPcChart() {
        pcWork15day.setUsePercentValues(true)
        pcWork15day.description.isEnabled = false
        pcWork15day.setExtraOffsets(5f, 10f, 5f, 5f)

        pcWork15day.dragDecelerationFrictionCoef = 0.95f

        pcWork15day.setCenterText("赶考666")
        pcWork15day.setDrawEntryLabels(false)

        pcWork15day.setExtraOffsets(30f, 0f, 30f, 0f)

        pcWork15day.setDrawHoleEnabled(true)
        pcWork15day.setHoleColor(Color.TRANSPARENT)

        pcWork15day.setTransparentCircleColor(Color.WHITE)
        pcWork15day.setTransparentCircleAlpha(110)

        pcWork15day.setCenterTextColor(Color.WHITE)

        pcWork15day.setHoleRadius(58f)
        pcWork15day.setTransparentCircleRadius(61f)

        pcWork15day.setDrawCenterText(true)

        pcWork15day.setRotationAngle(0f)
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        pcWork15day.setRotationEnabled(true)
        pcWork15day.isHighlightPerTapEnabled = true

        pcWork15day.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        // chart.spin(2000, 0, 360);
        val l = pcWork15day.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
    }

    private fun setData() {
        val values = ArrayList<BarEntry>()

        chatList.forEachIndexed { index, chatBean ->
            values.add(BarEntry(index * 1f, chatBean.number.toFloat()))
        }
        val set1: BarDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values

            val colors = ArrayList<Int>()
            values.forEach {
                if (it.y > 80) {
                    colors.add(Color.parseColor("#14CE74"))
                } else if (it.y > 60) {
                    colors.add(Color.parseColor("#FFAF37"))
                } else {
                    colors.add(Color.parseColor("#FF5B61"))
                }
            }
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "")
            set1.setDrawIcons(false)
            val colors = ArrayList<Int>()
            values.forEach {
                if (it.y >= 80) {
                    colors.add(Color.parseColor("#14CE74"))
                } else if (it.y >= 60) {
                    colors.add(Color.parseColor("#FFAF37"))
                } else {
                    colors.add(Color.parseColor("#FF5B61"))
                }
            }
            set1.colors = colors
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextColors(colors)

            data.setValueFormatter(MyValueFormatter())
            data.setValueTextSize(12f)
            data.barWidth = 0.8f
            chart.data = data
        }
    }

    private fun setWrongData() {
        val values = java.util.ArrayList<BarEntry>()

        wrongList.forEachIndexed { index, chatBean ->
            values.add(
                BarEntry(
                    index * 1f,
                    floatArrayOf(chatBean.eNumber.toFloat(), chatBean.sNumber.toFloat())
                )
            )
        }

        val set1: BarDataSet

        if (bcWrongNumber.data != null &&
            bcWrongNumber.data.dataSetCount > 0
        ) {
            set1 = bcWrongNumber.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            bcWrongNumber.data.notifyDataChanged()
            bcWrongNumber.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "Statistics Vienna 2014")
            set1.setDrawIcons(false)
            set1.colors = arrayListOf(Color.parseColor("#14CE74"), Color.parseColor("#FFAF37"))
//            set1.stackLabels = arrayOf("Births", "Divorces")
            val dataSets = java.util.ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueFormatter(MyValueFormatter())
            data.setValueTextColor(Color.WHITE)
            bcWrongNumber.data = data
        }

        bcWrongNumber.setFitBars(true)
        bcWrongNumber.invalidate()
    }

    private fun set15DayData() {

        val entries: MutableList<PieEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        pcList.forEachIndexed { index, chatBean ->
            entries.add(
                PieEntry(
                    chatBean.number.toFloat(),
                    chatBean.name,
                    chatBean
                )
            )
        }
        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors = java.util.ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.3f
        dataSet.valueLinePart2Length = 0.5f

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.valueLineColor = Color.WHITE
        val data = PieData(dataSet)
        data.setValueFormatter(Day15PercentFormatter())
//        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pcWork15day.setData(data)

        // undo all highlights
        pcWork15day.highlightValues(null)
        pcWork15day.invalidate()
    }
}