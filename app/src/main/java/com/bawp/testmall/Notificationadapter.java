package com.bawp.testmall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Notificationadapter extends RecyclerView.Adapter<Notificationadapter.ViewHolder> {
    private List<NotificationModel> notificationModelList;

    public Notificationadapter(List<NotificationModel> notificationModelList) {
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = notificationModelList.get(position).getImage();
        String text = notificationModelList.get(position).getBody();
        boolean read = notificationModelList.get(position).isReaded();
        holder.setdata(image,text,read);

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Simple_image);
            textView = itemView.findViewById(R.id.text_n);

        }
        private void setdata(String image,String body,boolean readed)
        {
            Glide.with(itemView.getContext()).load(image).into(imageView);
            textView.setText(body);
            if(readed){
                textView.setAlpha(0.5f);
            }
            else{
                textView.setAlpha(1.0f);
            }
        }
    }
}
