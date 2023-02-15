package com.bawp.testmall.Adapter;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.R;
import com.bawp.testmall.model.cart_item_model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static java.util.logging.Logger.global;

public class Cart_Adapter extends RecyclerView.Adapter {
    private int totalAmount;
    public static List<cart_item_model> cart_item_modelList;

    public Cart_Adapter(List<cart_item_model> cart_item_modelList) {
        this.cart_item_modelList = cart_item_modelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cart_item_modelList.get(position).getType()){
            case 0:
                return cart_item_model.getCart_item();

            case 1:
                return cart_item_model.getTotal_amount();
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType)
        {

            case cart_item_model.cart_item:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item_layout,parent,false);
                return new cartitemViewHolder(view);
            case cart_item_model.total_amount:
                View cart_total_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                return new carttotalitemViewHolder(cart_total_view);
            default:
                return null;

        }
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cart_item_modelList.get(position).getType()){
            case cart_item_model.cart_item:
                String product_ID = cart_item_modelList.get(position).getProduct_ID();
                String resourse = cart_item_modelList.get(position).getProduct_image();
                String title = cart_item_modelList.get(position).getProduct_title();
                String product_price = cart_item_modelList.get(position).getProduct_price();
                Long freecoupons = cart_item_modelList.get(position).getFree_coupons();
                String cutted_price = cart_item_modelList.get(position).getCutted_price();
                Long offers_applied = cart_item_modelList.get(position).getOffers_applied();

                ((cartitemViewHolder)holder).setitemdetails(product_ID,resourse,title,freecoupons,product_price,cutted_price,offers_applied,position);
                break;
            case cart_item_model.total_amount:

                int Totalitemtext=0;
                int Total_item_price=0;
                String DelevaryPrice;
                int SavedPrice=0;

                for(int x=0; x<cart_item_modelList.size();x++){
                    if(cart_item_modelList.get(x).getType()==cart_item_model.cart_item){
                        Totalitemtext++;
                        Total_item_price = Total_item_price + Integer.parseInt(cart_item_modelList.get(x).getProduct_price());
                    }
                }
                if(Total_item_price>500){
                    DelevaryPrice = "FREE";
                    totalAmount = Total_item_price;
                }
                else
                {
                    DelevaryPrice = "60";
                    totalAmount = Total_item_price+60;
                }


//                String Totalitemtext = cart_item_modelList.get(position).getTotal_items();
//                String Total_item_price = cart_item_modelList.get(position).getTotalItemPrice();
//                String DelevaryPrice = cart_item_modelList.get(position).getDelivary_price();
//                String totalAmount = cart_item_modelList.get(position).getTotalAmount();
//                String SavedPrice = cart_item_modelList.get(position).getSavedAmount();
                cart_item_modelList.get(position).setTotal_items(Totalitemtext);
                cart_item_modelList.get(position).setTotalItemPrice(Total_item_price);
                cart_item_modelList.get(position).setDelivaryPrice(DelevaryPrice);
                cart_item_modelList.get(position).setTotalAmount(totalAmount);
                cart_item_modelList.get(position).setSavedAmount(SavedPrice);
                ((carttotalitemViewHolder) holder).setTotal_amount(Totalitemtext,Total_item_price,DelevaryPrice,totalAmount,SavedPrice);


                break;
            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return cart_item_modelList.size();
    }

    class cartitemViewHolder extends RecyclerView.ViewHolder{

        private ImageView productimage;
        private ImageView freeCoupon;
        private TextView ProductTitle;
        private TextView FreeCoupons;
        private TextView productprice;
        private TextView cuttedprice;
        private TextView OffersApplied;
        private TextView CouponsApplied;
        private TextView product_Qty;
        private LinearLayout removeButton;




        public cartitemViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage = itemView.findViewById(R.id.product_image);
            ProductTitle = itemView.findViewById(R.id.product_title);
            freeCoupon = itemView.findViewById(R.id.freecoupen_icon);
            FreeCoupons = itemView.findViewById(R.id.tv_free_coupen);
            productprice = itemView.findViewById(R.id.product_price);
            cuttedprice = itemView.findViewById(R.id.cutted_price);
            OffersApplied = itemView.findViewById(R.id.offers_applied);
            CouponsApplied = itemView.findViewById(R.id.coupens_applied);
            product_Qty = itemView.findViewById(R.id.productQty);
            removeButton = itemView.findViewById(R.id.remove_item_btn);

        }
        private void setitemdetails(String product_ID, String resource, String title, Long freecouponsnumber, String productpricetext, String cuttedpricetext, Long offersapplinednumber, final int position)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(productimage);
            ProductTitle.setText(title);
            if(freecouponsnumber >0 ) {
                freeCoupon.setVisibility(View.VISIBLE);
                FreeCoupons.setVisibility(View.VISIBLE);
                if (freecouponsnumber == 1) {
                    FreeCoupons.setText("Free" + freecouponsnumber + "coupen");
                }
                else{
                    FreeCoupons.setText("Free" + freecouponsnumber + "coupens");

                }
            }
            else {
                freeCoupon.setVisibility(View.INVISIBLE);
                FreeCoupons.setVisibility(View.INVISIBLE);
            }
            productprice.setText(productpricetext);
            cuttedprice.setText(cuttedpricetext);
            if(offersapplinednumber > 0){
                OffersApplied.setVisibility(View.VISIBLE);
                OffersApplied.setText(offersapplinednumber + "Offers Applied");
            }
            else
            {
                OffersApplied.setVisibility(View.INVISIBLE);
            }

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBqueries.removeFromCartList(position,itemView.getContext());
                    Log.d("cart", "onClick: "+position);
                }
            });

        }
    }

    class carttotalitemViewHolder extends RecyclerView.ViewHolder{
        private TextView Total_items;
        private TextView Total_items_price;
        private TextView Delivary_price;
        private TextView Total_amount;
        private TextView Saved_amount;
        private TextView Total_cart_amount;


        public carttotalitemViewHolder(@NonNull View itemView) {
            super(itemView);
            Total_items = itemView.findViewById(R.id.Total_items);
            Total_items_price = itemView.findViewById(R.id.Total_items_price);
            Delivary_price = itemView.findViewById(R.id.Delevary_charge);
            Total_amount = itemView.findViewById(R.id.Total_price);
            Saved_amount = itemView.findViewById(R.id.Saved_amount);
            int total;

        }
        public void setTotal_amount(int Total_item_text,int Total_item_price_text,String Delivery_price_text,int Total_amount_text,int Saved_amount_text){
            Total_items.setText("Price "+Total_item_text);
            Total_items_price.setText("RS."+Total_item_price_text+"/-");
            if(Delivary_price.equals("FREE")) {
                Delivary_price.setText(Delivery_price_text);
            }
            else {
                Delivary_price.setText("RS."+Delivery_price_text+"/-");
            }
            Total_amount.setText("Total Amount is RS."+Total_amount_text+"/-");
            Saved_amount.setText("You Saved RS."+Saved_amount_text+"/-");
//            Total_cart_amount.setText("Rupees " + Total_amount_text);
        }
    }
}
