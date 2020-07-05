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

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {
    private Holder holder;
    private List<String> myHourDataset, myTemperatureDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        myHourDataset = new ArrayList<>();
        myTemperatureDataset = new ArrayList<>();

        holder = new Holder(this);

        Intent intent = getIntent();
        int tvLineChartTitleColor = intent.getIntExtra("tvLineChartColor", 0);  // Recupero dal bundle dell'intent il colore dello sfondo del titolo

        holder.setTitleColor(tvLineChartTitleColor);    // Imposto il colore del titolo, utilizzando l'Holder
    }

    public void deleteItem(int position) {
        myHourDataset.remove(position);
        myTemperatureDataset.remove(position);
        holder.getLineChartAdapter().notifyItemRemoved(position);
    }

    /* La classe Holder si occuperà della gestione dell'interfaccia dell'Activity.
     * Utilizzo il paradigma Model view viewControl
     */
    class Holder implements View.OnClickListener {
        private Context context;
        private TextView tvLineChartTitle;
        private RecyclerView rvLineChartData;
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.Adapter mAdapter;
        private EditText etlineDataHour, etLineDataTemperature;
        private Button btnAddLineChartData, btnCreateLineChart;
        private LineChart lineChart;

        /* Holder(Context): costruttore;
         * @param context: Context. E' il contesto in cui gira l'activity, in questo caso la MainActivity
         */
        public Holder(Context context) {
            this.context = context;
            tvLineChartTitle = findViewById(R.id.tvLineChartTitle);

            rvLineChartData = findViewById(R.id.rvInsertLineChartData);
            layoutManager = new LinearLayoutManager(context);
            mAdapter = new MyAdapter(myHourDataset, myTemperatureDataset);
            rvLineChartData.setLayoutManager(layoutManager);
            rvLineChartData.setAdapter(mAdapter);

            etlineDataHour = findViewById(R.id.etlineDataHour);
            etLineDataTemperature = findViewById(R.id.etLineDataTemperature);
            btnAddLineChartData = findViewById(R.id.btnAddLineChartData);
            btnCreateLineChart = findViewById(R.id.btnCreateLineChart);
            lineChart = findViewById(R.id.lineChart);

            btnAddLineChartData.setOnClickListener(this);
            btnCreateLineChart.setOnClickListener(this);
        }

        /* setTitleColor(int): void. Metodo che imposta il colore del titolo
         * @param color: int. E' il valore intero del colore da impostare come sfondo del titolo
         */
        public void setTitleColor(int color) {
            tvLineChartTitle.setBackgroundColor(color); // Imposto lo sfondo della TextView
            tvLineChartTitle.setTextColor(Color.BLACK); // Imposto il colore del testo della TextView
        }

        public RecyclerView.Adapter getLineChartAdapter() {
            return mAdapter;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnAddLineChartData) {
                if ((etlineDataHour.getText() != null) && (etLineDataTemperature.getText() != null)) {
                    myHourDataset.add(etlineDataHour.getText().toString());
                    myTemperatureDataset.add(etLineDataTemperature.getText().toString());
                    mAdapter.notifyDataSetChanged();
                    etlineDataHour.setText("");
                    etLineDataTemperature.setText("");
                }
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        List<String> mHourDataset, mTemperatureDataset;

        MyAdapter(List<String> myHourDataset, List<String> myTemperatureDataset) {
            mHourDataset = myHourDataset;
            mTemperatureDataset = myTemperatureDataset;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_linechart_added_data, parent, false);
            return new ViewHolder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.tvLineHourData.setText("Hour: " + mHourDataset.get(position));
            viewHolder.tvLineTemperatureData.setText("Temperature: " + mTemperatureDataset.get(position));
        }

        @Override
        public int getItemCount() {
            return mHourDataset.size();
        }

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
                    deleteItem(getAdapterPosition());
                }
            }
        }
    }
}
