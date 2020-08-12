package it.ilfuma.rc.restoacasteldileva;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import it.ilfuma.rc.restoacasteldileva.Database.Shop;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<Shop> mShops;
    private int shop_view = R.layout.shop_view;
    private OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void changeText(String text, int position){
        mShops.get(position).shopName = text;
    }
    public ShopAdapter(List<Shop> shops, Context context){
        mShops = shops;
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(shop_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvShopName.setText(mShops.get(position).shopName);
        holder.tvShopDesctiption.setText(mShops.get(position).shopDescription);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        ImageRequest stringRequest = new ImageRequest(mShops.get(position).shopLogo, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.ivShopLogo.setImageBitmap(response
                );
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
    public int getItemCount() {
        return mShops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvShopName, tvShopDesctiption;
        private ImageView ivShopLogo;
        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvShopDesctiption = itemView.findViewById(R.id.tvShopDesctiption);
            ivShopLogo = itemView.findViewById(R.id.ivShopLogo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
    }
}