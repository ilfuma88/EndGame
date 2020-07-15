package it.ilfuma.rc.restoacasteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Holder();
    }
    private class Holder implements View.OnClickListener {
        private Button btnCategories;
        Holder(){
            btnCategories = findViewById(R.id.btnCategories);
            btnCategories.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
        }
    }
}