package it.ilfuma.rc.restoacasteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Holder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_category);
        setContentView(R.layout.activity_main);

        holder = new Holder(this);
    }

    class Holder implements View.OnClickListener {
        private Context context;
        private Button btnGoToCategories;

        Holder(Context context) {
            this.context = context;
            btnGoToCategories = findViewById(R.id.btnGoToCategories);

            btnGoToCategories.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnGoToCategories) {
                Intent intent = new Intent(context, CategoryActivity.class);
                startActivity(intent);
            }
        }
    }
}