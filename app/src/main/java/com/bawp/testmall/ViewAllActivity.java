package com.bawp.testmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.bawp.testmall.Adapter.wishlist_adapter;
import com.bawp.testmall.model.Horizontal_product_model;
import com.bawp.testmall.model.wish_list_model;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridView gridView;
   public static List<Horizontal_product_model> horizontalProductModelList ;
   public static List<wish_list_model> wish_list_modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.view_all_recyclerView);
        gridView = findViewById(R.id.grid_view);
        int layout_code = getIntent().getIntExtra("layout_code", -1);
        if (layout_code == 0) {
            gridView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
//            wish_list_modelList.add(new wish_list_model(R.mipmap.phone, "Vivo v19", 2, "3.4", 187, "Rs.49999/-", "Rs.59999/-", "Online"));
//            wish_list_modelList.add(new wish_list_model(R.mipmap.phone, "Vivo v3", 1, "3", 18, "Rs.49999/-", "Rs.59999/-", "cash on delivary"));
//            wish_list_modelList.add(new wish_list_model(R.mipmap.phone, "Vivo v17", 2, "3.4", 187, "Rs.49999/-", "Rs.59999/-", "Online"));
//            wish_list_modelList.add(new wish_list_model(R.mipmap.phone, "Samsung m30s", 0, "4.4", 87, "Rs.49999/-", "Rs.59999/-", "cash on delivary"));
//            wish_list_modelList.add(new wish_list_model(R.mipmap.phone, "Vivo v19", 2, "3.4", 187, "Rs.49999/-", "Rs.59999/-", "Online"));
//            wish_list_modelList.add(new wish_list_model(R.mipmap.phone, "Vivo v15 pro", 4, "3.4", 187, "Rs.49999/-", "Rs.59999/-", "Online"));

            wishlist_adapter adapter = new wishlist_adapter(wish_list_modelList, false);
            recyclerView.setAdapter(adapter);
        } else {
            gridView.setVisibility(View.VISIBLE);
            grid_product_view_adapter gridProductViewAdapter = new grid_product_view_adapter(horizontalProductModelList);
            gridView.setAdapter(gridProductViewAdapter);


        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
