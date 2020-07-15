package it.ilfuma.rc.restoacasteldileva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
    private String[] mCategories;
    private Integer[] mImgCategories;
    private LayoutInflater mLayoutInflater;
    private int mViewInflater = R.layout.category_view;

    public CategoryAdapter(Context context, String[] categories, Integer[] imgCategories){
        mCategories = categories;
        mImgCategories = imgCategories;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mCategories.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewInflater, parent, false);

        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        ImageView ivCategory = convertView.findViewById(R.id.ivCategory);

        tvCategory.setText(mCategories[position]);
        ivCategory.setImageResource(mImgCategories[position]);

        return convertView;
    }
}
