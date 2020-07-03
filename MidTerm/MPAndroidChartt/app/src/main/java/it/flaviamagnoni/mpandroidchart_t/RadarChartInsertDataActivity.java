package it.flaviamagnoni.mpandroidchart_t;

import android.content.Intent;
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

public class RadarChartInsertDataActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewDataRadarChart;
    private RadarChartActivity.Adapter mRadarChartDataAdapter;
    private RecyclerView.LayoutManager mRadarChartLayoutDataManager;

    private ArrayList<Integer> mLabelData;
    private ArrayList<String> mLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart_insert_data);
        mLabelData = new ArrayList<>();
        mLabel = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        mLabel.addAll(bundle.getStringArrayList("label"));
    }
    public void buldView(){
        mRecyclerViewDataRadarChart = findViewById(R.id.rvRadarChartInsertData);
        mRecyclerViewDataRadarChart.setHasFixedSize(true);
        mRadarChartLayoutDataManager = new LinearLayoutManager(this);
        mRadarChartDataAdapter = new RadarChartActivity.Adapter(mLabel);

        mRecyclerViewDataRadarChart.setLayoutManager(mRadarChartLayoutDataManager);
        mRecyclerViewDataRadarChart.setAdapter(mRadarChartDataAdapter);
    }


    public class Adapter extends RecyclerView.Adapter<RadarChartActivity.Adapter.ViewHolder> {
        private ArrayList<String> mRowItem;


        public Adapter(ArrayList<String> rowItem) {
            mRowItem = rowItem;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextViewInsertLabel;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextViewInsertLabel = itemView.findViewById(R.id.tvRadarChartLabel);
            }
        }

        @NonNull
        @Override
        public RadarChartActivity.Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_radarchart_insert_data, parent, false);
            RadarChartActivity.Adapter.ViewHolder viewHolder = new RadarChartActivity.Adapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RadarChartActivity.Adapter.ViewHolder holder, int position) {
            holder.mTextViewInsertLabel.setText(mRowItem.get(position));
        }

        @Override
        public int getItemCount() {
            return mRowItem.size();
        }
    }
}
