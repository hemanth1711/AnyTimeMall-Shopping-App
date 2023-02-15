package com.bawp.testmall.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.My_wishListFragment;
import com.bawp.testmall.R;
import com.bawp.testmall.model.wish_list_model;
import com.bawp.testmall.productdetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class wishlist_adapter extends RecyclerView.Adapter<wishlist_adapter.ViewHolder> {
    List<wish_list_model> wish_list_modelList = new ArrayList<>();
    private Boolean wishlist;

    public wishlist_adapter(List<wish_list_model> wish_list_modelList,Boolean wishlist) {
        this.wish_list_modelList = wish_list_modelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public wishlist_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull wishlist_adapter.ViewHolder holder, int position) {
        String resource = wish_list_modelList.get(position).getProduct_image();
        String title = wish_list_modelList.get(position).getProduct_title();
        long freecouponnumber = wish_list_modelList.get(position).getFreecoupons();
        String average_rating = wish_list_modelList.get(position).getRatings();
        long total_ratings = wish_list_modelList.get(position).getTotal_ratings();
        String price = wish_list_modelList.get(position).getProduct_price();
        String cutted_price = wish_list_modelList.get(position).getCutted_price();
        boolean payment_type = wish_list_modelList.get(position).isCOD();
        holder.setdata(resource,title,freecouponnumber,average_rating,total_ratings,price,cutted_price,payment_type,position);
    }

    @Override
    public int getItemCount() {
        return wish_list_modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productimage;
        private TextView product_title;
        private TextView freecoupons;
        private ImageView coupon_icon;
        private TextView productPrice;
        private TextView cutted_price;
        private TextView payment_method;
        private TextView Rating;
        private View pricecut;
        private  TextView TotalRatings;
        private ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage = itemView.findViewById(R.id.product_image);
            product_title = itemView.findViewById(R.id.product_title);
            freecoupons = itemView.findViewById(R.id.freecoupon);
            coupon_icon = itemView.findViewById(R.id.coupon_icon);
            productPrice = itemView.findViewById(R.id.product_price);
            cutted_price = itemView.findViewById(R.id.cutted_price);
            payment_method = itemView.findViewById(R.id.payment_method);
            Rating = itemView.findViewById(R.id.tv_product_rating_mini_view);
            pricecut = itemView.findViewById(R.id.price_cut);
            TotalRatings = itemView.findViewById(R.id.total_ratings);
            delete = itemView.findViewById(R.id.delete_btn);
        }
        private void setdata(String resource, final String title, long freecouponnumber, String averagerating, long totalratingsnum, String price, String cutprice, boolean payment_type, final int index){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(productimage);
            product_title.setText(title);
            if(freecouponnumber !=0)
            {
                coupon_icon.setVisibility(View.VISIBLE);
                freecoupons.setVisibility(View.VISIBLE);
                if(freecouponnumber==1)
                {
                    freecoupons.setText("free " + freecouponnumber + "coupon");
                }
                else {
                    freecoupons.setText("free " + freecouponnumber + "coupons");
                }
            }
            else {
                coupon_icon.setVisibility(View.INVISIBLE);
                freecoupons.setVisibility(View.INVISIBLE);
            }
            Rating.setText(averagerating);
            TotalRatings.setText(totalratingsnum + "ratings");
            productPrice.setText(price);
            cutted_price.setText(cutprice);
            if(payment_type==true) {
                payment_method.setVisibility(View.VISIBLE);
            }
                else {
                payment_method.setVisibility(View.INVISIBLE);
            }
            if(wishlist){
                delete.setVisibility(View.VISIBLE);
            }
            else{
                delete.setVisibility(View.GONE);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete.setEnabled(false);
                    Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_SHORT).show();
                    DBqueries.removeFromWishList(index,itemView.getContext());
                    DBqueries.wishListModelList.clear();
                    My_wishListFragment.wishlistAdapter.notifyDataSetChanged();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), productdetails.class);
                    intent.putExtra("pro_name",title);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
