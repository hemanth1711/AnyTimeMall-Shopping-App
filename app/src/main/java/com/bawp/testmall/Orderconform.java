package com.bawp.testmall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawp.testmall.Mail.JavaMailAPI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class Orderconform extends AppCompatActivity {
    private TextView Ordertextid;
    private ImageView cnt_btn;
    String Order_id = UUID.randomUUID().toString().substring(0,28);
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderconform);
        cnt_btn = findViewById(R.id.continue_shop_btn);
        Ordertextid = findViewById(R.id.Order_id2);
        String mail = firebaseAuth.getCurrentUser().getEmail().toString().trim();
        String messgae = "Hii this is to conform that your order which you have placed in Any Time mall is conformed and will " +
                "reach you there within 2 to 3 Days you" +
                "Your order Id is "+ Order_id ;
        String subject = " Order Conformation From Any Time Mall";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,messgae);
        javaMailAPI.execute();
        Ordertextid.setText("Order Id: "+ Order_id);
        cnt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orderconform.this,main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
