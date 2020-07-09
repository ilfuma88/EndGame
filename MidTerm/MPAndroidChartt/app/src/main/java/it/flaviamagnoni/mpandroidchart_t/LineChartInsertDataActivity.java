/*
 * In questa Activity si fa un esempio di come si può costruire un LineChart.
 * Scopo dell'esempio: realizzare un grafico che mostra l'andamento della temperatura nelle ore
 * inserite dall'utente (hour, temperature). Si può, per esempio, visualizzare l'andamento della
 * temperatura di una giornata.
 */

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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * L'Activity LineChartInsertDataActivity si occupa di gestire la fase di presa in input dei dati
 * inseriti dall'utente e, tramite l'Holder li invia in un RecyclerView e, quando l'utente fa touch
 * sul bottone "Create Chart", viene lanciata la relativa Activity che crea il LineChart con
 * i dati ottenuti.
 * @Author EndGame()
 * @Version 2.0
 */
public class LineChartInsertDataActivity extends AppCompatActivity {
    private Holder holder;  // E' l'oggetto che gestisce l'interfaccia dell'Activity
    private ArrayList<Integer> mHourDataset, mTemperatureDataset;    // Array di interi che contengono l'input dell'utente
    private Intent intent;
    private int latestHour; // Ultimo dato Label dell'array mHourDataset (Hour)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_insert_data);

        holder = new Holder(this);  // @param Context this: è il contesto in cui gira l'Activity (LineChartInsertDataActivity)
        mHourDataset = new ArrayList<>();   // Creo l'array (inizialmente array vuoto) che conterrà le input label (Hour)
        mTemperatureDataset = new ArrayList<>();    // Creo l'array (inizialmente array vuoto) che conterrà gli input value (Temperature)

        intent = getIntent();

