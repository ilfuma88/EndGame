package it.flaviamagnoni.mpandroidchart_t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        new Holder();
    }

    Integer value;
    String label;

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

        EditText etValue, etLabel, etChangeText;
        Button btnUpdate, btnClear, btnChangeText;
        TextView tvNewData;

        //Creo un array di colori che andranno poi associati ai dati tramite il metodo setColors
        Resources res = getResources();

        int[] colorClassArray = new int[]{res.getColor(R.color.col_1_chart), res.getColor(R.color.col_2_chart),
                res.getColor(R.color.col_3_chart), res.getColor(R.color.col_4_chart),
                res.getColor(R.color.col_5_chart), res.getColor(R.color.col_6_chart),
                res.getColor(R.color.col_7_chart), res.getColor(R.color.col_8_chart),
                res.getColor(R.color.col_9_chart), res.getColor(R.color.col_10_chart)};

        Holder(){
            pieChart = findViewById(R.id.pieChart);
            etValue = findViewById(R.id.etValue);
            etLabel = findViewById(R.id.etLabel);
            btnUpdate = findViewById(R.id.btnUpdate);
            btnClear = findViewById(R.id.btnClearChart);
            btnChangeText = findViewById(R.id.btnChangeText);
            tvNewData = findViewById(R.id.tvNewData);
            etChangeText = findViewById(R.id.etChangeText);

            etValue.setOnEditorActionListener(this);
            etLabel.setOnEditorActionListener(this);
            etChangeText.setOnEditorActionListener(this);
            etChangeText.setVisibility(View.INVISIBLE);
            btnUpdate.setOnClickListener(this);
            btnClear.setOnClickListener(this);
            btnChangeText.setOnClickListener(this);

            etValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLabel.setInputType(InputType.TYPE_CLASS_TEXT);

            //Passo 2
            PieDataSet pieDataSet = new PieDataSet(dataValues1(), "");

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
            }
            if (v.getId() == R.id.btnClearChart){
            }
            if (v.getId() == R.id.btnChangeText){
                etChangeText.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE){
//                if (v.getId() == R.id.etValue){
//                    value = Integer.parseInt(etValue.getText().toString());
//                }
//                if (v.getId()==R.id.etLabel)
//                    label = etLabel.getText().toString();
//                    tvNewData.append(" (".concat(etValue.getText().toString().concat(", ").concat(label)).concat(")"));
                if (v.getId() == R.id.etChangeText)
                    pieChart.setCenterText(etChangeText.getText().toString());
                etChangeText.setText("");
            }
            return false;
        }

        void clearChart(){
            //ArrayList<PieEntry> data = new ArrayList<PieEntry>();
            //PieDataSet pieDataSet0 = new PieDataSet(data, "voidChart");
            //PieData pieData0 = new PieData(pieDataSet0);
            //pieChart.setData(pieData0);
            //pieChart.invalidate();
        }
    }

}



