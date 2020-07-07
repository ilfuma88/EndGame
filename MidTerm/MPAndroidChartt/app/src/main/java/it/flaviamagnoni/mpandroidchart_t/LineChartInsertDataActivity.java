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

import java.util.ArrayList;

public class LineChartInsertDataActivity extends AppCompatActivity {
    private Holder holder;
    private ArrayList<Integer> mLineChartDataset, mHourDataset, mTemperatureDataset;
    private Intent intent;
    private int latestHour = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_insert_data);

        holder = new Holder(this);
        mLineChartDataset = new ArrayList<>();
        mHourDataset = new ArrayList<>();
        mTemperatureDataset = new ArrayList<>();

        intent = getIntent();
    }

    class Holder implements View.OnClickListener {
        private Context context;
        private int lineChartcolor = getColor(R.color.colBtnLineChart);  // Colore che rappresenta la LineChartActivity

        private ConstraintLayout clLineChartInsertData;

        private TextView tvLineChartTitle;
        private EditText etlineDataHour, etLineDataTemperature;
        private Button btnAddLineChartData, btnCreateLineChart;

        private RecyclerView rvInsertLineChartData;
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.Adapter mAdapter;

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

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnAddLineChartData) {
                String hour = etlineDataHour.getText().toString();
                String temperature = etLineDataTemperature.getText().toString();
                // Verifico che gli input siano corretti (non null e le ore nel range 0 -:- 23)
                if (((etlineDataHour.getText() != null) && (!hour.equals(""))) && ((etLineDataTemperature.getText() != null) && (!temperature.equals("")))) {
                    if (((Integer.parseInt(hour)) >= 0) && ((Integer.parseInt(hour)) <= 23)) {
                        if (Integer.parseInt(hour) >= latestHour) {
                            mHourDataset.add(Integer.parseInt(hour)); // Aggiungo l'input alla Lista relativa
                            mTemperatureDataset.add(Integer.parseInt(temperature));   // Aggiungo l'input alla lista relativa
                            latestHour = Integer.parseInt(hour);
                            mAdapter.notifyDataSetChanged();    // Notifico all'Adapter che deve aggiungere la vista alla RecyclerView
                            etlineDataHour.setText("");
                            etLineDataTemperature.setText("");
                        }
                    }
                }
            }
            if (v.getId() == R.id.btnCreateLineChart) {
                Intent lineChartIntent = new Intent(context, LineChartActivity.class);
                lineChartIntent.putExtra("hourDataset", mHourDataset);
                lineChartIntent.putExtra("temperatureDataset", mTemperatureDataset);
                startActivity(lineChartIntent);
            }
        }

        public RecyclerView.Adapter getLineChartAdapter() {
            return mAdapter;
        }
    }

    public class MyLineChartAdapter extends RecyclerView.Adapter<MyLineChartAdapter.ViewHolder> {
        ArrayList<Integer> hourDataset, temperatureDataset;

        MyLineChartAdapter(ArrayList<Integer> myHourDataset, ArrayList<Integer> myTemperatureDataset) {
            this.hourDataset = myHourDataset;
            this.temperatureDataset = myTemperatureDataset;
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
                    deleteItem(getAdapterPosition());   // Chiamo il metodo deleteItem() per rimuovere l'oggetto dalla lista e dalla RecyclerView
                }
            }
        }
    }

    private void deleteItem(int adapterPosition) {
        mHourDataset.remove(adapterPosition);
        mTemperatureDataset.remove(adapterPosition);
        holder.getLineChartAdapter().notifyItemRemoved(adapterPosition);   // Avviso l'Adapter che una vista della RecyclerView è stata rimossa
    }
}