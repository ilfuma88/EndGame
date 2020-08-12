package it.ilfuma.rc.restoacasteldileva;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import it.ilfuma.rc.restoacasteldileva.Database.AppShopDatabase;
import it.ilfuma.rc.restoacasteldileva.Database.Shop;
import it.ilfuma.rc.restoacasteldileva.ShopsAdapter.OnItemClickListener;

public class ShopsActivity extends AppCompatActivity {
    private AppShopDatabase db;
    private RecyclerView.LayoutManager mLayoutManager;
    private ShopsAdapter mShopsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        createDB();
        new Holder();
    }

    private void createDB(){
        db = Room.databaseBuilder(getApplicationContext(), AppShopDatabase.class, "shop.db").allowMainThreadQueries().build();
    }

    public class Holder implements OnItemClickListener {
        Intent intent;
        VolleyShop volleyShop;
        RecyclerView rvShop;
        Context context;
        Holder() {
            context = getApplicationContext();
            rvShop = findViewById(R.id.rvShop);
            intent = getIntent();
            int i = intent.getIntExtra("categoryId", -1);
            if (i > -1) {
                this.volleyShop = new VolleyShop(ShopsActivity.this, db) {
                    @Override
                    void fill(List<Shop> snt) {
                        Log.w("CA", "fill");
                        fillList(snt);
                    }

                    void fillList(List<Shop> snt) {
                        mLayoutManager = new LinearLayoutManager(ShopsActivity.this);
                        rvShop.setLayoutManager(mLayoutManager);
                        mShopsAdapter = new ShopsAdapter(snt, context, R.layout.shop_view);
                        rvShop.setAdapter(mShopsAdapter);

                        mShopsAdapter.setOnItemClickListener(ShopsActivity.Holder.this);

                    }
                };
                volleyShop.searchShopsByCategoryId(String.valueOf(i));
            }
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(ShopsActivity.this, ShopActivity.class);
            intent.putExtra("shop", mShopsAdapter.getShop(position));
            startActivity(intent);
        }
    }
}
