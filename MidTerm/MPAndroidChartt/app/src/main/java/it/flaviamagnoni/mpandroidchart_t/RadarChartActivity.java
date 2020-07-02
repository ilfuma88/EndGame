package it.flaviamagnoni.mpandroidchart_t;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewRadarChart;
    private Adapter mRadarChartAdapter;
    private RecyclerView.LayoutManager mRadarChartLayoutManager;

    private ArrayList<RadarChartRow> mRadarChartRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);
        mRadarChartRows = new ArrayList<>();
        buldView();
    }

    public void buldView(){
        mRecyclerViewRadarChart = findViewById(R.id.rvInsertRadarChart);
        mRecyclerViewRadarChart.setHasFixedSize(true);
        mRadarChartLayoutManager = new LinearLayoutManager(this);
        mRadarChartAdapter = new Adapter(mRadarChartRows);

        mRecyclerViewRadarChart.setLayoutManager(mRadarChartLayoutManager);
        mRecyclerViewRadarChart.setAdapter(mRadarChartAdapter);
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private ArrayList<RadarChartRow> mRowItem;

        public Adapter(ArrayList<RadarChartRow> rowItem) {
            mRowItem = rowItem;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextViewInsertLabel;
            public ImageView mDeleteImageInserLabel;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextViewInsertLabel = itemView.findViewById(R.id.tvRadarChartLabel);
                mDeleteImageInserLabel = itemView.findViewById(R.id.ivRadarChartDelete);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_radarchart_insert_label, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RadarChartRow currentIteam = mRowItem.get(position);
            holder.mTextViewInsertLabel.setText(currentIteam.getLabel());
        }

        @Override
        public int getItemCount() {
            return mRowItem.size();
        }
    }
}
