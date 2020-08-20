package it.flaviamagnoni.mpandroidchart_t.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import java.util.ArrayList;

import it.flaviamagnoni.mpandroidchart_t.R;

public class RadarChartInsertLabelActivity extends AppCompatActivity {

    private ArrayList<String> mRadarChartLabelRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart_insert_label);
        mRadarChartLabelRows = new ArrayList<>();
        new Holder();
    }

    public class Holder implements View.OnClickListener, RadarChartInsertLabelActivity.Adapter.OnItemClickListener{
        private EditText etRadarChartLabel;
        private Button btnRadarChartAddLabel, btnRadarChartAddData;

        private RecyclerView mRecyclerViewRadarChart;
        private Adapter mRadarChartAdapter;
        private RecyclerView.LayoutManager mRadarChartLayoutManager;

        Holder() {
            mRecyclerViewRadarChart = findViewById(R.id.rvInsertRadarChart);
            mRecyclerViewRadarChart.setHasFixedSize(true);
            mRadarChartLayoutManager = new LinearLayoutManager(RadarChartInsertLabelActivity.this);
            mRadarChartAdapter = new Adapter(mRadarChartLabelRows, this);

            mRecyclerViewRadarChart.setLayoutManager(mRadarChartLayoutManager);
            mRecyclerViewRadarChart.setAdapter(mRadarChartAdapter);

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
                    if (etRadarChartLabel.getText() != null) {
                        mRadarChartLabelRows.add(etRadarChartLabel.getText().toString());
                        mRadarChartAdapter.notifyDataSetChanged();
                        etRadarChartLabel.setText("");
                    }
                    break;

                case R.id.btnRadarChartAddData:
                    if (mRadarChartLabelRows.size() < 1) {
                        Context context = getApplicationContext();
                        CharSequence text = "Warning: no label inserted!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        break;
                    }
                    Intent intent = new Intent(RadarChartInsertLabelActivity.this, RadarChartInsertDataActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("i", 0);
                    bundle.putStringArrayList("label",mRadarChartLabelRows);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onDeleteItemClick(int position) {
            mRadarChartLabelRows.remove(position);
            mRadarChartAdapter.notifyItemRemoved(position);
        }
    }

    public static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private ArrayList<String> mRowItem;
        private OnItemClickListener mListener;

        public Adapter(ArrayList<String> rowItem, OnItemClickListener onItemClickListener) {
            mRowItem = rowItem;
            mListener = onItemClickListener;
        }


        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView mTextViewInsertLabel;
            public ImageView mImegeViewDelete;

            public OnItemClickListener mListener;

            public ViewHolder(View itemView, OnItemClickListener listener) {
                super(itemView);
                mTextViewInsertLabel = itemView.findViewById(R.id.tvRadarChartLabel);
                mImegeViewDelete = itemView.findViewById(R.id.ivRadarChartDelete);
                mListener = listener;

                mImegeViewDelete.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                mListener.onDeleteItemClick(getAdapterPosition());
            }
        }
        public interface OnItemClickListener{
            void onDeleteItemClick (int  position);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_radarchart_insert_label, parent, false);
            ViewHolder viewHolder = new ViewHolder(view, mListener);
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
