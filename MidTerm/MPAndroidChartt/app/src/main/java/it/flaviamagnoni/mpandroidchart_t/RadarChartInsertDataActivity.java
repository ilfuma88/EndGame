package it.flaviamagnoni.mpandroidchart_t;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    public class Holder implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
        private EditText etRadarChartDataLabel;
        private Button btnRadarChartCreate, btnAddNewDataRadarChart;
        private Spinner spinnerColors;

        private RecyclerView rvRadarChartInsertData;
        private RadarChartInsertDataActivity.Adapter mRadarChartAdapter;
        private RecyclerView.LayoutManager mRadarChartLayoutManager;
        Holder(){
            for (int i = 0;i<bundle.getStringArrayList("label").size();i++){
                RowItem.add(new RadarChartRow(bundle.getStringArrayList("label").get(i),0));}

            Integer colors[] = {R.color.col_1_chart,R.color.col_2_chart,R.color.col_3_chart,R.color.col_4_chart,R.color.col_5_chart,R.color.col_6_chart,R.color.col_7_chart,R.color.col_8_chart,R.color.col_9_chart,R.color.col_10_chart};

            rvRadarChartInsertData = findViewById(R.id.rvRadarChartInsertData);
            rvRadarChartInsertData.setHasFixedSize(true);
            mRadarChartLayoutManager = new LinearLayoutManager(RadarChartInsertDataActivity.this);
            mRadarChartAdapter = new RadarChartInsertDataActivity.Adapter(RowItem);

            rvRadarChartInsertData.setLayoutManager(mRadarChartLayoutManager);
            rvRadarChartInsertData.setAdapter(mRadarChartAdapter);

            etRadarChartDataLabel = findViewById(R.id.etRadarChartDataLabel);
            btnAddNewDataRadarChart = findViewById(R.id.btnAddNewDataRadarChart);
            btnRadarChartCreate = findViewById(R.id.btnRadarChartCreate);
            spinnerColors = findViewById(R.id.spinnerColors);

            spinnerColors.setAdapter(new SpinnerAdapter(RadarChartInsertDataActivity.this,R.layout.spinner_color, colors));

            btnAddNewDataRadarChart.setOnClickListener(this);
            btnRadarChartCreate.setOnClickListener(this);
            spinnerColors.setOnItemSelectedListener(this);
        }

        @Override
        public void onClick(View view) {
            String labelData = etRadarChartDataLabel.getText().toString();
            System.out.println(labelData);
            switch (view.getId()){
                case R.id.btnAddNewDataRadarChart:
                    if (labelData != ""){
                        InserBundle(labelData);
                        Intent intent = new Intent(RadarChartInsertDataActivity.this, RadarChartInsertDataActivity.class);
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                    }
                    break;
                case R.id.btnRadarChartCreate:
                    if (labelData != ""){
                        InserBundle(labelData);
                        Intent intent = new Intent(RadarChartInsertDataActivity.this, RadarChartActivity.class);
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                    }
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String setColor = "color_" + String.valueOf(bundle.getInt("i"));
            bundle.putInt(setColor, Integer.parseInt(spinnerColors.getItemAtPosition(i).toString()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            String setColor = "color_" + String.valueOf(bundle.getInt("i"));
            bundle.putInt(setColor, Integer.parseInt(spinnerColors.getItemAtPosition(0).toString()));
        }
    }

    public void InserBundle(String labelData){
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
        bundle.putString(string1, labelData);
        bundle.putInt("i", i+=1);
    }

    public class SpinnerAdapter extends ArrayAdapter {
        private Integer mColors[];
        public SpinnerAdapter(@NonNull Context context,int spinnerColorId, Integer[] colors) {
            super(context, spinnerColorId, colors);
            mColors = colors;
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.spinner_color, parent, false);
            ImageView spinnerLayout= (ImageView) view.findViewById(R.id.spinnerLayout);
            spinnerLayout.setImageResource(mColors[position]);
            return view;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
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
            holder.etRadarChartData.addTextChangedListener(new TextViewListener() {
                @Override
                protected void onTextChanged(String before, String old, String aNew, String after) {
                    // intuitive usation of parametters
                    String completeOldText = before + old + after;
                    String completeNewText = before + aNew + after;

                    // update TextView
                    startUpdates(); // to prevent infinite loop.
                    if (completeNewText == ""){
                        currentItem.setValue(0);
                    }
                    else {
                        currentItem.setValue(Integer.valueOf(completeNewText));
                    }
                    endUpdates();
                }
            });
        }
        @Override
        public int getItemCount() {
            return mRowItem.size();

        }


        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvRadarChartDataLabel;
            public EditText etRadarChartData;


            public ViewHolder(View itemView) {
                super(itemView);
                tvRadarChartDataLabel = itemView.findViewById(R.id.tvRadarChartDataLabel);
                etRadarChartData = itemView.findViewById(R.id.etRadarChartData);
            }
        }
    }
    public abstract static class TextViewListener implements TextWatcher {
        /**
         * Unchanged sequence which is placed before the updated sequence.
         */
        private String _before;

        /**
         * Updated sequence before the update.
         */
        private String _old;

        /**
         * Updated sequence after the update.
         */
        private String _new;

        /**
         * Unchanged sequence which is placed after the updated sequence.
         */
        private String _after;

        /**
         * Indicates when changes are made from within the listener, should be omitted.
         */
        private boolean _ignore = false;

        @Override
        public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            _before = sequence.subSequence(0,start).toString();
            _old = sequence.subSequence(start, start+count).toString();
            _after = sequence.subSequence(start+count, sequence.length()).toString();
        }

        @Override
        public void onTextChanged(CharSequence sequence, int start, int before, int count) {
            _new = sequence.subSequence(start, start+count).toString();
        }

        @Override
        public void afterTextChanged(Editable sequence) {
            if (_ignore)
                return;

            onTextChanged(_before, _old, _new, _after);
        }

        /**
         * Triggered method when the text in the text view has changed.
         * <br/>
         * You can apply changes to the text view from this method
         * with the condition to call {@link #startUpdates()} before any update,
         * and to call {@link #endUpdates()} after them.
         *
         * @param before Unchanged part of the text placed before the updated part.
         * @param old Old updated part of the text.
         * @param aNew New updated part of the text?
         * @param after Unchanged part of the text placed after the updated part.
         */
        protected abstract void onTextChanged(String before, String old, String aNew, String after);

        /**
         * Call this method when you start to update the text view, so it stops listening to it and then prevent an infinite loop.
         * @see #endUpdates()
         */
        protected void startUpdates(){
            _ignore = true;
        }

        /**
         * Call this method when you finished to update the text view in order to restart to listen to it.
         * @see #startUpdates()
         */
        protected void endUpdates(){
            _ignore = false;
        }
    }
}

