/**
 * tutorial about  bar charts, and grouped Barcharts
 */
package it.flaviamagnoni.mpandroidchart_t;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = findViewById(R.id.barChart);
        /**
         * example of bar chart with two datasets
         */
        /**
        BarDataSet barDataSet1 = new BarDataSet(dataValue1(), "Dataset 1");
        BarDataSet barDataSet2 = new BarData(dataValue2(), "Dataset 2");
        barDataSet1.setColor(Color.DKGRAY);
        barDataSet2.setColor(Color.CYAN);
        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);
        barData.addDataSet(barDataSet2);
        */

        /**
         * example of grouped barcharts
         */
        BarDataSet barDataSet1 = new BarDataSet(barEntries1(), "DataSet 1");
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(), "DataSet 2");
        BarDataSet barDataSet3 = new BarDataSet(barEntries3(), "DataSet 3");
        BarDataSet barDataSet4 = new BarDataSet(barEntries4(), "DataSet 4");
        BarDataSet barDataSet5 = new BarDataSet(barEntries5(), "DataSet 5");
        BarDataSet barDataSet6 = new BarDataSet(barEntries6(), "DataSet 6");

        barDataSet1.setColor(Color.BLACK);
        barDataSet2.setColor(Color.BLUE);
        barDataSet3.setColor(Color.GRAY);
        barDataSet4.setColor(Color.GREEN);
        barDataSet5.setColor(Color.RED);
        barDataSet6.setColor(Color.YELLOW);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);
        barData.addDataSet(barDataSet2);
        barData.addDataSet(barDataSet3);
        barData.addDataSet(barDataSet4);

        barChart.setData(barData);

        String[] days = new String[]{"Monday", "Tuesday", "Wednesday",  "Thursday", "Friday", "Saturday", "Sunday"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        float barSpace = 0.08f;
        float groupSpace = 0.44f;
        barData.setBarWidth(0.10f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace, barSpace)*7);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.groupBars(0, groupSpace, barSpace);

        barChart.invalidate();
    }

    private ArrayList<BarEntry> dataValue1(){
        ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
        dataVals.add(new BarEntry(0, 3));
        dataVals.add(new BarEntry(1, 4));
        dataVals.add(new BarEntry(2, 6));
        dataVals.add(new BarEntry(4, 11));
        return dataVals;
    }
    private ArrayList<BarEntry> dataValue2(){
        ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
        dataVals.add(new BarEntry(1.8f, 9));
        dataVals.add(new BarEntry(2.6f, 4));
        dataVals.add(new BarEntry(3, 12));
        dataVals.add(new BarEntry(5, 3));
        return dataVals;
    }
    private ArrayList<BarEntry> barEntries1(){
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1, 2000));
        barEntries.add(new BarEntry(2, 792));
        barEntries.add(new BarEntry(3, 900));
        barEntries.add(new BarEntry(4, 630));
        barEntries.add(new BarEntry(5, 2800));
        barEntries.add(new BarEntry(6, 400));
        barEntries.add(new BarEntry(7, 500));
        return barEntries;
    }
    private ArrayList<BarEntry> barEntries2(){
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1, 400));
        barEntries.add(new BarEntry(2, 792));
        barEntries.add(new BarEntry(3, 300));
        barEntries.add(new BarEntry(4, 230));
        barEntries.add(new BarEntry(5, 850));
        barEntries.add(new BarEntry(6, 200));
        barEntries.add(new BarEntry(7, 1800));
        return barEntries;
    }
    private ArrayList<BarEntry> barEntries3(){
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1, 100));
        barEntries.add(new BarEntry(2, 792));
        barEntries.add(new BarEntry(3, 1900));
        barEntries.add(new BarEntry(4, 2630));
        barEntries.add(new BarEntry(5, 800));
        barEntries.add(new BarEntry(6, 600));
        barEntries.add(new BarEntry(7, 300));
        return barEntries;
    }
    private ArrayList<BarEntry> barEntries4(){
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1, 300));
        barEntries.add(new BarEntry(2, 1792));
        barEntries.add(new BarEntry(3, 1900));
        barEntries.add(new BarEntry(4, 2630));
        barEntries.add(new BarEntry(5, 840));
        barEntries.add(new BarEntry(6, 450));
        barEntries.add(new BarEntry(7, 500));
        return barEntries;
    }
    private ArrayList<BarEntry> barEntries5(){
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1, 563));
        barEntries.add(new BarEntry(2, 2749));
        barEntries.add(new BarEntry(3, 567));
        barEntries.add(new BarEntry(4, 2630));
        barEntries.add(new BarEntry(5, 348));
        barEntries.add(new BarEntry(6, 450));
        barEntries.add(new BarEntry(7, 343));
        return barEntries;
    }
    private ArrayList<BarEntry> barEntries6(){
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1, 324));
        barEntries.add(new BarEntry(2, 234));
        barEntries.add(new BarEntry(3, 2431));
        barEntries.add(new BarEntry(4, 645));
        barEntries.add(new BarEntry(5, 130));
        barEntries.add(new BarEntry(6, 931));
        barEntries.add(new BarEntry(7, 231));
        return barEntries;
    }
}
