package it.ilfuma.rc.restoacasteldileva;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import it.ilfuma.rc.restoacasteldileva.Database.Shop;

public class ShopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        new Holder();
    }

    class Holder{
        Intent intent;
        Shop shop;
        ImageView ivLogo;
        TextView tvDescription;
        Holder(){
            intent = getIntent();
            shop = intent.getParcelableExtra("shop");
            ivLogo = findViewById(R.id.ivLogo);
            tvDescription = findViewById(R.id.tvDescription);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            ImageRequest stringRequest = new ImageRequest(shop.shopLogo, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    ivLogo.setImageBitmap(response);
                }
            }, 0, 0,
                    ImageView.ScaleType.CENTER_CROP,
                    Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TO DO
                        }
                    });
            requestQueue.add(stringRequest);
            tvDescription.setText(shop.shopDescription);
        }
    }
}
