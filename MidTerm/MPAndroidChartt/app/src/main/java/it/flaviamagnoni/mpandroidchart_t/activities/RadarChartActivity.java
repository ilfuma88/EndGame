package it.flaviamagnoni.mpandroidchart_t.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.flaviamagnoni.mpandroidchart_t.R;

public class RadarChartActivity extends AppCompatActivity {
    private Intent intent;
    private Holder holder;
    private int cntMnuLineChartInfo = 0;
    private static final int PERMISSION_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);
        intent = getIntent();
        holder = new Holder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_line_chart_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuAboutUs:
                Intent intentAboutUs = new Intent(RadarChartActivity.this, AboutUsActivity.class);
                startActivity(intentAboutUs);
                return true;
            case R.id.mnuLineChartInfo:
                if (cntMnuLineChartInfo == 0) {
                    holder.showPopupWindow(R.id.mnuLineChartInfo);
                    cntMnuLineChartInfo += 1;
                }
                return true;
            case R.id.mnuGithub:
                Intent intentGithub = new Intent(Intent.ACTION_VIEW);
                intentGithub.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart"));
                startActivity(intentGithub);
                return true;
            case R.id.mnuSavePicture:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery(holder.radarChart, "RadarChart");
                } else {
                    requestStoragePermission(holder.radarChart);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void requestStoragePermission(View view) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(view, "Write permission is required to save image to gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(RadarChartActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
                        }
                    }).show();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Required!", Toast.LENGTH_SHORT)
                    .show();
            ActivityCompat.requestPermissions(RadarChartActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //saveToGallery(holder.lineChart, "LineChart");
                findViewById(R.id.mnuSavePicture).setEnabled(true);
            } else {
                Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    protected void saveToGallery(Chart chart, String name) {
        if (chart.saveToGallery(name + "_" + System.currentTimeMillis(), 70))
            Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                    .show();
    }

    public class Holder implements View.OnClickListener {
        private RadarChart radarChart;
        private RadarDataSet dataSet;
        private RadarData data;
        private ArrayList radarEntries;

        private Bundle bundle;
        private Context mContext;

        private ConstraintLayout clRadarChart;
        private ImageButton ibtnClosePopup;
        private TextView tvPopupLineChartInfo;
        private PopupWindow mPopupWindow;

        Holder(Context context){

            mContext = context;

            clRadarChart = findViewById(R.id.clRadarChart);
            radarChart = findViewById(R.id.radarChart);

            bundle = new Bundle(intent.getBundleExtra("bundle"));
            data = new RadarData();

            for(int i = 0; i<bundle.getInt("i"); i++){
                getEntries(i);
                String getLabelDataSet = "labelDataSet_" + String.valueOf(i);
                dataSet = new RadarDataSet(radarEntries,bundle.getString(getLabelDataSet));
                String color = "color_" + String.valueOf(i);
                dataSet.setColor(getColor(bundle.getInt(color)));
                data.addDataSet(dataSet);
            }

            XAxis xAxis = radarChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(bundle.getStringArrayList("label")));
            radarChart.setData(data);
            radarChart.invalidate();
        }

        protected void getEntries(int i){
            radarEntries = new ArrayList<>();
            String getDataSet = "dataSet_" + String.valueOf(i);
            for (int j = 0; j<bundle.getStringArrayList("label").size(); j++){
                radarEntries.add(new RadarEntry(bundle.getIntegerArrayList(getDataSet).get(j)));
            }
        }

        public void showPopupWindow(int id) {
            switch (id) {
                case R.id.mnuLineChartInfo:
                    radarChart.setEnabled(false);
                    radarChart.setTouchEnabled(false);
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);  // Inizializzo una nuova istanza del LayoutInflater service
                    View popupLineChartInfo = inflater.inflate(R.layout.popup_mnu_linechart_info, null);    // Gonfio (inflate) il layout "popup_mnu_app_info" all'interno della View popupLineChartInfo

                    mPopupWindow = new PopupWindow(popupLineChartInfo, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);   // Inizializzo una nuova istanza di una finestra popup

                    // Imposto un valore di elevation per la finestra di popup (per API >= 21)
                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    mPopupWindow.showAtLocation(clRadarChart, Gravity.CENTER, 0, 0);  // Visualizza la finestra di popup al centro del ConstraintLayout del root

                    ibtnClosePopup = popupLineChartInfo.findViewById(R.id.ibtnClosePopup);
                    ibtnClosePopup.setOnClickListener(this);
                    tvPopupLineChartInfo = popupLineChartInfo.findViewById(R.id.tvPopupLineChartInfo);
                    tvPopupLineChartInfo.setText(R.string.text_tvPopoup_radarChart);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ibtnClosePopup) {
                mPopupWindow.dismiss(); // Chiudo la finestra di popup
                radarChart.setEnabled(true);
                radarChart.setTouchEnabled(true);
                cntMnuLineChartInfo = 0;
            }
        }
    }
}
