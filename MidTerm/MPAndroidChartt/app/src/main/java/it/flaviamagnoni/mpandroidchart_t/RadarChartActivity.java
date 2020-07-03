package it.flaviamagnoni.mpandroidchart_t;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);
        intent = getIntent();
        new Holder();
    }
    public class Holder{
        private RadarChart radarChart;
        private RadarDataSet dataSet;
        private RadarData data;
        private ArrayList<RadarEntry> dataVals;
        private Bundle bundle;
        Holder(){
            radarChart = findViewById(R.id.radarChart);
            bundle = new Bundle(intent.getBundleExtra("bundle"));
            data = new RadarData();
            for(int i = 0; i<bundle.getInt("i"); i++){
                dataVals = new ArrayList<>();
                String getDataSet = "dataSet_" + String.valueOf(i);
                for (int j = 0; j<bundle.getStringArrayList("label").size(); j++){
                    dataVals.add(new RadarEntry((int)bundle.getIntegerArrayList(getDataSet).get(j)));
                }
                String getLabelDataSet = "labelDataSet_" + String.valueOf(i);
                dataSet = new RadarDataSet(dataVals,bundle.getString(getLabelDataSet));
                dataSet.setColor(Color.BLACK);
                data.addDataSet(dataSet);
            }
            XAxis xAxis = radarChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(bundle.getStringArrayList("label")));
            radarChart.setData(data);
            radarChart.invalidate();
        }
    }
}
