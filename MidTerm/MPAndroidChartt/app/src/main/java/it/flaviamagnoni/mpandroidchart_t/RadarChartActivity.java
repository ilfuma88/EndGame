package it.flaviamagnoni.mpandroidchart_t;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;

import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewRadarChart;
    private Adapter mRadarChartAdapter;
    private RecyclerView.LayoutManager mRadarChartLayoutManager;

    private ArrayList<String> mRadarChartLabelRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);
        mRadarChartLabelRows = new ArrayList<>();
        buldView();
        new Holder();
    }

    public class Holder implements View.OnClickListener {
        private EditText etRadarChartLabel;
        private Button btnRadarChartAddLabel, btnRadarChartAddData;

        Holder() {
            etRadarChartLabel = findViewById(R.id.etRadarChartLabel);
            btnRadarChartAddLabel = findViewById(R.id.btnRadarChartAddLabel);
            btnRadarChartAddData = findViewById(R.id.btnRadarChartAddData);

            btnRadarChartAddLabel.setOnClickListener(this);
            btnRadarChartAddData.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRadarChartAddLabel:
                    mRadarChartLabelRows.add(etRadarChartLabel.getText().toString());
                    mRadarChartAdapter.notifyDataSetChanged();
                    etRadarChartLabel.setText("");
                    break;

                case R.id.btnRadarChartAddData:
                    if (mRadarChartLabelRows.size() < 1) {
                        Context context = getApplicationContext();
                        CharSequence text = "Warning: no label inserted!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("i",0);
                    bundle.putStringArrayList("label",mRadarChartLabelRows);
                    Intent intent = new Intent(RadarChartActivity.this, RadarChartInsertDataActivity.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
            }
        }
    }

    public void buldView(){
        mRecyclerViewRadarChart = findViewById(R.id.rvInsertRadarChart);
        mRecyclerViewRadarChart.setHasFixedSize(true);
        mRadarChartLayoutManager = new LinearLayoutManager(this);
        mRadarChartAdapter = new Adapter(mRadarChartLabelRows);

        mRecyclerViewRadarChart.setLayoutManager(mRadarChartLayoutManager);
        mRecyclerViewRadarChart.setAdapter(mRadarChartAdapter);
    }

    public static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private ArrayList<String> mRowItem;


        public Adapter(ArrayList<String> rowItem) {
            mRowItem = rowItem;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextViewInsertLabel;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextViewInsertLabel = itemView.findViewById(R.id.tvRadarChartLabel);
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
            holder.mTextViewInsertLabel.setText(mRowItem.get(position));
        }

        @Override
        public int getItemCount() {
            return mRowItem.size();
        }
    }
}
