package com.bawp.testmall;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawp.testmall.model.Horizontal_product_model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class grid_product_view_adapter extends BaseAdapter {
    List<Horizontal_product_model> horizontal_product_models;

    public grid_product_view_adapter(List<Horizontal_product_model> horizontal_product_models) {
        this.horizontal_product_models = horizontal_product_models;
    }

    @Override
    public int getCount() {
        return horizontal_product_models.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final View view;
        if(convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);

            ImageView grid_image = view.findViewById(R.id.hs_product_Image);
            final TextView grid_title = view.findViewById(R.id.hs_product_title);
            TextView grid_description = view.findViewById(R.id.hs_product_des);
            TextView grid_prize = view.findViewById(R.id.hs_product_rate);

            Glide.with(view.getContext()).load(horizontal_product_models.get(position).getProduct_image()).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(grid_image);
            grid_title.setText(horizontal_product_models.get(position).getProduct_title());
            final String Name_of_product = horizontal_product_models.get(position).getProduct_title();
            grid_description.setText(horizontal_product_models.get(position).getProduct_discription());
            grid_prize.setText("Rs."+horizontal_product_models.get(position).getProduct_prize());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(),productdetails.class);
                    intent.putExtra("pro_name",Name_of_product);
                    parent.getContext().startActivity(intent);

                }
            });
        }
        else
        {
            view=convertView;
        }
        return view;
    }
}
