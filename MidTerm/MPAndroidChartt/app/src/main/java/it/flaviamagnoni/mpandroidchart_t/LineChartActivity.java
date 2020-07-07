package it.flaviamagnoni.mpandroidchart_t;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class LineChartActivity extends AppCompatActivity {
    private Holder holder;
    private ArrayList<Integer> hourDataset, temperatureDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        holder = new Holder(this);

        Intent intent = getIntent();
        hourDataset = intent.getIntegerArrayListExtra("hourDataset");
        temperatureDataset = intent.getIntegerArrayListExtra("temperatureDataset");

        createLineChart();
    }

    public void createLineChart() {
        List<Entry> entries = new ArrayList<Entry>();
        int index;
        int hourX, temperatureY;

        for (index = 0; index < hourDataset.size(); index++) {
            hourX = hourDataset.get(index);
            temperatureY = temperatureDataset.get(index);
            entries.add(new Entry(hourX, temperatureY));
        }

        LineDataSet dataSet = new LineDataSet(entries, "");
        //dataSet.setColor();   // Styling
        //dataSet.setValueTextColor();  // Styling

        LineData lineData = new LineData(dataSet);

        holder.createChart(lineData);
    }

    class Holder {
        private Context context;
        private int lineChartActivityColor = getColor(R.color.colBtnLineChart);
        private ConstraintLayout clLineChartActivity;
        private TextView tvLineChartTitle;

        private LineChart lineChart;

        public Holder(Context context) {
            this.context = context;

            clLineChartActivity = findViewById(R.id.clLineChartActivity);
            clLineChartActivity.setBackground(getDrawable(R.drawable.sfondo_chart));
            tvLineChartTitle = findViewById(R.id.tvLineChartTitle);
            tvLineChartTitle.setText(R.string.tvLineChart_title);
            tvLineChartTitle.setBackgroundColor(lineChartActivityColor);

            lineChart = findViewById(R.id.lineChart);
            lineChart.setBackgroundColor(Color.WHITE);
        }

        public void createChart(LineData lineData) {
            lineChart.setData(lineData);
            lineChart.invalidate();
        }
    }
}