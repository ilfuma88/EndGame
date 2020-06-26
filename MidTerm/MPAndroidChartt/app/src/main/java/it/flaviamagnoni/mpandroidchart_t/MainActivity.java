package it.flaviamagnoni.mpandroidchart_t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Holder();
    }

    class Holder implements View.OnClickListener{
        ImageView ivTitle;
        Button btnLineChart, btnBarChart, btnCombinedChart, btnPieChart;
        Animation transBtnDx = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_btn_dx);
        Animation transBtnSx = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_btn_sx);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        Holder(){
            btnLineChart = findViewById(R.id.btnLineChart);
            btnBarChart = findViewById(R.id.btnBarChart);
            btnCombinedChart = findViewById(R.id.btnCombinedChart);
            btnPieChart = findViewById(R.id.btnPieChart);
            ivTitle = findViewById(R.id.ivTitle);

            btnLineChart.setOnClickListener(this);
            btnBarChart.setOnClickListener(this);
            btnCombinedChart.setOnClickListener(this);
            btnPieChart.setOnClickListener(this);

            ivTitle.startAnimation(fadeIn);
            btnLineChart.startAnimation(transBtnSx);
            btnBarChart.startAnimation(transBtnDx);
            btnCombinedChart.startAnimation(transBtnSx);
            btnPieChart.startAnimation(transBtnDx);

        }

        @Override
        public void onClick(View v) {
            if (v.getId()== R.id.btnPieChart){
                Intent i = new Intent(MainActivity.this, PieChart.class);
                startActivity(i);
            }
        }

    }
}
