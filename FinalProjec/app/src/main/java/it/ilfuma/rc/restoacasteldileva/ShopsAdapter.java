package it.ilfuma.rc.restoacasteldileva;

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

import java.util.List;

import it.ilfuma.rc.restoacasteldileva.Database.Shop;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {
    private List<Shop> mShops;
    private int mInflateView;
    private OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public Shop getShop(int position){
        return mShops.get(position);
    }

    public ShopsAdapter(List<Shop> shops, Context context, int inflateView){
        mShops = shops;
        mContext = context;
        mInflateView = inflateView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(mInflateView, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvShopName.setText(mShops.get(position).shopName);
        holder.tvShopDescription.setText(mShops.get(position).shopDescription);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        ImageRequest stringRequest = new ImageRequest(mShops.get(position).shopLogo, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.ivShopLogo.setImageBitmap(response);
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
        private TextView tvShopName, tvShopDescription;
        private ImageView ivShopLogo;
        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvShopDescription = itemView.findViewById(R.id.tvShopDescription);
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