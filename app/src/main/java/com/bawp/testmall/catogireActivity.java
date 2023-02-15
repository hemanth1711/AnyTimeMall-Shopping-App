package com.bawp.testmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bawp.testmall.Adapter.home_page_adapter;
import com.bawp.testmall.Adapter.horizontal_product_adapter;
import com.bawp.testmall.model.Home_page_model;
import com.bawp.testmall.model.Horizontal_product_model;
import com.bawp.testmall.model.slider_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.bawp.testmall.Data.DBqueries.homePageModelList;

public class catogireActivity extends AppCompatActivity {
    private RecyclerView categori_recycler_view;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<Home_page_model> homePageModelList = new ArrayList<>();
    home_page_adapter homePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catogire);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String titlee = getIntent().getStringExtra("catogerititle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(titlee);
        categori_recycler_view = findViewById(R.id.cat_recycle);
        LinearLayoutManager testinglinearLayoutManager =  new LinearLayoutManager(this);
        testinglinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categori_recycler_view.setLayoutManager(testinglinearLayoutManager);
        categori_recycler_view.setAdapter(homePageAdapter);

        if(titlee.equals("Mobiles"))
        {
            Toast.makeText(catogireActivity.this,"Mobiles",Toast.LENGTH_SHORT).show();
            firebaseFirestore.collection("CATEGORIES").document("MOBILES")
                    .collection("Top_Mobiles").orderBy("index")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if((long) queryDocumentSnapshot.get("view_type") ==0){

                                List<slider_model> sliderModelList = new ArrayList<>();
                                long no_of_banners = (long)queryDocumentSnapshot.get("no_of_banners");
                                for(long x=1;x <no_of_banners+1;x++){
                                    sliderModelList.add(new slider_model(queryDocumentSnapshot.get("banner_"+x).toString(),
                                            queryDocumentSnapshot.get("banner_"+x+"_background").toString()));
                                }
                                homePageModelList.add(new Home_page_model(0,sliderModelList));
                                homePageAdapter = new home_page_adapter(homePageModelList);
                                categori_recycler_view.setAdapter(homePageAdapter);
                                homePageAdapter.notifyDataSetChanged();
                            }else if((long) queryDocumentSnapshot.get("view_type") ==1){

                                homePageModelList.add(new Home_page_model(1,queryDocumentSnapshot.get("strip_ad_banner").toString(),
                                        queryDocumentSnapshot.get("background").toString()));
                            }else if((long) queryDocumentSnapshot.get("view_type") ==2){

                                List<Horizontal_product_model> horizontalProductModelList = new ArrayList<>();

                                long no_of_products = (long)queryDocumentSnapshot.get("no_of_products");
                                for(long x=1;x <no_of_products+1;x++){
                                    horizontalProductModelList.add(new Horizontal_product_model(queryDocumentSnapshot.get("product_ID_"+x).toString()
                                            ,queryDocumentSnapshot.get("product_image_"+x).toString(),queryDocumentSnapshot.get("product_title_"+x).toString(),
                                            queryDocumentSnapshot.get("product_subtitle_"+x).toString(),queryDocumentSnapshot.get("product_price_"+x).toString()));
                                }
                                homePageModelList.add(new Home_page_model(2,queryDocumentSnapshot.get("layout_title").toString(),queryDocumentSnapshot.get("layout_background").toString(),horizontalProductModelList));

                            }else if((long) queryDocumentSnapshot.get("view_type") ==3){
                                List<Horizontal_product_model> Gridlayoutlist = new ArrayList<>();

                                long no_of_products = (long)queryDocumentSnapshot.get("no_of_products");
                                for(long x=1;x <no_of_products+1;x++){
                                    Gridlayoutlist.add(new Horizontal_product_model(queryDocumentSnapshot.get("product_ID_"+x).toString()
                                            ,queryDocumentSnapshot.get("product_image_"+x).toString(),queryDocumentSnapshot.get("product_title_"+x).toString(),
                                            queryDocumentSnapshot.get("product_subtitle_"+x).toString(),queryDocumentSnapshot.get("product_price_"+x).toString()));
                                }
                                homePageModelList.add(new Home_page_model(3,queryDocumentSnapshot.get("layout_title").toString(),queryDocumentSnapshot.get("layout_background").toString(),Gridlayoutlist));


                            }
                        }
                        homePageAdapter = new home_page_adapter(homePageModelList);
                        categori_recycler_view.setAdapter(homePageAdapter);
                        homePageAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        String error= task.getException().getMessage();
                        Toast.makeText(catogireActivity.this,error,Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
        else
        {
            homePageAdapter = new home_page_adapter(homePageModelList);
            categori_recycler_view.setAdapter(homePageAdapter);
            homePageAdapter.notifyDataSetChanged();
        }


//        List<slider_model> sliderModelList = new ArrayList<>();
//        sliderModelList.add(new slider_model(R.mipmap.wishlist,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.notifications,"#077AE4"));
//
//
//        sliderModelList.add(new slider_model(R.mipmap.cart,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.banner,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.home,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.ic_launcher,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.mylog,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.myorders,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.wishlist,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.notifications,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.cart,"#077AE4"));
//        sliderModelList.add(new slider_model(R.mipmap.app,"#077AE4"));
//



//        List<Horizontal_product_model> horizonatal_product_models = new ArrayList<>();
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.phone,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.ic_launcher,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.cart,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.app,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.myorders,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.myorders,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.myorders,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.phone,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.phone,"Vivo v19","12 Gb Ram","12000"));



//        LinearLayoutManager testinglinearLayoutManager =  new LinearLayoutManager(this);
//        testinglinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        categori_recycler_view.setLayoutManager(testinglinearLayoutManager);



//        homePageModelList.add(new Home_page_model(0,sliderModelList));
//        homePageModelList.add(new Home_page_model(1,R.mipmap.strip,"#000000"));
//        homePageModelList.add(new Home_page_model(2,"Deals Of The Day",horizonatal_product_models));
//        homePageModelList.add(new Home_page_model(3,"Vivo Of The Day",horizonatal_product_models));
//        homePageModelList.add(new Home_page_model(2,"Mobiles of the day",horizonatal_product_models));
//        homePageModelList.add(new Home_page_model(3,"trends Of The Day",horizonatal_product_models));
//        homePageModelList.add(new Home_page_model(2,"Deals Of The Day",horizonatal_product_models));
//        homePageModelList.add(new Home_page_model(3,"trends Of The Day",horizonatal_product_models));
////        homePageModelList.add(new Home_page_model(0,sliderModelList));
//        homePageModelList.add(new Home_page_model(1,R.mipmap.banner,"#ffff00"));
//        homePageModelList.add(new Home_page_model(2,"Deals Of The Day",horizonatal_product_models));
//        homePageModelList.add(new Home_page_model(3,"Deals Of The Day",horizonatal_product_models));
//        homePageModelList.add(new Home_page_model(0,sliderModelList));
//        homePageModelList.add(new Home_page_model(1,R.mipmap.strip,"#000000"));
//
//


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search){
            return true;
        }
        else if (id == android.R.id.home)
        {
            finish();
            return true;
        }
//        else if(id == R.id.notifications)
//        {
//            return true;
//        }
//        else if(id == R.id.cart){
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
