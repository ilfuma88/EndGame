package it.ilfuma.rc.restoacasteldileva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class Category extends AppCompatActivity{
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_category);
            new Holder();
        }

        private class Holder implements AdapterView.OnItemClickListener {

            private GridView gvCategories;

            private String[] textCategorie;
            private Integer[] imgCategorie;

            Holder() {
                textCategorie = new String[]{"Abbigliamento", "Autoricambi", "Bar/Tavola Calda", "CAF", "Cartoleria", "Centro Estetico", "Cialde/Capsule",
                        "dentista", "Edilizia/Ferramenta", "Fisioterapia", "Gioielleria", "Ristornate/Pizzeria"};

                imgCategorie = new Integer[]{R.drawable.negozi_abbigliamento, R.drawable.autoricambi, R.drawable.bar_tavolacalda, R.drawable.caf,
                        R.drawable.cartoleria, R.drawable.centro_estetico, R.drawable.cialde_capsule, R.drawable.dentista,
                        R.drawable.edilizia_ferramenta, R.drawable.fisioterapia, R.drawable.gioielleria,
                        R.drawable.ristorante_pizzeria};

                gvCategories = findViewById(R.id.gvCategories);

                CategoryAdapter categoryAdapter = new CategoryAdapter(Category.this, textCategorie, imgCategorie);
                gvCategories.setAdapter(categoryAdapter);

                gvCategories.setOnItemClickListener(this);
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Intent intent = new Intent(Category.this, ShopActivity.class);
                intent.putExtra("categoryId", position +1);
                startActivity(intent);
            }
        }
    }