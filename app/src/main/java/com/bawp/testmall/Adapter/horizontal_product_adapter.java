package com.bawp.testmall.Adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.testmall.R;
import com.bawp.testmall.model.Horizontal_product_model;
import com.bawp.testmall.productdetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class horizontal_product_adapter extends RecyclerView.Adapter<horizontal_product_adapter.ViewHolder> {
    private List<Horizontal_product_model> horizontalProductModelList;

    public horizontal_product_adapter(List<Horizontal_product_model> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }

    @NonNull
    @Override
    public horizontal_product_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull horizontal_product_adapter.ViewHolder holder, int position) {

       String resource = horizontalProductModelList.get(position).getProduct_image();
       String title = horizontalProductModelList.get(position).getProduct_title();
       String description = horizontalProductModelList.get(position).getProduct_discription();
       String rate = horizontalProductModelList.get(position).getProduct_prize();

       holder.setProductimage(resource);
       holder.product_title.setText(title);
       holder.product_des.setText(description);
       holder.product_rate.setText(rate);

       holder.setproductname(title);

    }

    @Override
    public int getItemCount() {
        if(horizontalProductModelList.size()>8)
        {
            return 8;
        }
        else
        return horizontalProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView product_image;
        private TextView product_title;
        private TextView product_des;
        private TextView product_rate;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.hs_product_Image);
            product_title = itemView.findViewById(R.id.hs_product_title);
            product_des = itemView.findViewById(R.id.hs_product_des);
            product_rate = itemView.findViewById(R.id.hs_product_rate);
        }

        private void setproductname(final String title){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), productdetails.class);
                    intent.putExtra("pro_name",title);
                    itemView.getContext().startActivity(intent);

                }
            });
        }
        private void setProductimage(String resource)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(product_image);
        }
        private void setProduct_title(String title)
        {
            product_title.setText(title);
        }
        private void setProduct_des(String description)
        {
            product_des.setText( description);
        }
        private void setProduct_rate(String rate){
            product_rate.setText(rate);
        }
    }
}
