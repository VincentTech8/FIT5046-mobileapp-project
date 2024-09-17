// Import necessary packages for composing UI elements and creating a bar chart
package com.example.mobileassignment3

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

// Composable function to display a bar chart with provided integer values
@Composable
fun BarChartScreen(intValue1: Int, intValue2: Int, intValue3: Int, intValue4: Int) {
    // Create a list of bar entries representing the data points on the chart
    val barEntries = listOf(
        BarEntry(0f, intValue1.toFloat()),
        BarEntry(1f, intValue2.toFloat()),
        BarEntry(2f, intValue3.toFloat()),
        BarEntry(3f, intValue4.toFloat())
    )

    // Create a data set for the bar chart with the provided bar entries and a label
    val barDataSet = BarDataSet(barEntries, "Nutrients")
    barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

    // Create bar data using the bar data set and set the bar width
    val barData = BarData(barDataSet)
    barData.barWidth = 0.8f

    // Create an AndroidView to display the BarChart
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 50.dp)
            .height(500.dp),
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(listOf("", "", "", ""))
                legend.apply {
                    setDrawInside(false)
                    setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)
                    setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
                    setOrientation(Legend.LegendOrientation.HORIZONTAL)
                    setPadding(0, 0, 0, 20)
                    setYOffset(10f)
                }
                animateY(2000) // Animate chart on the Y-axis
            }
        }
    )
}
