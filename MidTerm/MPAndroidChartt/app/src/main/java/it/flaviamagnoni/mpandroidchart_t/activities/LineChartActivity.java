/*
 * In questa Activity viene ricevuto l'intent che ha lanciato l'Activity e vengono ricavati gli
 * array passati tramite il bundle dell'Intent. In questo modo è possibile costruire i dati del
 * chart e creare il relativo LineChart.
 */

package it.flaviamagnoni.mpandroidchart_t.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.flaviamagnoni.mpandroidchart_t.R;

/**
 * L'Activity LineChartActivity si occupa della gestione dei dati raccolti (inseriti dall'utente
 * nell'Activity chiamante) e della creazione del relativo LineChart.
 *  * @Author EndGame()
 *  * @Version 1.0
 */
public class LineChartActivity extends AppCompatActivity {
    private Holder holder;  // Gestore dell'interfaccia
    private ArrayList<Integer> hourDataset, temperatureDataset; // ArrayList di interi che contengono l'input, inserito dall'utente, e passato tramite Intent
    private int cntMnuLineChartInfo = 0;
    private static final int PERMISSION_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        holder = new Holder(this);  // @param this: è il contesto in cui gira l'Activity (LineChartActivity)

        Intent intent = getIntent();    // Intent che contiene nel bundle i dati inseriti dall'utente nell'Activity chiamante
        hourDataset = intent.getIntegerArrayListExtra("hourDataset");   // hourDataset contiene l'array di label inserite dall'utente
        temperatureDataset = intent.getIntegerArrayListExtra("temperatureDataset"); // temperatureDataset contiene l'array di value inseriti dall'utente

