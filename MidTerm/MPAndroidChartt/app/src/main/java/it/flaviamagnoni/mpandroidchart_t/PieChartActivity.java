package it.flaviamagnoni.mpandroidchart_t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        ArrayList<PieInfo> pInfo = i.getParcelableArrayListExtra("arrayValues");
        setContentView(R.layout.activity_pie_chart);
        ArrayList<PieEntry> data = initData(pInfo);
        new Holder(data);
    }

    ArrayList<PieEntry> initData(ArrayList<PieInfo> arrPieInfo){
        ArrayList<PieEntry> arrPieEntry = new ArrayList<>();
        for (int i = 0; i< arrPieInfo.size(); i++)
            arrPieEntry.add(new PieEntry(arrPieInfo.get(i).getValue(), arrPieInfo.get(i).label));
        return arrPieEntry;
    }

    private ArrayList<PieEntry> dataValues1(){
        ArrayList<PieEntry> dataVals = new ArrayList<PieEntry>();
        dataVals.add(new PieEntry(15, "Lun"));
        dataVals.add(new PieEntry(34, "Mar"));
        dataVals.add(new PieEntry(23, "Mer"));
        dataVals.add(new PieEntry(86, "Gio"));
        dataVals.add(new PieEntry(26, "Ven"));
        dataVals.add(new PieEntry(18, "Sab"));
        dataVals.add(new PieEntry(50, "Dom"));
        return dataVals;
    }

    class Holder implements View.OnClickListener{

        com.github.mikephil.charting.charts.PieChart pieChart;

        Button btnPieNewData, btnPieChangeText;

        //Creo un array di colori che andranno poi associati ai dati tramite il metodo setColors
        Resources res = getResources();

        int[] colorClassArray = new int[]{res.getColor(R.color.col_1_chart), res.getColor(R.color.col_2_chart),
                res.getColor(R.color.col_3_chart), res.getColor(R.color.col_4_chart),
                res.getColor(R.color.col_5_chart), res.getColor(R.color.col_6_chart),
                res.getColor(R.color.col_7_chart), res.getColor(R.color.col_8_chart),
                res.getColor(R.color.col_9_chart), res.getColor(R.color.col_10_chart)};

        Holder(ArrayList<PieEntry> dataVals){
            pieChart = findViewById(R.id.pieChart);
            btnPieNewData = findViewById(R.id.btnPieNewData);
            btnPieChangeText = findViewById(R.id.btnPieChangeText);

            btnPieNewData.setOnClickListener(this);
            btnPieChangeText.setOnClickListener(this);

            //Passo 2
            PieDataSet pieDataSet = new PieDataSet(dataVals, "");

            pieDataSet.setColors(colorClassArray);
            //Passo 3
            PieData pieData = new PieData(pieDataSet);
            //Passo 4
            pieChart.setData(pieData);
            //pieChart.setDescription(null);
            pieChart.setCenterText("My First PieChart");
            pieChart.setCenterTextSize(30);
            //pieChart.setCenterTextRadiusPercent(30);
            pieChart.setEntryLabelColor(Color.BLACK);
            pieChart.invalidate();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnPieNewData){
                startActivity(new Intent(PieChartActivity.this, PieChartInsertDataActivity.class));
            }
            if (v.getId() == R.id.btnPieChangeText){
                pieChart.setCenterText("");
            }
        }

    }

}



