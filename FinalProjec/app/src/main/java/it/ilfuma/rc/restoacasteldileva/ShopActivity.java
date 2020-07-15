package it.ilfuma.rc.restoacasteldileva;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import it.ilfuma.rc.restoacasteldileva.Database.AppShopDatabase;
import it.ilfuma.rc.restoacasteldileva.Database.Shop;

public class ShopActivity extends AppCompatActivity {
    private AppShopDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        createDB();
        new Holder();
    }

    private void createDB(){
        db = Room.databaseBuilder(getApplicationContext(), AppShopDatabase.class, "shop.db").allowMainThreadQueries().build();
    }

    public class Holder{
        Intent intent;
        VolleyShop volleyShop;
        RecyclerView rvShop;
        Holder(){
            rvShop = findViewById(R.id.rvShop);
            intent = getIntent();
            int i = intent.getIntExtra("categoryId", -1);
            if (i>-1){
                this.volleyShop = new VolleyShop(ShopActivity.this, db) {
                    @Override
                    void fill(List<Shop> snt) {
                        Log.w("CA", "fill");
                        fillList(snt);
                    }

                    private void fillList(List<Shop> snt) {
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShopActivity.this);
                        rvShop.setLayoutManager(layoutManager);
                        ShopAdapter mAdapter = new ShopAdapter(snt);
                        rvShop.setAdapter(mAdapter);
                    }
                };
                volleyShop.searchShopsByCategoryId(String.valueOf(i));
            }
        }
    }
}