        latestHour = -1;
    }

    /**
     * La classe Holder si occupa della gestione dell'interfaccia dell'Activity.
     * Utilizzo il paradigma Model view viewControl
     * @Author EndGame()
     * @Version 1.0
     */
    class Holder implements View.OnClickListener {
        private Context context;    // Rappresenta il contesto in cui l'Activity gira (LineChartInsertDataActivity)
        private int lineChartcolor = getColor(R.color.colBtnLineChart);  // Colore che rappresenta il LineChart

        // Definizione delle View e ViewGroup che realizzano il Layout
        private ConstraintLayout clLineChartInsertData;

        private TextView tvLineChartTitle;
        private EditText etlineDataHour, etLineDataTemperature;
        private Button btnAddLineChartData, btnCreateLineChart;

        private RecyclerView rvInsertLineChartData;
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.Adapter mAdapter;  // si occupa della gestione delle viste ViewHolder della RecyclerView

        /**
         * Holder(Context) è il costruttore. Qui vengono collegate le viste nel Layout
         * ("activity_line_chart_insert_data.xml) al codice Java.
         * @param context: Context. E' il contesto in cui gira l'activity, in questo caso la LineChartInsertDataActivity.
         */
        public Holder(Context context) {
            this.context = context;
            clLineChartInsertData = findViewById(R.id.clLineChartInsertData);
            clLineChartInsertData.setBackground(getDrawable(R.drawable.sfondo_chart));

            tvLineChartTitle = findViewById(R.id.tvLineChartTitle);
            tvLineChartTitle.setText(R.string.tvLineChart_title);
            tvLineChartTitle.setBackgroundColor(lineChartcolor);
            etlineDataHour = findViewById(R.id.etlineDataHour);
            etlineDataHour.setBackgroundColor(Color.WHITE);
            etLineDataTemperature = findViewById(R.id.etLineDataTemperature);
            etLineDataTemperature.setBackgroundColor(Color.WHITE);
            btnAddLineChartData = findViewById(R.id.btnAddLineChartData);
            btnCreateLineChart = findViewById(R.id.btnCreateLineChart);

            rvInsertLineChartData = findViewById(R.id.rvInsertLineChartData);
            rvInsertLineChartData.setBackgroundColor(Color.WHITE);
            layoutManager = new LinearLayoutManager(context);
            rvInsertLineChartData.setLayoutManager(layoutManager);
            mAdapter = new MyLineChartAdapter(mHourDataset, mTemperatureDataset);
            rvInsertLineChartData.setAdapter(mAdapter);
            //rvInsertLineChartData.setHasFixedSize(true);

            btnAddLineChartData.setOnClickListener(this);
            btnCreateLineChart.setOnClickListener(this);
        }

        /**
         * onClick() è l'evento associato al click di una View. Qui si gestisce il click del bottone
         * che aggiunge i dati inseriti dall'utente ai Dataset e alla RecyclerView, e il click
         * del bottone che lancia l'Activity che crea il LineChart.
         * @param v : è la View che è stata cliccata
         */
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnAddLineChartData) {
                String hour = etlineDataHour.getText().toString();
                String temperature = etLineDataTemperature.getText().toString();
                // Verifico che gli input siano corretti (non null e le ore nel range 0 -:- 23)
                if (((etlineDataHour.getText() != null) && (!hour.equals(""))) && ((etLineDataTemperature.getText() != null) && (!temperature.equals("")))) {
                    if (((Integer.parseInt(hour)) >= 0) && ((Integer.parseInt(hour)) <= 23)) {
                        if (Integer.parseInt(hour) > latestHour) { // Eseguo il codice solo se l'utente ha inserito una label Hour >= latestHour (ultimo elemento nell'array; al primo inserimento = 0)
                            mHourDataset.add(Integer.parseInt(hour)); // Aggiungo l'input hour alla Lista relativa
                            mTemperatureDataset.add(Integer.parseInt(temperature));   // Aggiungo l'input temperature alla lista relativa
                            latestHour = mHourDataset.get((mHourDataset.size()) - 1);   // Aggiorno latestHour con l'ultimo elemento dell'array mHourDataset
                            mAdapter.notifyDataSetChanged();    // Notifico all'Adapter che deve aggiungere la vista alla RecyclerView
                            etlineDataHour.setText("");
                            etLineDataTemperature.setText("");
                        } else {
                            Toast.makeText(context, "Insert an hour greater than the previous one!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "No valid hour! Insert an hour between 0 -:- 23!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (!(hour.equals("") && temperature.equals(""))) {
                        if (hour.equals("")) {
                            Toast.makeText(context, "Insert an Hour!", Toast.LENGTH_LONG).show();
                        }
                        if (temperature.equals("")) {
                            Toast.makeText(context, "Insert a Temperature!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Insert an Hour and a Temperature!", Toast.LENGTH_LONG).show();
                    }
                }
            }
            if (v.getId() == R.id.btnCreateLineChart) {
                Intent lineChartIntent = new Intent(context, LineChartActivity.class);  // Intent che lancia l'Activity LineChartActivity.class
                lineChartIntent.putExtra("hourDataset", mHourDataset);  // Put dell'array di dati (mHourDataset) nel bundle dell'Intent
                lineChartIntent.putExtra("temperatureDataset", mTemperatureDataset);    // Put dell'array di dati (mTemperatureDataset) nel bundle dell'Intent
                startActivity(lineChartIntent); // Lancio l'Activity LineChartActivity.class che si occupa della creazione del chart
            }
        }

        /**
         * getLineChartAdapter() ritorna l'Adapter associato al RecyclerView
         * @return RecyclerView.Adapter : l'adapter che gestisce il RecyclerView
         */
        public RecyclerView.Adapter getLineChartAdapter() {
            return mAdapter;    // Ritorna l'Adapter che gestisce la RecyclerView
        }
    }

    /**
     * MyLineChartAdapter si occupa della gestione delle viste ViewHolder della RecyclerView
     * @Author EndGame()
     * @Version 2.0
     */
    public class MyLineChartAdapter extends RecyclerView.Adapter<MyLineChartAdapter.ViewHolder> {
        ArrayList<Integer> hourDataset, temperatureDataset; // Array di interi che contengono l'input dell'utente

        /**
         * MyLineChartAdapter è il costruttore della classe.
         * @param myHourDataset : è l'array contenente le input label (Hour);
         * @param myTemperatureDataset : è l'array contenente gli input value (Temperature).
         */
        MyLineChartAdapter(ArrayList<Integer> myHourDataset, ArrayList<Integer> myTemperatureDataset) {
            this.hourDataset = myHourDataset;
            this.temperatureDataset = myTemperatureDataset;
        }

        /**
         * onCreateViewHolder() viene chiamato quando la RecyclerView necessita di creare una
         * nuova riga (ViewHolder), che viene riempita nella onBindViewHolder().
         * @param parent : ViewGroup
         * @param viewType : tipo di view
         * @return ViewHolder : oggetto gestito dalla classe ViewHolder (che si occupa del Layout della row del RecyclerView)
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_linechart_added_data, parent, false);
            return new ViewHolder(cl);
        }

        /**
         * onBindViewHolder() utilizza la posizione della ViewHolder da riempire, per determinare
         * quali dovrebbero essere i contenuti, in base alla posizione nella lista.
         * @param viewHolder : è la vista che deve essere riempita;
         * @param position : è la posizione degli oggetti nella lista.
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.tvLineHourData.setText("Hour: " + mHourDataset.get(position));   // Set del testo da mostrare nella TextView relativa alla label Hour
            viewHolder.tvLineTemperatureData.setText("Temperature: " + mTemperatureDataset.get(position));  // Set del testo da mostrare nella TextView relativa al value Temperature
        }

        /**
         * getItemCount() restituisce la dimensione del Dataset.
         * @return int : dimensione del dataset
         */
        @Override
        public int getItemCount() {
            return mHourDataset.size();
        }

        /**
         * La classe ViewHolder ha il compito di gestire l'interfaccia delle View (ViewHolder)
         * che costituiscono una singola riga della RecyclerView.
         * @Author EndGame()
         * @Version 1.0
         */
        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // Definizione delle View che realizzano il Layout
            private TextView tvLineHourData, tvLineTemperatureData;
            private ImageView ivDeleteLineChartData;

            /**
             * ViewHolder() è il costruttore. Qui vengono collegate le viste ViewHolder
             * (nel Layout "row_linechart_added_data.xml") al codice Java.
             * @param cl : è il ConstraintLayout (ViewGroup)
             */
            public ViewHolder(ConstraintLayout cl) {
                super(cl);
                tvLineHourData = cl.findViewById(R.id.tvLineHourData);
                tvLineTemperatureData = cl.findViewById(R.id.tvLineTemperatureData);
                ivDeleteLineChartData = cl.findViewById(R.id.ivDeleteLineChartData);

                ivDeleteLineChartData.setOnClickListener(this);
            }

            /**
             * onClick() è l'evento associato al click di una View. Qui si gestisce il click della
             * ImageView "ic_delete.xml" che permette l'eliminazione della riga dai Dataset e
             * dalla RecyclerView.
             * @param v : è la View che ha generato l'evento.
             */
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ivDeleteLineChartData) {
                    deleteItem(getAdapterPosition());   // Chiamo il metodo deleteItem() per rimuovere l'oggetto dalla lista e dalla RecyclerView che sta nella posizione getAdapterPosition()
                }
            }
        }
    }

    /**
     * deleteItem(int position) rimuove il dato (inserito dall'utente) che si trova nella
     * posizione "position" e avvisa l'Adapter di aggiornare la RecyclerView.
     * @param adapterPosition : è la posizione, nella lista, del dato da rimuovere.
     */
    private void deleteItem(int adapterPosition) {
        mHourDataset.remove(adapterPosition);   // Rimuove, dall'array mHourDataset, il dato con indice adapterPosition
        mTemperatureDataset.remove(adapterPosition);    // Rimuove, dall'array mTemperatureDataset, il dato con indice adapterPosition
        if (mHourDataset.size() > 0) {
            latestHour = mHourDataset.get((mHourDataset.size()) - 1);   // Aggiorno latestHour con l'ultimo elemento dell'array mHourDataset
        } else {
            latestHour = -1;
        }
        holder.getLineChartAdapter().notifyItemRemoved(adapterPosition);   // Avviso l'Adapter che una vista della RecyclerView è stata rimossa
    }
}