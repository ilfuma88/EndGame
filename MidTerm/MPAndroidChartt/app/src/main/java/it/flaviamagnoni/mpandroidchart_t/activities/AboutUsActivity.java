package it.flaviamagnoni.mpandroidchart_t.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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
        private ImageButton ibtnEndgameGithub;

        public Holder(Context context) {
            mContext = context;

            ibtnEndgameGithub = findViewById(R.id.ibtnEndGameGithub);
            ibtnEndgameGithub.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ibtnEndGameGithub) {
                Intent intentGithub = new Intent(Intent.ACTION_VIEW);
                intentGithub.setData(Uri.parse("https://github.com/ilfuma88/EndGame"));
                startActivity(intentGithub);
            }
        }
    }
}