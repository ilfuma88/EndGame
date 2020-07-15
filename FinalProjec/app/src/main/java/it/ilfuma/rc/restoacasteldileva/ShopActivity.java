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

    abstract class VolleyShop implements Response.ErrorListener, Response.Listener<String> {
        abstract void fill(List<Shop> snt);

        void searchShopsByCategoryId(String id) {
            String url = "https://my-json-server.typicode.com/fabiobf96/SimpleJson/shops?categoryId=%s";
            url = String.format(url, id);
            apiCall(url);
        }

        private void apiCall(String url) {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(ShopActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this, this);
            requestQueue.add(stringRequest);
        }
        private void imgCall(String url) {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(ShopActivity.this);
            ImageRequest stringRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // TO DO
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
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            // TO DO
        }

        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            String shops;
            try {
                JSONObject jsonObject = new JSONObject(response);
                shops = jsonObject.getJSONObject("").toString();
                Type listView = new TypeToken<List<Shop>>() {}.getType();
                List<Shop> snt = gson.fromJson(shops, listView);
                if (snt != null && snt.size() > 0) {
                    Log.w("CA", "" + snt.size());
                    db.shopDAO().insertAll(snt);
                    fill(snt);
                }
            } catch (Exception e) {
                //TO DO
            }
        }
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
                this.volleyShop = new VolleyShop() {
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
