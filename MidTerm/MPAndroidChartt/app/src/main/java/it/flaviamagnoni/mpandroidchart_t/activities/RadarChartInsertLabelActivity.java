package it.flaviamagnoni.mpandroidchart_t.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.flaviamagnoni.mpandroidchart_t.R;

public class RadarChartInsertLabelActivity extends AppCompatActivity {

    private Holder holder;
    private ArrayList<String> mRadarChartLabelRows;
    private int cntMnuAppInfo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart_insert_label);
        mRadarChartLabelRows = new ArrayList<>();
        holder= new Holder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuAboutUs:
                Intent intentAboutUs = new Intent(RadarChartInsertLabelActivity.this, AboutUsActivity.class);
                startActivity(intentAboutUs);
                return true;
            case R.id.mnuAppInfo:
                if (cntMnuAppInfo == 0) {
                    holder.showPopupWindow(R.id.mnuAppInfo);
                    cntMnuAppInfo += 1;
                }
                return true;
            case R.id.mnuGithub:
                Intent intentGithub = new Intent(Intent.ACTION_VIEW);
                intentGithub.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart"));
                startActivity(intentGithub);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class Holder implements View.OnClickListener, RadarChartInsertLabelActivity.Adapter.OnItemClickListener{
        private EditText etRadarChartLabel;
        private Button btnRadarChartAddLabel, btnRadarChartAddData;

        private RecyclerView mRecyclerViewRadarChart;
        private Adapter mRadarChartAdapter;
        private RecyclerView.LayoutManager mRadarChartLayoutManager;

        private Context mContext;

        private PopupWindow mPopupWindow;
        private TextView tvPopupMain;
        private ImageButton ibtnClosePopup;

        private ConstraintLayout clRcInsertLabel;

        private int mPosition;


        Holder(Context context) {
            mContext = context;

            clRcInsertLabel = findViewById(R.id.clRcInsertLabel);

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
                    String label = etRadarChartLabel.getText().toString();
                    if (label.compareTo("") != 0) {
                        mRadarChartLabelRows.add(label);
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

                case R.id.ibtnClosePopup:
                    mPopupWindow.dismiss(); // Chiudo la finestra di popup
                    btnRadarChartAddData.setEnabled(true);
                    btnRadarChartAddLabel.setEnabled(true);
                    cntMnuAppInfo = 0;
                    break;
            }
        }

        public void showPopupWindow(int id) {
            switch (id) {
                case R.id.mnuAppInfo:
                    btnRadarChartAddLabel.setEnabled(false);
                    btnRadarChartAddData.setEnabled(false);
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);  // Inizializzo una nuova istanza del LayoutInflater service
                    View popupAppInfo = inflater.inflate(R.layout.popup_mnu_app_info, null);    // Gonfio (inflate) il layout "popup_mnu_app_info" all'interno della View popupAppInfo

                    mPopupWindow = new PopupWindow(popupAppInfo, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);   // Inizializzo una nuova istanza di una finestra popup

                    // Imposto un valore di elevation per la finestra di popup (per API >= 21)
                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    mPopupWindow.showAtLocation(clRcInsertLabel, Gravity.CENTER, 0, 0);  // Visualizza la finestra di popup al centro del ConstraintLayout del root

                    ibtnClosePopup = popupAppInfo.findViewById(R.id.ibtnClosePopup);
                    ibtnClosePopup.setOnClickListener(this);
                    tvPopupMain = popupAppInfo.findViewById(R.id.tvPopupMain);
                    tvPopupMain.setText(R.string.text_tvPopup_insert_label_radarChart);
                    break;
            }
        }

        @Override
        public void onDeleteItemClick(int position) {
            mPosition = position;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            // Setting Dialog Title
            alertDialog.setTitle("Confirm Delete?");
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want delete this label?");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_delete);
            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mRadarChartLabelRows.remove(mPosition);
                    mRadarChartAdapter.notifyItemRemoved(mPosition);
                }
            });
            // Setting Negative "NO" Btn
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog
                    Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            alertDialog.show();

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
            public ImageView mImageViewDelete;

            public OnItemClickListener mListener;

            public ViewHolder(View itemView, OnItemClickListener listener) {
                super(itemView);
                mTextViewInsertLabel = itemView.findViewById(R.id.tvRadarChartLabel);
                mImageViewDelete = itemView.findViewById(R.id.ivRadarChartDelete);
                mListener = listener;

                mImageViewDelete.setOnClickListener(this);
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
