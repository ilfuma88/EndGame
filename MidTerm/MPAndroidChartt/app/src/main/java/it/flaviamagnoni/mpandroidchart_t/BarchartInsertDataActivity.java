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
    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
        ArrayList<BarInfo> Bars;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView tvLabel;
            public TextView tvValue;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvLabel = itemView.findViewById(R.id.tvRowBarChartDataLabel);
                tvValue = itemView.findViewById(R.id.tvRowBarChartDataValue);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public DataAdapter() {
            label = newLabel;
            value = newValue;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public DataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_barchart_insert_data, parent, false);
        ...
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.setText(mDataset[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }


}