package it.flaviamagnoni.mpandroidchart_t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        new Holder();
    }


    class Holder implements View.OnClickListener, EditText.OnEditorActionListener{

        com.github.mikephil.charting.charts.PieChart pieChart;

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

        EditText etValue, etLabel;
        Button btnUpdate;

        //Creo un array di colori che andranno poi associati ai dati tramite il metodo setColors
        Resources res = getResources();

        int[] colorClassArray = new int[]{res.getColor(R.color.colMon), res.getColor(R.color.colTue),
                res.getColor(R.color.colWed), res.getColor(R.color.colThu),
                res.getColor(R.color.colFri), res.getColor(R.color.colSat), res.getColor(R.color.colSun)};

        Holder(){
            pieChart = findViewById(R.id.pieChart);
            etValue = findViewById(R.id.etValue);
            etLabel = findViewById(R.id.etLabel);
            btnUpdate = findViewById(R.id.btnUpdate);
            etValue.setOnEditorActionListener(this);
            btnUpdate.setOnClickListener(this);
            //Passo 2
            PieDataSet pieDataSet = new PieDataSet(dataValues1(), "My First PieDataSet");
            pieDataSet.setColors(colorClassArray);
            //Passo 3
            PieData pieData = new PieData(pieDataSet);
            //Passo 4
            pieChart.setData(pieData);
            pieChart.setDescription(null);
            pieChart.setCenterText("My First PieChart");
            pieChart.setCenterTextSize(30);
            //pieChart.setCenterTextRadiusPercent(30);
            pieChart.setEntryLabelColor(Color.BLACK);
            pieChart.invalidate();
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.btnUpdate){
                dataValues1().add(new PieEntry(Integer.parseInt(String.valueOf(Integer.parseInt(etValue.getText().toString()))), etLabel.getText().toString()));
                pieChart.invalidate();
            }

        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                btnUpdate.setClickable(true);
            }
            return false;
        }
    }

}



