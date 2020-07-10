/*
 * In questa Activity viene ricevuto l'intent che ha lanciato l'Activity e vengono ricavati gli
 * array passati tramite il bundle dell'Intent. In questo modo è possibile costruire i dati del
 * chart e creare il relativo LineChart.
 */

package it.flaviamagnoni.mpandroidchart_t;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * L'Activity LineChartActivity si occupa della gestione dei dati raccolti (inseriti dall'utente
 * nell'Activity chiamante) e della creazione del relativo LineChart.
 *  * @Author EndGame()
 *  * @Version 1.0
 */
public class LineChartActivity extends AppCompatActivity {
    private Holder holder;  // Gestore dell'interfaccia
    private ArrayList<Integer> hourDataset, temperatureDataset; // ArrayList di interi che contengono l'input, inserito dall'utente, e passato tramite Intent

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

    /**
     * La classe Holder si occupa della gestione dell'interfaccia dell'Activity.
     * Utilizzo il paradigma Model view viewControl
     * @Author EndGame()
     * @Version 1.0
     */
    class Holder {
        private Context context;    // Rappresenta il contesto in cui l'Activity gira (LineChartActivity)
        private int orientation = getResources().getConfiguration().orientation;
        private int lineChartActivityColor = getColor(R.color.colBtnLineChart); // Colore che rappresenta il LineChart

        // Definizione delle View e ViewGroup che realizzano il Layout
        private ConstraintLayout clLineChartActivity;
        private TextView tvLineChartTitle;

        private LineChart lineChart;    // Questa View è il LineChart
        private Legend legend;

        /**
         * Holder(Context) è il costruttore. Qui vengono collegate le viste nel Layout
         * ("activity_line_chart.xml) al codice Java.
         * @param context: Context. E' il contesto in cui gira l'activity, in questo caso la LineChartActivity.
         */
        public Holder(Context context) {
            this.context = context;

            clLineChartActivity = findViewById(R.id.clLineChartActivity);
            clLineChartActivity.setBackground(getDrawable(R.drawable.sfondo_chart));
            tvLineChartTitle = findViewById(R.id.tvLineChartTitle);
            tvLineChartTitle.setText(R.string.tvLineChart_title);
            tvLineChartTitle.setBackgroundColor(lineChartActivityColor);

            lineChart = findViewById(R.id.lineChart);

            legend = lineChart.getLegend();
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
            dataSet.setLabel("Temperature trend");
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
            Description description = new Description();
            description.setText("Temperature LineChart");
            lineChart.setDescription(description);

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
    }
}