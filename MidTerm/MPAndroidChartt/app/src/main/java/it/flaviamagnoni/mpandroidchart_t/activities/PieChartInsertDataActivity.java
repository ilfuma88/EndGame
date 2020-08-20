package it.flaviamagnoni.mpandroidchart_t.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.flaviamagnoni.mpandroidchart_t.R;

public class PieChartInsertDataActivity extends AppCompatActivity {

    ArrayList<PieInfo> mPieChartData = new ArrayList<PieInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_insert_data);
        new Holder();
    }


    class Holder implements View.OnClickListener, PieDataAdapter.OnItemClickListener {

        RecyclerView rvInsertPieData;
        EditText etPieDataLabel, etPieDataValue;
        Button btnAddPieData, btnInsertPieDataCrete;
        PieDataAdapter mPieAdapter;
        RecyclerView.LayoutManager linearLayout;

        Holder(){
            rvInsertPieData = findViewById(R.id.rvInsertPieData);
            etPieDataLabel = findViewById(R.id.etPieDataLabel);
            etPieDataValue = findViewById(R.id.etPieDataValue);
            etPieDataValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            btnAddPieData = findViewById(R.id.btnAddPieData);
            btnInsertPieDataCrete = findViewById(R.id.btnCreatePieChart);

            linearLayout = new LinearLayoutManager(PieChartInsertDataActivity.this);
            mPieAdapter = new PieDataAdapter(mPieChartData, this);
            rvInsertPieData.setLayoutManager(linearLayout);
            rvInsertPieData.setAdapter(mPieAdapter);
            rvInsertPieData.setHasFixedSize(true);

            btnAddPieData.setOnClickListener(this);
            btnInsertPieDataCrete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAddPieData:
                    if (etPieDataValue.getText().toString()!= null){
                        if (etPieDataLabel.getText().toString()!= null) {
                            Float val = Float.parseFloat(etPieDataValue.getText().toString());
                            String lab = etPieDataLabel.getText().toString();
                            PieInfo pieInfo = new PieInfo(lab, val);
                            mPieChartData.add(pieInfo);
                            mPieAdapter.notifyDataSetChanged();
                            rvInsertPieData.invalidate();
                            etPieDataLabel.setText("");
                            etPieDataValue.setText("");
                            Log.w("added:", val.toString().concat(lab));
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.tst_no_label, Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), R.string.tst_no_value, Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btnCreatePieChart:
                    Intent i = new Intent(PieChartInsertDataActivity.this, PieChartActivity.class);
                    i.putExtra("arrayValues", mPieChartData);
                    startActivity(i);
                    break;
            }
        }

        @Override
        public void onDeleteItemClick(int position) {
            mPieChartData.remove(position);
            mPieAdapter.notifyItemRemoved(position);
        }
    }

    public static class PieDataAdapter extends RecyclerView.Adapter<PieDataAdapter.ViewHolder> {

        private ArrayList<PieInfo> mDataArray;
        private OnItemClickListener mPieListener;

        public PieDataAdapter(ArrayList<PieInfo> arrayList, OnItemClickListener listener){
            mDataArray = arrayList;
            mPieListener = listener;
        }



        @NonNull
        @Override
        public PieDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.piedata_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(view, mPieListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PieDataAdapter.ViewHolder holder, int position) {
            PieInfo currentItem = mDataArray.get(position);
            holder.tvPieLabel.setText(currentItem.getLabel());
            holder.tvPieValue.setText(Float.toString(currentItem.getValue()));
        }

        @Override
        public int getItemCount() {
            return mDataArray.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvPieLabel, tvPieValue;
            ImageView ivPieDelete;
            Spinner spPieColors;
            OnItemClickListener mListener;

            public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
                super(itemView);
                tvPieLabel = itemView.findViewById(R.id.tvPieLabel);
                tvPieValue = itemView.findViewById(R.id.tvPieValue);
                ivPieDelete = itemView.findViewById(R.id.ivPieDelete);
                spPieColors = itemView.findViewById(R.id.spPieColors);
                mListener = listener;

                ivPieDelete.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mListener.onDeleteItemClick(getAdapterPosition());
            }
        }

        public interface OnItemClickListener{
            void onDeleteItemClick(int position);
        }
    }



}