        createLineChart();  // Metodo per creare il LineChart
    }

    /**
     * createLineChart() è il metodo che si occupa della costruzione dei dati da visualizzare
     * nel LineChart, e che chiama la gestione dello stile dei dati e la creazione del chart.
     */
    public void createLineChart() {
        List<Entry> entries = new ArrayList<Entry>();   // Array di Entry. La classe Entry rappresenta una entry nel chart, con coordinate (x, y)
        int index;  // Indice che rappresenta la posizione dei dati, nell'array, da trasformare in Entry
        int hourX, temperatureY;    // Coordinate dati dell'Entry

        for (index = 0; index < hourDataset.size(); index++) {
            hourX = hourDataset.get(index); // Prendo dall'array la label in posizione index
            temperatureY = temperatureDataset.get(index);   // Prendo dall'array il value in posizione index
            entries.add(new Entry(hourX, temperatureY));    // Trasformo i dati in Entry objects e li aggiungo all'array di Entry (entries)
        }

        LineDataSet dataSet = new LineDataSet(entries, ""); // Aggiungo le entries al DataSet object, che tiene i dati insieme, e permette di uniformare lo stile
        holder.stylingDataset(dataSet); // Gestisco lo stile del LineDataSet object con un metodo dell'Holder
        LineData lineData = new LineData(dataSet);  // Aggiungo i dataSet objects ad un lineData object, che contiene tutti i dati rappresentati dal LineChart
        holder.createChart(lineData);   // Chiamo il metodo dell'Holder che aggiorna e disegna il LineChart
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
                Intent intentAboutUs = new Intent(LineChartActivity.this, AboutUsActivity.class);
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
                    saveToGallery(holder.lineChart, "LineChart");
                } else {
                    requestStoragePermission(holder.lineChart);
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
                            ActivityCompat.requestPermissions(LineChartActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
                        }
                    }).show();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Required!", Toast.LENGTH_SHORT)
                    .show();
            ActivityCompat.requestPermissions(LineChartActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
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

    /**
     * La classe Holder si occupa della gestione dell'interfaccia dell'Activity.
     * Utilizzo il paradigma Model view viewControl
     * @Author EndGame()
     * @Version 1.0
     */
    class Holder implements OnChartGestureListener, View.OnClickListener {
        private Context mContext;    // Rappresenta il contesto in cui l'Activity gira (LineChartActivity)
        private int orientation = getResources().getConfiguration().orientation;
        private int lineChartActivityColor = getColor(R.color.colBtnLineChart); // Colore che rappresenta il LineChart

        // Definizione delle View e ViewGroup che realizzano il Layout
        private ConstraintLayout clLineChartActivity;
        private TextView tvLineChartTitle;

        private LineChart lineChart;    // Questa View è il LineChart
        private Legend legend;

        // Per costruire il popup associato al click di "mnuAppInfo"
        private ImageButton ibtnClosePopup;
        private TextView tvPopupLineChartInfo;
        private PopupWindow mPopupWindow;

        /**
         * Holder(Context) è il costruttore. Qui vengono collegate le viste nel Layout
         * ("activity_line_chart.xml) al codice Java.
         * @param context: Context. E' il contesto in cui gira l'activity, in questo caso la LineChartActivity.
         */
        public Holder(Context context) {
            this.mContext = context;

            clLineChartActivity = findViewById(R.id.clLineChartActivity);
            clLineChartActivity.setBackground(getDrawable(R.drawable.sfondo_chart));
            tvLineChartTitle = findViewById(R.id.tvLineChartTitle);
            tvLineChartTitle.setText(R.string.tvLineChart_title);
            tvLineChartTitle.setBackgroundColor(lineChartActivityColor);

            lineChart = findViewById(R.id.lineChart);
            lineChart.setOnChartGestureListener(this);  // Imposto il Listener di gestures sul chart

            legend = lineChart.getLegend();
            lineChart.setDoubleTapToZoomEnabled(false); // Disabilito lo zoom tramite il doppio tap
        }

        /**
         * stylingDataset() gestisce lo stile del LineDataSet object
         * @param dataSet : oggetto che tiene i dati insieme, permettendo di uniformare lo stile
         */
        public void stylingDataset(LineDataSet dataSet) {
            // Styling del dataset
            dataSet.setColor(Color.RED);
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setCircleRadius(5);
            dataSet.setCircleHoleColor(Color.YELLOW);
            dataSet.setCircleHoleRadius(3);
            dataSet.setLabel(getString(R.string.text_LineChart_Temperature_label_legend));
            dataSet.setValueTextSize(12);
            dataSet.setValueTextColor(Color.BLUE);  // Styling
            dataSet.setMode(LineDataSet.Mode.LINEAR);
            dataSet.setLineWidth((float)1.5);
        }

        /**
         * createChart metodo dell'Holder che carica i dati nel chart,
         * disegna il LineChart e, poi, lo aggiorna (refresh).
         * @param lineData : data object che deve essere settato nel chart.
         */
        public void createChart(LineData lineData) {
            lineChart.setBackgroundColor(Color.WHITE);
            //Description description = new Description();
            //lineChart.setDescription(description);
            Description description = lineChart.getDescription();
            description.setEnabled(true);   // enable/disable description
            description.setText(getString(R.string.text_LineChart_Chart_Title));    // imposta il testo della descrizione
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                description.setPosition(650f, 50f);  // imposta la posizione della descrizione sullo schermo portrait
            } else {
                description.setPosition(1000f, 50f);  // imposta la posizione della descrizione sullo schermo landscape
            }

            lineChart.setData(lineData);    // Setto il data object lineData nel chart

            XAxis xAxis = lineChart.getXAxis(); // Prendo l'asse delle x (Hour)
            YAxis yAxisRight = lineChart.getAxisRight();    // Prendo l'asse y a destra del LineChart
            YAxis yAxisLeft = lineChart.getAxisLeft();  //Prendo l'asse y a sinistra del LineChart

            // Gestisco lo stile degli assi
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // Posiziono l'asse delle x lungo l'asse orizzontale in basso al chart
            xAxis.setAxisLineColor(Color.BLACK);
            xAxis.setAxisLineWidth(2);
            xAxis.setDrawLabels(true);
            xAxis.setAxisMinimum(0);
            xAxis.setAxisMaximum(24);
            xAxis.enableGridDashedLine(20,20,20);
            xAxis.setTextSize(14);
            xAxis.setDrawGridLines(true);
            xAxis.setGridLineWidth((float) 0.5);
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                xAxis.setLabelCount(23);
            }

            yAxisRight.setDrawLabels(false);    // Non disegno l'asse delle y (Temperature) lungo l'asse verticale di destra (in questo modo è disegnato solo a sinistra)
            yAxisLeft.setAxisLineColor(Color.BLACK);
            yAxisLeft.setAxisLineWidth(2);
            yAxisLeft.setDrawLabels(true);
            yAxisLeft.setAxisMinimum(-50f);
            yAxisLeft.setAxisMaximum(50f);
            yAxisLeft.setTextSize(14);
            yAxisLeft.setDrawGridLines(true);
            yAxisLeft.setGridLineWidth((float) 0.5);
            yAxisLeft.enableGridDashedLine(20,20,20);
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                yAxisLeft.setLabelCount(100);
            }

            yAxisRight.setDrawGridLines(false);
            yAxisRight.enableAxisLineDashedLine(20,20,20);
            yAxisRight.setDrawAxisLine(false);
            //yAxisLeft.setUseAutoScaleMinRestriction(true);
            //yAxisLeft.setUseAutoScaleMaxRestriction(true);

            // Gestisco lo stile della Legend
            legend.setEnabled(true);
            legend.setTextSize(18);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setDrawInside(true);
            legend.setYOffset(10);

            // Gestisco lo stile del LineChart
            lineChart.animateXY(1500, 3000);
            lineChart.setBackgroundColor(Color.WHITE);
            lineChart.setExtraOffsets(5,20,20,50);
            //lineChart.setVisibleXRange((float) 1, (float) 1);
            //lineChart.setVisibleXRangeMaximum(5);

            lineChart.invalidate(); // Refresh del LineChart
        }

        public void showPopupWindow(int id) {
            switch (id) {
                case R.id.mnuLineChartInfo:
                    lineChart.setEnabled(false);
                    lineChart.setTouchEnabled(false);
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);  // Inizializzo una nuova istanza del LayoutInflater service
                    View popupLineChartInfo = inflater.inflate(R.layout.popup_mnu_linechart_info, null);    // Gonfio (inflate) il layout "popup_mnu_app_info" all'interno della View popupLineChartInfo

                    mPopupWindow = new PopupWindow(popupLineChartInfo, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);   // Inizializzo una nuova istanza di una finestra popup

                    // Imposto un valore di elevation per la finestra di popup (per API >= 21)
                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    mPopupWindow.showAtLocation(clLineChartActivity, Gravity.CENTER, 0, 0);  // Visualizza la finestra di popup al centro del ConstraintLayout del root

                    ibtnClosePopup = popupLineChartInfo.findViewById(R.id.ibtnClosePopup);
                    ibtnClosePopup.setOnClickListener(this);
                    tvPopupLineChartInfo = popupLineChartInfo.findViewById(R.id.tvPopupLineChartInfo);
                    tvPopupLineChartInfo.setText(R.string.text_tvPopupLineChartInfo);
                    break;
            }
        }

        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartLongPressed(MotionEvent me) {
            Toast.makeText(mContext, "LongPressed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onChartDoubleTapped(MotionEvent me) {

        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {

        }

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

        }

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

        }

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ibtnClosePopup) {
                mPopupWindow.dismiss(); // Chiudo la finestra di popup
                lineChart.setEnabled(true);
                lineChart.setTouchEnabled(true);
                cntMnuLineChartInfo = 0;
            }
        }
    }
}