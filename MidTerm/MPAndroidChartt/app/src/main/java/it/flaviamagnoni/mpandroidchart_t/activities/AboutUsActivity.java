package it.flaviamagnoni.mpandroidchart_t.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import it.flaviamagnoni.mpandroidchart_t.R;

public class AboutUsActivity extends AppCompatActivity {
    private Holder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle(getString(R.string.text_about_us_title));

        holder = new Holder(this);
    }

    class Holder implements View.OnClickListener {
        private Context mContext;
        private ImageButton ibtnGithub;

        public Holder(Context context) {
            mContext = context;

            ibtnGithub = findViewById(R.id.ibtnGithub);
            ibtnGithub.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ibtnGithub) {
                Intent intentGithub = new Intent(Intent.ACTION_VIEW);
                intentGithub.setData(Uri.parse("https://github.com/ilfuma88/EndGame"));
                startActivity(intentGithub);
            }
        }
    }
}