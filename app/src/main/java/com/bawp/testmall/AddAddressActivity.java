package com.bawp.testmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAddressActivity extends AppCompatActivity {
    private Button saveaaddressbtn;
    private EditText City;
    private EditText Full_Address;
    private EditText pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Set Address");
        saveaaddressbtn = findViewById(R.id.save_address);
        City = findViewById(R.id.address_fulname);
        Full_Address = findViewById(R.id.full_address);
        pincode = findViewById(R.id.pincode);
        saveaaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city_name = City.getText().toString().trim();
                String FullAddress = Full_Address.getText().toString().trim();
                String PinCode = pincode.getText().toString().trim();
                if(City!=null && Full_Address!=null && pincode!=null){
                    saveaaddressbtn.setEnabled(true);
                    Intent intent = new Intent(AddAddressActivity.this,DelevaryActivity.class);
                    intent.putExtra("city", city_name);
                    intent.putExtra("Full_Address",FullAddress);
                    intent.putExtra("pincode",PinCode);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddAddressActivity.this,"enter the missing fields ",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
