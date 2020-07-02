package it.flaviamagnoni.mpandroidchart_t;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        barCharAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(barCharAdapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView label;
            private TextView value;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                label = itemView.findViewById(R.id.)
            }
        }
    }
}