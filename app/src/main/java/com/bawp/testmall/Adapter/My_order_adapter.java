package com.bawp.testmall.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.testmall.R;
import com.bawp.testmall.model.Myorder_item_model;
import com.bawp.testmall.order_detailsActivity;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

public class My_order_adapter extends RecyclerView.Adapter<My_order_adapter.ViewHolder> {
    private List<Myorder_item_model> myorder_item_modelList;

    public My_order_adapter(List<Myorder_item_model> myorder_item_modelList) {
        this.myorder_item_modelList = myorder_item_modelList;
    }

    @NonNull
    @Override
    public My_order_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull My_order_adapter.ViewHolder holder, int position) {
        String Resource = myorder_item_modelList.get(position).getProduct_image();
//        int rating = myorder_item_modelList.get(position).getRating();
        String title = myorder_item_modelList.get(position).getProduct_title();
        String delevary_Status = myorder_item_modelList.get(position).getDelivary_status();
        Date delivary_date = myorder_item_modelList.get(position).getDate();
        holder.setData(Resource,title,delevary_Status,delivary_date);

    }

    @Override
    public int getItemCount() {
        return myorder_item_modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView product_image;
        private ImageView delevary_indicator;
        private TextView producttitle;
        private TextView Delivary_status;
        private LinearLayout ratenowlayout;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.Product_image);
            producttitle = itemView.findViewById(R.id.Product_title);
            delevary_indicator = itemView.findViewById(R.id.order_indicator);
            Delivary_status = itemView.findViewById(R.id.order_date);
            ratenowlayout =itemView.findViewById(R.id.Rate_now_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), order_detailsActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setData(String resource, String title, String delevarydate, Date date){
            Glide.with(itemView.getContext()).load(resource).into(product_image);
            producttitle.setText(title);
            Delivary_status.setText(delevarydate + String.valueOf(date));
            if(delevarydate.equals("cancelled")){
                delevary_indicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
            }
            else{
                delevary_indicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.green)));

            }
//            setRating(rating);
            for(int x=0; x < ratenowlayout.getChildCount();x++)
            {
                final int StarPosition = x;
                ratenowlayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        setRating(StarPosition);
                    }
                });
            }

        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setRating(int starPosition) {
            for(int x=0 ; x<ratenowlayout.getChildCount();x++)
            {
                ImageView startbtn = (ImageView)ratenowlayout.getChildAt(x);
                startbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(x <= starPosition)
                {
                    startbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }

        }
    }
