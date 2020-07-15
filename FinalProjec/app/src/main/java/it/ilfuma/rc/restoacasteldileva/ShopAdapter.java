package it.ilfuma.rc.restoacasteldileva;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ilfuma.rc.restoacasteldileva.Database.Shop;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<Shop> mShops;
    private int shop_view = R.layout.shop_view;

    public ShopAdapter(List<Shop> shops){
        mShops = shops;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(shop_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvShopName.setText(mShops.get(position).shopName);
        holder.tvShopDesctiption.setText(mShops.get(position).shopDescription);
    }

    @Override
    public int getItemCount() {
        return mShops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvShopName, tvShopDesctiption;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvShopDesctiption = itemView.findViewById(R.id.tvShopDesctiption);
        }
    }
}