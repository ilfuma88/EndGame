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
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
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
     * nel LineChart.
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
        //dataSet.setColor();   // Styling
        //dataSet.setValueTextColor();  // Styling

        LineData lineData = new LineData(dataSet);  // Aggiungo i dataSet objects ad un lineData object, che contiene tutti i dati rappresentati dal LineChart

        holder.createChart(lineData);   // Chiama il metodo dell'Holder che aggiorna e disegna il LineChart
    }

    /**
     * La classe Holder si occupa della gestione dell'interfaccia dell'Activity.
     * Utilizzo il paradigma Model view viewControl
     * @Author EndGame()
     * @Version 1.0
     */
    class Holder {
        private Context context;    // Rappresenta il contesto in cui l'Activity gira (LineChartActivity)
        private int lineChartActivityColor = getColor(R.color.colBtnLineChart); // Colore che rappresenta il LineChart

        // Definizione delle View e ViewGroup che realizzano il Layout
        private ConstraintLayout clLineChartActivity;
        private TextView tvLineChartTitle;

        private LineChart lineChart;    // Questa View è il LineChart

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
            lineChart.setBackgroundColor(Color.WHITE);
        }

        /**
         * createChart metodo dell'Holder che carica i dati nel chart,
         * aggiorna e disegna il LineChart.
         * @param lineData : data object che deve essere settato nel chart.
         */
        public void createChart(LineData lineData) {
            lineChart.setData(lineData);    // Setto il data object lineData nel chart
            lineChart.invalidate(); // Refresh del LineChart
        }
    }
}