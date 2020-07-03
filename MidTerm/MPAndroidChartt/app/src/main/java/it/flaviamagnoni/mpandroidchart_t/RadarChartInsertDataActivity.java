package it.flaviamagnoni.mpandroidchart_t;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.RowId;
import java.util.ArrayList;

public class RadarChartInsertDataActivity extends AppCompatActivity {

    private Intent intent;
    private Bundle bundle;
    private ArrayList<RadarChartRow> RowItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart_insert_data);
        RowItem = new ArrayList<>();
        intent = getIntent();
        bundle = new Bundle(intent.getBundleExtra("bundle"));
        new Holder();
    }
    public class Holder implements View.OnClickListener {
        private EditText etRadarChartDataLabel;
        private Button btnRadarChartCreate, btnAddNewDataRadarChart;

        private RecyclerView rvRadarChartInsertData;
        private RadarChartInsertDataActivity.Adapter mRadarChartAdapter;
        private RecyclerView.LayoutManager mRadarChartLayoutManager;
        Holder(){
            for (int i = 0;i<bundle.getStringArrayList("label").size();i++){
                RowItem.add(new RadarChartRow(bundle.getStringArrayList("label").get(i),0));}

            rvRadarChartInsertData = findViewById(R.id.rvRadarChartInsertData);
            rvRadarChartInsertData.setHasFixedSize(true);
            mRadarChartLayoutManager = new LinearLayoutManager(RadarChartInsertDataActivity.this);
            mRadarChartAdapter = new RadarChartInsertDataActivity.Adapter(RowItem);

            rvRadarChartInsertData.setLayoutManager(mRadarChartLayoutManager);
            rvRadarChartInsertData.setAdapter(mRadarChartAdapter);

            etRadarChartDataLabel = findViewById(R.id.etRadarChartDataLabel);
            btnAddNewDataRadarChart = findViewById(R.id.btnAddNewDataRadarChart);
            btnRadarChartCreate = findViewById(R.id.btnRadarChartCreate);

            btnAddNewDataRadarChart.setOnClickListener(this);
            btnRadarChartCreate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnAddNewDataRadarChart:
                    if (etRadarChartDataLabel.getText().toString() != null){
                        InserBundle(etRadarChartDataLabel.getText().toString());
                        Intent intent = new Intent(RadarChartInsertDataActivity.this, RadarChartInsertDataActivity.class);
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                    }
                    break;
                case R.id.btnRadarChartCreate:
                    if (etRadarChartDataLabel.getText().toString() != null){
                        InserBundle(etRadarChartDataLabel.getText().toString());
                        Intent intent = new Intent(RadarChartInsertDataActivity.this, RadarChartActivity.class);
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }

    public void InserBundle(String etRadarChartDataLabel){
        int i = bundle.getInt("i");
        bundle.remove("i");
        ArrayList<Integer> data = new ArrayList<>();
        for (int j = 0; j < bundle.getStringArrayList("label").size(); j++){
            data.add(RowItem.get(j).getValue());
        }
        System.out.println(data);
        String string = "dataSet_" + String.valueOf(i);
        bundle.putIntegerArrayList(string, data);
        String string1 = "labelDataSet_" + String.valueOf(i);
        bundle.putString(string1, etRadarChartDataLabel);
        bundle.putInt("i", i++);
    }

    public static class Adapter extends RecyclerView.Adapter<RadarChartInsertDataActivity.Adapter.ViewHolder> {
        private ArrayList<RadarChartRow> mRowItem;

        public Adapter(ArrayList<RadarChartRow> rowItem) {
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
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            final RadarChartRow currentItem = mRowItem.get(position);

            holder.tvRadarChartDataLabel.setText(currentItem.getLabel());
            holder.etRadarChartDataLabel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    currentItem.setValue(Integer.valueOf(charSequence.toString()));
                    System.out.println(currentItem.getValue());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
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

