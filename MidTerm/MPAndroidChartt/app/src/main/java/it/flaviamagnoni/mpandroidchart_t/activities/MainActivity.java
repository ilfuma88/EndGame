package it.flaviamagnoni.mpandroidchart_t.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import it.flaviamagnoni.mpandroidchart_t.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Holder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuAboutUs:
                Intent intentAboutUs = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intentAboutUs);
                return true;
            case R.id.mnuAppInfo:
                //Intent intentAppInfo = new Intent(this, AppInfo.class);
                //startActivity(intentAppInfo);
                return true;
            case R.id.mnuGithub:
                Intent intentGithub = new Intent(Intent.ACTION_VIEW);
                intentGithub.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart"));
                startActivity(intentGithub);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class Holder implements View.OnClickListener{
        ConstraintLayout clMain;
        ImageView ivTitle;
        Button btnLineChart, btnBarChart, btnRadarChart, btnPieChart;
        Animation transBtnDx = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_btn_dx);
        Animation transBtnSx = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_btn_sx);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        Holder(){
            clMain = findViewById(R.id.clMain);
            clMain.setBackground(getDrawable(R.drawable.sfondo_chart));
            btnLineChart = findViewById(R.id.btnLineChart);
            btnBarChart = findViewById(R.id.btnBarChart);
            btnRadarChart = findViewById(R.id.btnRadarChart);
            btnPieChart = findViewById(R.id.btnPieChart);
            ivTitle = findViewById(R.id.ivTitle);

            btnLineChart.setOnClickListener(this);
            btnBarChart.setOnClickListener(this);
            btnRadarChart.setOnClickListener(this);
            btnPieChart.setOnClickListener(this);

            ivTitle.setImageDrawable(getDrawable(R.drawable.mpandroidchart));
            ivTitle.startAnimation(fadeIn);
            btnLineChart.startAnimation(transBtnSx);
            btnBarChart.startAnimation(transBtnDx);
            btnRadarChart.startAnimation(transBtnSx);
            btnPieChart.startAnimation(transBtnDx);

        }

        @Override
        public void onClick(View v) {
            if (v.getId()== R.id.btnLineChart) {
                Intent intent = new Intent(MainActivity.this, LineChartInsertDataActivity.class);
//                intent.putExtra("LineChartColor", ((ColorDrawable) btnLineChart.getBackground()).getColor()); // Inserisco nel bundle dell'intent il colore che rappresenta il LineChart
                startActivity(intent);
            }
            if (v.getId() == R.id.btnBarChart)
                startActivity(new Intent(MainActivity.this, BarchartInsertDataActivity.class));
            if (v.getId() == R.id.btnRadarChart)
                startActivity(new Intent(MainActivity.this, RadarChartInsertLabelActivity.class));
            if (v.getId()== R.id.btnPieChart){
                startActivity(new Intent(MainActivity.this, PieChartInsertDataActivity.class));
            }
        }
    }
}
