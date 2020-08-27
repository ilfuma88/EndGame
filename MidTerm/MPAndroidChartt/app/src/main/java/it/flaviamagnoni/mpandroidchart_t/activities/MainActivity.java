package it.flaviamagnoni.mpandroidchart_t.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import it.flaviamagnoni.mpandroidchart_t.R;

public class MainActivity extends AppCompatActivity {
    private Activity mActivity;
    private Holder holder;
    private int cntMnuAppInfo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = MainActivity.this;  // Get dell'Activity
        holder = new Holder(this);
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
                if (cntMnuAppInfo == 0) {
                    holder.showPopupWindow(R.id.mnuAppInfo);
                    cntMnuAppInfo += 1;
                }
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

    class Holder implements View.OnClickListener {
        private Context mContext;
        ConstraintLayout clMain;
        ImageView ivTitle;
        Button btnLineChart, btnBarChart, btnRadarChart, btnPieChart;
        // Creo le animazioni per i bottoni e il logo
        Animation transBtnDx = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_btn_dx);
        Animation transBtnSx = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_btn_sx);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        // Per costruire il popup associato al click di "mnuAppInfo"
        private ImageButton ibtnClosePopup;
        private PopupWindow mPopupWindow;

        Holder(Context context){
            mContext = context; // Prendo il context

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
            // Animazioni dei Button
            ivTitle.startAnimation(fadeIn);
            btnLineChart.startAnimation(transBtnSx);
            btnBarChart.startAnimation(transBtnDx);
            btnRadarChart.startAnimation(transBtnSx);
            btnPieChart.startAnimation(transBtnDx);

        }

        public void showPopupWindow(int id) {
            switch (id) {
                case R.id.mnuAppInfo:
                    btnLineChart.setEnabled(false);
                    btnBarChart.setEnabled(false);
                    btnRadarChart.setEnabled(false);
                    btnPieChart.setEnabled(false);

                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);  // Inizializzo una nuova istanza del LayoutInflater service
                    View popupAppInfo = inflater.inflate(R.layout.popup_mnu_app_info, null);    // Gonfio (inflate) il layout "popup_mnu_app_info" all'interno della View popupAppInfo

                    mPopupWindow = new PopupWindow(popupAppInfo, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);   // Inizializzo una nuova istanza di una finestra popup

                    // Imposto un valore di elevation per la finestra di popup (per API >= 21)
                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    mPopupWindow.showAtLocation(clMain, Gravity.CENTER, 0, 0);  // Visualizza la finestra di popup al centro del ConstraintLayout del root

                    ibtnClosePopup = popupAppInfo.findViewById(R.id.ibtnClosePopup);
                    ibtnClosePopup.setOnClickListener(this);
                    break;
            }
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
            if (v.getId() == R.id.ibtnClosePopup) {
                mPopupWindow.dismiss(); // Chiudo la finestra di popup
                btnLineChart.setEnabled(true);
                btnBarChart.setEnabled(true);
                btnRadarChart.setEnabled(true);
                btnPieChart.setEnabled(true);
                cntMnuAppInfo = 0;
            }
        }
    }
}
