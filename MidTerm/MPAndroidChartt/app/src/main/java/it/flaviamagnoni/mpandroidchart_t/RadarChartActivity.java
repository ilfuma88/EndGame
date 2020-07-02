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

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
        private ArrayList<RadarChartRow> mRowItem;

        public Adapter(ArrayList<RadarChartRow> rowItem){
            mRowItem = rowItem;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mTextView;
            public ImageView mDeleteImage;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.tvOutput);
                mDeleteImage = itemView.findViewById(R.id.ivDelete);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_iteam, parent, false);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder (ViewHolder holder, int position) {
            mRowItem currentItem = mRowItem.get(position);

            holder.mTextView.setText(currentItem.getTextResource());
            holder.mDeleteImage.setImageResource(currentItem.getImageResource());
        }

        @Override
        public int getItemCount() {
            return mRowItem.size();
        }
    }

}
