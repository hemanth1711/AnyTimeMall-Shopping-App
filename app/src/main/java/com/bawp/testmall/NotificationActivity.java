package com.bawp.testmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawp.testmall.Data.DBqueries;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.mail.Quota;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static Notificationadapter notificationadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView= findViewById(R.id.nrecycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        notificationadapter = new Notificationadapter(DBqueries.notificationModelList);
        recyclerView.setAdapter(notificationadapter);
        notificationadapter.notifyDataSetChanged();

    }

//    public class NotificationModel {
//        private String image,body;
//        private boolean readed ;
//
//        public NotificationModel(String image, String body, boolean readed) {
//            this.image = image;
//            this.body = body;
//            this.readed = readed;
//        }
//
//        public String getImage() {
//            return image;
//        }
//
//        public void setImage(String image) {
//            this.image = image;
//        }
//
//        public String getBody() {
//            return body;
//        }
//
//        public void setBody(String body) {
//            this.body = body;
//        }
//
//        public boolean isReaded() {
//            return readed;
//        }
//
//        public void setReaded(boolean readed) {
//            this.readed = readed;
//        }
//    }

//    public class Notificationadapter extends RecyclerView.Adapter<Notificationadapter.ViewHolder>{
//        private List<NotificationModel> notificationModelList;
//
//        public Notificationadapter(List<NotificationModel> notificationModelList) {
//            this.notificationModelList = notificationModelList;
//        }
//
//        @NonNull
//        @Override
//        public Notificationadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(NotificationActivity.this).inflate(R.layout.notification_recycle_item,parent,false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull Notificationadapter.ViewHolder holder, int position) {
//            String image = notificationModelList.get(position).getImage();
//            String text = notificationModelList.get(position).getBody();
//            boolean read = notificationModelList.get(position).isReaded();
//            holder.setdata(image,text,read);
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return notificationModelList.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            private ImageView imageView;
//            private TextView textView;
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.Simple_image);
//                textView = itemView.findViewById(R.id.nrecycle);
//
//            }
//            void setdata(String image, String body, boolean readed)
//            {
//                Glide.with(NotificationActivity.this).load(image).into(imageView);
//                textView.setText(body);
//                if(readed){
//                    textView.setAlpha(0.5f);
//                }
//                else{
//                    textView.setAlpha(1.0f);
//                }
//            }
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
