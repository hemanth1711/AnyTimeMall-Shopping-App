package com.bawp.testmall;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.testmall.Adapter.product_images_adapter;
import com.bawp.testmall.Adapter.productdescriptionadapter;
import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.model.cart_item_model;
import com.bawp.testmall.model.wish_list_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class productdetails extends AppCompatActivity {
    private ViewPager product_images_view_pager;
    private TabLayout tab_layout;
    public static FloatingActionButton Addtowislist;
    public static boolean Added_to_wish_list=false;
    public static boolean Added_to_cart=false;
    private TextView product_title;
    private  TextView product_mini_rating;
    private  TextView mini_total_ratings;
    private  TextView ProductPrice;
    private TextView cutted_price;
    private TextView COD;
    private ImageView cod_image;

    //Product description

    private TabLayout productDetailsTabLayout;
    private ViewPager productDescriptionViePager;

    //rating layout
    private LinearLayout ratenowlayout;
    //

    private Button Delivary_buy_now;
    private LinearLayout AddToCart;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();;
    public static String productID;
    private DocumentSnapshot documentSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String product_name = getIntent().getStringExtra("pro_name");
        Toast.makeText(productdetails.this,product_name,Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        product_images_view_pager = findViewById(R.id.product_images_view_pager);
        tab_layout = findViewById(R.id.product_image_indicator);

       AddToCart = findViewById(R.id.Add_to_cart_btn);
        productDescriptionViePager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        Delivary_buy_now = findViewById(R.id.Buy_now_btn);
        product_title = findViewById(R.id.product_image_title);
        product_mini_rating = findViewById(R.id.tv_product_rating_mini_view);
        COD = findViewById(R.id.Tv_cod_available_text);
        cod_image = findViewById(R.id.imageView_cod);
        mini_total_ratings = findViewById(R.id.total_ratings_mini_view);
        ProductPrice = findViewById(R.id.product_price);
        cutted_price = findViewById(R.id.cutted_price);


//        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        if(product_name!=null) {
            if (product_name.equals("Laptop")) {
                productID = "2qTdK26jupHxrEfPUP1b";
            }
            else if(product_name.equals("LG Monitor")){
                productID = "YBhmfYFmLgvxeKkbpBmk";
            }
            else if(product_name.equals("Bass")){
                productID = "HQq3L3ZcT4fkGQSxYNGn";
            }
            else if(product_name.equals("fitbit")){
                productID = "UgIv3cx4zNUStlvzzHJi";
            }
            else if(product_name.equals("Smart Speaker")){
                productID = "hieSQdlDv4axycTjx6uL";
            }
            else {
                productID = "xChr32RFfaVWcDifmEXl";
            }
        }
        else{
            productID = "xChr32RFfaVWcDifmEXl";
        }
        firebaseFirestore.collection("PRODUCTS").document(
               productID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                     documentSnapshot = task.getResult();
                    for(long x =1 ; x< (long)documentSnapshot.get("no_of_product_images")+1 ; x++){

                        productImages.add(documentSnapshot.get("product_image_"+x).toString());
                    }
                    product_images_adapter productImagesAdapter = new product_images_adapter(productImages);
                    product_images_view_pager.setAdapter(productImagesAdapter);

                    product_title.setText(documentSnapshot.get("product_title").toString());
                    product_mini_rating.setText(documentSnapshot.get("average_rating").toString());
                    mini_total_ratings.setText(documentSnapshot.get("total_ratings").toString());
                    ProductPrice.setText("RS."+documentSnapshot.get("product_price").toString());
                    cutted_price.setText("RS."+documentSnapshot.get("cutted_price").toString());

                    if(DBqueries.WishList.size()==0){
                        Log.d("called", "onComplete: "+"got size"+DBqueries.WishList.size());
                        DBqueries.WishList.clear();
                        DBqueries.loadWishList(productdetails.this,false);
                    }

                    if(DBqueries.cartList.size()==0){
                        DBqueries.loadCartList(productdetails.this,false);
                    }

                    if((boolean)documentSnapshot.get("COD")){
                        cod_image.setVisibility(View.VISIBLE);
                        COD.setVisibility(View.VISIBLE);
                    }
                    else {
                        cod_image.setVisibility(View.INVISIBLE);
                        COD.setVisibility(View.INVISIBLE);
                    }
//                    if(DBqueries.WishList.size()==0){
//                        DBqueries.WishList.clear();
//                        DBqueries.loadWishList(productdetails.this);
//                        Log.d("called", "onComplete: "+"got size"+DBqueries.WishList.size());
//                    }

                    if(DBqueries.cartList.contains(productID)){
//                        Log.d("called", "onComplete: "+"added "+"got size"+DBqueries.WishList.size());
                        Added_to_cart = true;
                    }
                    else {
                        Added_to_cart =false;
                    }

                    if(DBqueries.WishList.contains(productID)){
                        Log.d("called", "onComplete: "+"added "+"got size"+DBqueries.WishList.size());
                        Added_to_wish_list = true;
                        Addtowislist.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                    }
                    else {
                        Added_to_wish_list =false;
                        Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#807F7F")));
                    }

                }
                else {

                    String error = task.getException().getMessage();
                    Toast.makeText(productdetails.this,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

//        product_images_adapter productImagesAdapter = new product_images_adapter(productImages);
//        product_images_view_pager.setAdapter(productImagesAdapter);
        tab_layout.setupWithViewPager(product_images_view_pager,true);
        Addtowislist = findViewById(R.id.add_to_wish_list);
        Addtowislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBqueries.WishList.contains(productID)){
                    Log.d("called", "onClick: "+"second added");
                    Added_to_wish_list = true;
                    Addtowislist.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
                Addtowislist.setEnabled(false);
                    if(Added_to_wish_list)
                    {
                        int index = DBqueries.WishList.indexOf(productID);
                        DBqueries.removeFromWishList(index,productdetails.this);
//                        Added_to_wish_list=false;
                        Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#807F7F")));
                    }
                    else{
                        Map<String,Object> add_product = new HashMap<>();
                        add_product.put("product_ID_"+ String.valueOf(DBqueries.WishList.size()),productID);
                        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                .update(add_product).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Map<String,Object> updateListSize = new HashMap<>();
                                    updateListSize.put("list_size", (long) (DBqueries.WishList.size()+1));
                                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                            .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                if(DBqueries.wishListModelList.size()!=0){
                                                    DBqueries.wishListModelList.add(new wish_list_model(documentSnapshot.get("product_image_1").toString()
                                                            ,documentSnapshot.get("product_title").toString(),
                                                            (long)documentSnapshot.get("free_coupens"),
                                                            documentSnapshot.get("average_rating").toString(),
                                                            (long)documentSnapshot.get("total_ratings"),
                                                            documentSnapshot.get("product_price").toString(),
                                                            documentSnapshot.get("cutted_price").toString(),
                                                            (boolean) documentSnapshot.get("COD")));
                                                }
                                                Added_to_wish_list=true;
                                                Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.rgb(255,0,0)));
                                                DBqueries.WishList.add(productID);
                                                Toast.makeText(productdetails.this,"added to wish list",Toast.LENGTH_SHORT).show();
                                            }else
                                            {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(productdetails.this,error,Toast.LENGTH_SHORT).show();
                                                Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#807F7F")));
                                            }
                                            Addtowislist.setEnabled(true);
                                        }
                                    });
                                }else
                                {
                                    Addtowislist.setEnabled(true);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(productdetails.this,error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
            }
        });

        productDescriptionViePager.setAdapter(new productdescriptionadapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount()));

        productDescriptionViePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDescriptionViePager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ratenowlayout =findViewById(R.id.Rate_now_container);
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

        Delivary_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DelevaryActivity.CartItemList!=null){
                    DelevaryActivity.CartItemList.clear();
                }
                DelevaryActivity.CartItemList = new ArrayList<>();
                DelevaryActivity. CartItemList.add(new cart_item_model(cart_item_model.cart_item,productID,documentSnapshot.get("product_image_1").toString()
                        , documentSnapshot.get("product_title").toString(),
                        (long) documentSnapshot.get("free_coupens"),
                        documentSnapshot.get("product_price").toString(),
                        documentSnapshot.get("cutted_price").toString(),
                        (long)1,
                        (long)0,
                        (long)0));
                DelevaryActivity.CartItemList.add(new cart_item_model(cart_item_model.total_amount));
              Intent intent = new Intent(productdetails.this,AddAddressActivity.class);
              startActivity(intent);
            }
        });

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Added_to_cart)
                {
//                    int index = DBqueries.WishList.indexOf(productID);
//                    DBqueries.removeFromWishList(index,productdetails.this);
////                        Added_to_wish_list=false;
//                    Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#807F7F")));
                    Toast.makeText(productdetails.this,"Already Added To Cart",Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String,Object> add_product = new HashMap<>();
                    add_product.put("product_ID_"+ String.valueOf(DBqueries.cartList.size()),productID);
                    add_product.put("list_size", (long) (DBqueries.cartList.size()+1));
                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                            .update(add_product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
//                                Map<String,Object> updateListSize = new HashMap<>();
//                                updateListSize.put("list_size", (long) (DBqueries.cartList.size()+1));
//                                firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
//                                        .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful()){

                                            if(DBqueries.cartItemModelList.size()!=0){
                                                DBqueries. cartItemModelList.add(new cart_item_model(cart_item_model.cart_item,productID,documentSnapshot.get("product_image_1").toString()
                                                        , documentSnapshot.get("product_title").toString(),
                                                        (long) documentSnapshot.get("free_coupens"),
                                                        documentSnapshot.get("product_price").toString(),
                                                        documentSnapshot.get("cutted_price").toString(),
                                                        (long)1,
                                                        (long)0,
                                                        (long)0));
                                            }
                                            Added_to_cart=true;
//                                            Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.rgb(255,0,0)));
                                            DBqueries.cartList.add(productID);
                                            Toast.makeText(productdetails.this,"added to cart ",Toast.LENGTH_SHORT).show();
                                        }else
                                        {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(productdetails.this,error,Toast.LENGTH_SHORT).show();
                                            Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#807F7F")));
                                        }
                                        Addtowislist.setEnabled(true);
                                    }
                                });
//                            }else
//                            {
//                                Addtowislist.setEnabled(true);
//                                String error = task.getException().getMessage();
//                                Toast.makeText(productdetails.this,error,Toast.LENGTH_SHORT).show();
//                            }
                        }
//                    });

                }
//            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        else if(id == R.id.search)
        {
            return true;
        }
        else if(id == R.id.cart){
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.frame_Layout);
            if(fragment!=null)
            {
                fragment = new My_cart_Fragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_Layout,fragment)
                        .commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
