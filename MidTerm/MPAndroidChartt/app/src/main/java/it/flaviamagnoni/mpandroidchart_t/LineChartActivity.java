package it.flaviamagnoni.mpandroidchart_t;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/* L'Activity LineChartActivity si occupa di prendere in input i dati inseriti dall'utente,
 * li mostra in un RecyclerView e, quando l'utente fa touch sul bottone "Create Chart", l'Activity
 * crea il relativo LineChart.
 */
public class LineChartActivity extends AppCompatActivity {
    private Holder holder;  // E' l'oggetto che gestisce l'interfaccia dell'Activity
    private List<String> myHourDataset, myTemperatureDataset;   // Liste che contengono l'input dell'utente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        myHourDataset = new ArrayList<>();
        myTemperatureDataset = new ArrayList<>();

        holder = new Holder(this);  // @param Context this: è il contesto in cui gira l'Activity (LineChartActivity)

        Intent intent = getIntent();
//        int lineChartColor = intent.getIntExtra("LineChartColor", 0);  // Recupero dal bundle dell'intent il colore dello sfondo

//        holder.setColor(/*lineChartColor*/);    // Imposto il colore dello sfondo del titolo utilizzando l'Holder
    }

    /* deleteItem(int position) rimuove il dato (inserito dall'utente) che si trova nella
     * posizione "position" e avvisa l'Adapter di aggiornare la RecyclerView.
     * @param int position: è la posizione nella lista della stringa da rimuovere.
     */
    public void deleteItem(int position) {
        myHourDataset.remove(position);
        myTemperatureDataset.remove(position);
        holder.getLineChartAdapter().notifyItemRemoved(position);   // Avviso l'Adapter che una vista della RecyclerView è stata rimossa
    }

    /* createLineChart(List<String> myHourDataset, List<String> myTemperatureDataset, LineChart lineChart)
     * è il metodo che si occupa della creazione del LineChart.
     * @param List<String> myHourDataset: è l'array che contiene i dati delle ore da inserire nell'asse delle x;
     * @param List<String> myTemperatureDataset: è l'array che contiene i dati delle temperature da inserire nell'asse delle y;
     * @param LineChart lineChart: è l'oggetto LineChart, collegato all'oggetto nel layout relativo, che deve essere disegnato.
     */
    private void createLineChart(List<String> myHourDataset, List<String> myTemperatureDataset, LineChart lineChart) {
        List<String> hourData = myHourDataset, temperatureData = myTemperatureDataset;
        List<Entry> entries = new ArrayList<Entry>();   // Array di Entry. La classe Entry rappresenta una entry nel chart, con coordinate (x, y)
        int index;  // Indice che rappresenta la posizione dei dati da trasformare in Entry
        int labelX, valueY;   // Coordinate dati dell'Entry

        for (index = 0; index < hourData.size(); index++) {
            // Trasformo i dati da stringa a intero
            labelX = Integer.parseInt(hourData.get(index));
            valueY = Integer.parseInt(temperatureData.get(index));
            // Trasformo i dati in Entry objects
            entries.add(new Entry(labelX, valueY));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Temperature\nChart");    // Aggiungo le entries al DataSet object, che tiene i dati insieme, e permette di uniformare lo stile
        //dataSet.setColor();   // Styling
        //dataSet.setValueTextColor();  // Styling

        LineData lineData = new LineData(dataSet);  // Aggiungo i dataSet objects ad un lineData object, che contiene tutti i dati rappresentati dal LineChart
        lineChart.setData(lineData);    // Setto il data object nel chart
        lineChart.invalidate(); // Refresh del LineChart
    }

    /* La classe Holder si occuperà della gestione dell'interfaccia dell'Activity.
     * Utilizzo il paradigma Model view viewControl
     */
    class Holder implements View.OnClickListener {
        private Context context;
        private ConstraintLayout clLineChart;
        private int color = getColor(R.color.colBtnLineChart);  // Colore che rappresenta la LineChartActivity

        private TextView tvLineChartTitle;
        private RecyclerView rvLineChartData;
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.Adapter mAdapter;
        private EditText etlineDataHour, etLineDataTemperature;
        private Button btnAddLineChartData, btnCreateLineChart;
        private LineChart lineChart;
        private Legend legend;

        /* Holder(Context): costruttore;
         * @param context: Context. E' il contesto in cui gira l'activity, in questo caso la MainActivity
         */
        public Holder(Context context) {
            this.context = context;

            clLineChart = findViewById(R.id.clLineChart);
            clLineChart.setBackground(getDrawable(R.drawable.sfondo_chart));

            tvLineChartTitle = findViewById(R.id.tvLineChartTitle);
            tvLineChartTitle.setBackgroundColor(color);

            rvLineChartData = findViewById(R.id.rvInsertLineChartData);
            layoutManager = new LinearLayoutManager(context);
            mAdapter = new MyAdapter(myHourDataset, myTemperatureDataset);
            rvLineChartData.setLayoutManager(layoutManager);
            rvLineChartData.setAdapter(mAdapter);
            rvLineChartData.setBackgroundColor(Color.WHITE);

            etlineDataHour = findViewById(R.id.etlineDataHour);
            etLineDataTemperature = findViewById(R.id.etLineDataTemperature);

            btnAddLineChartData = findViewById(R.id.btnAddLineChartData);
            btnCreateLineChart = findViewById(R.id.btnCreateLineChart);

            lineChart = findViewById(R.id.lineChart);
            lineChart.setBackgroundColor(Color.WHITE);
            // Imposto la Legend
            legend = lineChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(11f);
            legend.setTextColor(Color.WHITE);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);

            btnAddLineChartData.setOnClickListener(this);
            btnCreateLineChart.setOnClickListener(this);
        }


//        /* setColor(int): void. Metodo che imposta il colore del titolo
//         * @param color: int. E' il valore intero del colore da impostare come sfondo del titolo
//         */
//        public void setColor(/*int color*/) {
//            tvLineChartTitle.setBackgroundColor(getColor(R.color.colBtnLineChart)); // Imposto lo sfondo della TextView
//            tvLineChartTitle.setTextColor(Color.BLACK); // Imposto il colore del testo della TextView
//            clLineChart.setBackground(getDrawable(R.drawable.sfondo_chart));
//        }

        public RecyclerView.Adapter getLineChartAdapter() {
            return mAdapter;    // Ritorna l'Adapter che gestisce la RecyclerView
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnAddLineChartData) {
                // Verifico che gli input siano corretti (non null e le ore nel range 0 -:- 23)
                if (((etlineDataHour.getText() != null) && (!etlineDataHour.getText().toString().equals(""))) && ((etLineDataTemperature.getText() != null) && (!etLineDataTemperature.getText().toString().equals("")))) {
                    if (((Integer.parseInt(etlineDataHour.getText().toString())) >= 0) && ((Integer.parseInt(etlineDataHour.getText().toString())) <= 23)) {
                        myHourDataset.add(etlineDataHour.getText().toString()); // Aggiungo l'input alla Lista relativa
                        myTemperatureDataset.add(etLineDataTemperature.getText().toString());   // Aggiungo l'input alla lista relativa
                        mAdapter.notifyDataSetChanged();    // Notifico all'Adapter che deve aggiungere la vista alla RecyclerView
                        etlineDataHour.setText("");
                        etLineDataTemperature.setText("");
                    }
                }
            }
            if (v.getId() == R.id.btnCreateLineChart) {
                createLineChart(myHourDataset, myTemperatureDataset, lineChart);    // Chiamo il metodo per creare il LineChart
            }
        }
    }

    /* MyAdapter si occupa della gestione delle viste ViewHolder della RecyclerView
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        List<String> mHourDataset, mTemperatureDataset;

        MyAdapter(List<String> myHourDataset, List<String> myTemperatureDataset) {
            mHourDataset = myHourDataset;
            mTemperatureDataset = myTemperatureDataset;
        }

        /* onCreateViewHolder() viene chiamato quando la RecyclerView necessita di creare una
         * nuova riga (ViewHolder), che viene riempita nella onBindViewHolder()
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_linechart_added_data, parent, false);
            return new ViewHolder(cl);
        }

        /* onBindViewHolder() utilizza la posizione della ViewHolder da riempire, per determinare
         * quali dovrebbero essere i contenuti, in base alla posizione nella lista.
         * @param ViewHolder viewHolder: è la vista che deve essere riempita;
         * @param int position: è la posizione degli oggetti nella lista
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.tvLineHourData.setText("Hour: " + mHourDataset.get(position));
            viewHolder.tvLineTemperatureData.setText("Temperature: " + mTemperatureDataset.get(position));
        }

        /* getItemCount() restituisce la dimensione del Dataset.

         */
        @Override
        public int getItemCount() {
            return mHourDataset.size();
        }

        /* La classe ViewHolder ha il compito di gestire l'interfaccia delle view
         * che costituiscono una singola riga della RecyclerView
         */
        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvLineHourData, tvLineTemperatureData;
            private ImageView ivDeleteLineChartData;

            public ViewHolder(ConstraintLayout cl) {
                super(cl);
                tvLineHourData = cl.findViewById(R.id.tvLineHourData);
                tvLineTemperatureData = cl.findViewById(R.id.tvLineTemperatureData);
                ivDeleteLineChartData = cl.findViewById(R.id.ivDeleteLineChartData);

                ivDeleteLineChartData.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ivDeleteLineChartData) {
                    deleteItem(getAdapterPosition());   // Chiamo il metodo deleteItem() per rimuovere l'oggetto dalla lista e dalla RecyclerView
                }
            }
        }
    }
}
