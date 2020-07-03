package it.flaviamagnoni.mpandroidchart_t;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RadarChartInsertDataActivity extends AppCompatActivity {

    private Intent intent;
    private ArrayList<String> label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart_insert_data);
        label = new ArrayList<>();
        intent = getIntent();
        label.addAll(intent.getStringArrayListExtra("label"));
        new Holder();
    }
    public class Holder{
        private EditText etRadarChartDataLabel;
        private Button btnRadarChartCreate;

        private RecyclerView rvRadarChartInsertData;
        private RadarChartInsertDataActivity.Adapter mRadarChartAdapter;
        private RecyclerView.LayoutManager mRadarChartLayoutManager;
        Holder(){
            rvRadarChartInsertData = findViewById(R.id.rvRadarChartInsertData);
            rvRadarChartInsertData.setHasFixedSize(true);
            mRadarChartLayoutManager = new LinearLayoutManager(RadarChartInsertDataActivity.this);
            mRadarChartAdapter = new RadarChartInsertDataActivity.Adapter(label);

            rvRadarChartInsertData.setLayoutManager(mRadarChartLayoutManager);
            rvRadarChartInsertData.setAdapter(mRadarChartAdapter);

            etRadarChartDataLabel = findViewById(R.id.etRadarChartLabel);
            btnRadarChartCreate = findViewById(R.id.btnRadarChartCreate);
        }
    }

    public static class Adapter extends RecyclerView.Adapter<RadarChartInsertDataActivity.Adapter.ViewHolder> {
        private ArrayList<String> mRowItem;

        public Adapter(ArrayList<String> rowItem) {
            mRowItem = rowItem;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_radarchart_insert_data, parent, false);
            RadarChartInsertDataActivity.Adapter.ViewHolder viewHolder = new RadarChartInsertDataActivity.Adapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvRadarChartDataLabel.setText(mRowItem.get(position));
        }

        @Override
        public int getItemCount() {
            return mRowItem.size();

        }


        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvRadarChartDataLabel;
            public EditText etRadarChartDataLabel;


            public ViewHolder(View itemView) {
                super(itemView);
                tvRadarChartDataLabel = itemView.findViewById(R.id.tvRadarChartDataLabel);
                etRadarChartDataLabel = itemView.findViewById(R.id.etRadarChartDataLabel);
            }
        }
    }
}

