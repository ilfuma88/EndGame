package it.ilfuma.rc.restoacasteldileva;

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
import it.ilfuma.rc.restoacasteldileva.ShopAdapter.OnItemClickListener;

public class ShopActivity extends AppCompatActivity {
    private AppShopDatabase db;
    private RecyclerView.LayoutManager mLayoutManager;
    private ShopAdapter mShopAdapter;

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

    public class Holder implements OnItemClickListener {
        Intent intent;
        VolleyShop volleyShop;
        RecyclerView rvShop;
        Holder() {
            rvShop = findViewById(R.id.rvShop);
            intent = getIntent();
            int i = intent.getIntExtra("categoryId", -1);
            if (i > -1) {
                this.volleyShop = new VolleyShop(ShopActivity.this, db) {
                    @Override
                    void fill(List<Shop> snt) {
                        Log.w("CA", "fill");
                        fillList(snt);
                    }

                    private void fillList(List<Shop> snt) {
                        mLayoutManager = new LinearLayoutManager(ShopActivity.this);
                        rvShop.setLayoutManager(mLayoutManager);
                        mShopAdapter = new ShopAdapter(snt);
                        rvShop.setAdapter(mShopAdapter);

                        mShopAdapter.setOnItemClickListener(ShopActivity.Holder.this);

                    }
                };
                volleyShop.searchShopsByCategoryId(String.valueOf(i));
            }
        }

        @Override
        public void onItemClick(int position) {
            mShopAdapter.changeText("Clicked", position);
            mShopAdapter.notifyDataSetChanged();
        }
    }
}
