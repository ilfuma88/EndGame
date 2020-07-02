package it.flaviamagnoni.mpandroidchart_t;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static it.flaviamagnoni.mpandroidchart_t.BarInfo.SomeBarInfo;

public class BarchartInsertDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter barCharAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart_insertdata);

        recyclerView = findViewById(R.id.rvInsertData);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        barCharAdapter = new DataAdapter(SomeBarInfo());
        recyclerView.setAdapter(barCharAdapter);
    }


    /**
     * la classe adapter contiee gia tutti gl oggetti da mettere nella recyclerview e si  occupa di farli comparire
     */
    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {
        ArrayList<BarInfo> bars;

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

        // Provide a suitable constructor (depends on the kind of dataset)
        public DataAdapter(ArrayList<BarInfo> barInfos) {
            this.bars = barInfos;

        }

        // Create new views (invoked by the layout manager)
        @Override
        public DataAdapter.DataHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_barchart_insert_data, parent, false);

            DataHolder dh = new DataHolder(v);
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