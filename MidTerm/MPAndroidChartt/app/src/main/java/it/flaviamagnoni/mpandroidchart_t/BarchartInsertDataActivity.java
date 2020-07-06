package it.flaviamagnoni.mpandroidchart_t;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BarchartInsertDataActivity extends AppCompatActivity
                                        implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter barChartAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnAddBarData;
    private Button btnCreateBarChart;
    private ArrayList <BarInfo> barsData = new ArrayList<>();
    private EditText etBarDataLabel;
    private EditText etBarDataValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart_insertdata);

        recyclerView = findViewById(R.id.rvInsertBarData);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        barChartAdapter = new DataAdapter(barsData);
        recyclerView.setAdapter(barChartAdapter);
        btnAddBarData = findViewById(R.id.btnAddBarData);
        btnAddBarData.setOnClickListener(this);
        btnCreateBarChart = findViewById(R.id.btnCreateBarChart);
        btnCreateBarChart.setOnClickListener(this);
        etBarDataLabel = findViewById(R.id.etBarDataLabel);
        etBarDataValue = findViewById(R.id.etBarDataValue);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddBarData:
                barsData.add(new BarInfo(etBarDataLabel.getText().toString(),
                        etBarDataValue.getText().toString()));
                barChartAdapter.notifyDataSetChanged();
                etBarDataLabel.setText("");
                etBarDataValue.setText("");
                break;
            case R.id.btnCreateBarChart:
                Intent intent = new Intent (BarchartInsertDataActivity.this, BarChartActivity.class);
                intent.putParcelableArrayListExtra("chart",barsData);
                /**   codice inutile in  questa versione del progetto, ma date le modeste dimensioni del progetto, mi piace e me lo tengo qui
                for (int i=0; i < barsData.size(); i++) {
                    String key = "barsData";
                    key = key+String.valueOf(i);
                    intent.putExtra(key, barsData.get(i));
                }
                */
                BarchartInsertDataActivity.this.startActivity(intent);
                break;
        }
    }

    /**
     * la classe adapter contiene gia tutti gl oggetti da mettere nella recyclerview e si  occupa di farli comparire
     */
    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {
        ArrayList<BarInfo> bars;

        // Provide a suitable constructor (depends on the kind of dataset)
        public DataAdapter(ArrayList<BarInfo> newBars) {
            this.bars = newBars;
        }
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class DataHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView tvLabel;
            public TextView tvValue;

            public DataHolder(View itemView) {
                super(itemView);
                tvLabel = itemView.findViewById(R.id.tvRowBarChartDataLabel);
                tvValue = itemView.findViewById(R.id.tvRowBarChartDataValue);
            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public DataAdapter.DataHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            ConstraintLayout recyclerRow = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_barchart_insert_data, parent, false);

            DataHolder dh = new DataHolder(recyclerRow);
            return dh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(DataHolder holder, int position) {
            holder.tvLabel.setText(bars.get(position).label);
            holder.tvValue.setText(bars.get(position).value);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return bars.size();
        }
    }

}