package com.bawp.testmall.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.testmall.R;
import com.bawp.testmall.catogireActivity;
import com.bawp.testmall.model.category_model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class category_adapter extends RecyclerView.Adapter<category_adapter.ViewHolder> {
    List<category_model> categoryModelList;

    public category_adapter(List<category_model> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public category_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catogeries_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull category_adapter.ViewHolder holder, int position) {
        String icon = categoryModelList.get(position).getLink();
        String name_of_icon = categoryModelList.get(position).getName_icon();
        holder.setCatogeriename(name_of_icon,position);
        holder.setcatogeryIcon(icon);


    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryicon;
        private TextView name_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryicon = itemView.findViewById(R.id.catogery_icon);
            name_item = itemView.findViewById(R.id.catogery_name);
        }
        private void setcatogeryIcon(String iconurl){
            if(!iconurl.equals("null"))
            Glide.with(itemView.getContext()).load(iconurl).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(categoryicon);
        }
        private void setCatogeriename(final String name, final int position){
            name_item.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(position!=0) {
                        Intent catogeriintent = new Intent(itemView.getContext(), catogireActivity.class);
                        catogeriintent.putExtra("catogerititle", name);
                        itemView.getContext().startActivity(catogeriintent);
                    }
                }
            });
        }
    }
}
