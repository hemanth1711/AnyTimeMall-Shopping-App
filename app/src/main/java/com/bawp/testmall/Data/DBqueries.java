package com.bawp.testmall.Data;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bawp.testmall.Adapter.My_order_adapter;
import com.bawp.testmall.Adapter.category_adapter;
import com.bawp.testmall.Adapter.home_page_adapter;
import com.bawp.testmall.My_cart_Fragment;
import com.bawp.testmall.My_wishListFragment;
import com.bawp.testmall.NotificationActivity;
import com.bawp.testmall.NotificationModel;
import com.bawp.testmall.R;
import com.bawp.testmall.model.Home_page_model;
import com.bawp.testmall.model.Horizontal_product_model;
import com.bawp.testmall.model.Myorder_item_model;
import com.bawp.testmall.model.cart_item_model;
import com.bawp.testmall.model.category_model;
import com.bawp.testmall.model.slider_model;
import com.bawp.testmall.model.wish_list_model;
import com.bawp.testmall.productdetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class DBqueries {

    public  static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
     public static List<category_model> categoryModelList = new ArrayList<>();
    public static List<Home_page_model> homePageModelList = new ArrayList<>();
    public static List<String> WishList = new ArrayList<>();
    public  static  List<wish_list_model> wishListModelList = new ArrayList<>();
    public  static  List<NotificationModel> notificationModelList  = new ArrayList<>();
    private static ListenerRegistration registration;
    public static List<String> cartList = new ArrayList<>();
    public  static  List<cart_item_model> cartItemModelList = new ArrayList<>();
    public static  List<Myorder_item_model> myorder_item_models = new ArrayList<>();


    public static void loadCat(final category_adapter categoryAdapter, final Context context){
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                categoryModelList.add(new category_model(queryDocumentSnapshot.get("icon").toString(),queryDocumentSnapshot.get("categoryName").toString()));
                                categoryAdapter.notifyDataSetChanged();

                            }
                        }
                        else
                        {
                            String error= task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
    public static void loadFullData(final home_page_adapter homePageAdapter, final Context context){
        firebaseFirestore.collection("CATEGORIES").document("HOME")
                .collection("TOP_DEALS").orderBy("index")
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
                        }else if((long) queryDocumentSnapshot.get("view_type") ==1){

                            homePageModelList.add(new Home_page_model(1,queryDocumentSnapshot.get("strip_ad_banner").toString(),
                                    queryDocumentSnapshot.get("background").toString()));
                        }else if((long) queryDocumentSnapshot.get("view_type") ==2){
                            List<wish_list_model> ViewAllProductList = new ArrayList<>();
                            List<Horizontal_product_model> horizontalProductModelList = new ArrayList<>();

                            long no_of_products = (long)queryDocumentSnapshot.get("no_of_products");
                            for(long x=1;x <no_of_products+1;x++){
                                horizontalProductModelList.add(new Horizontal_product_model(queryDocumentSnapshot.get("product_ID_"+x).toString()
                                        ,queryDocumentSnapshot.get("product_image_"+x).toString(),queryDocumentSnapshot.get("product_title_"+x).toString(),
                                        queryDocumentSnapshot.get("product_subtitle_"+x).toString(),queryDocumentSnapshot.get("product_price_"+x).toString()));

                                ViewAllProductList.add(new wish_list_model(queryDocumentSnapshot.get("product_image_"+x).toString()
                                        ,queryDocumentSnapshot.get("product_full_title_"+x).toString(),
                                        (long)queryDocumentSnapshot.get("free_coupens_"+x),
                                        queryDocumentSnapshot.get("average_rating_"+x).toString(),
                                        (long)queryDocumentSnapshot.get("total_ratings_"+x),
                                        queryDocumentSnapshot.get("product_price_"+x).toString(),
                                        queryDocumentSnapshot.get("cutted_price_"+x).toString(),
                                        (boolean) queryDocumentSnapshot.get("COD_"+x)));
                            }
                            homePageModelList.add(new Home_page_model(2,queryDocumentSnapshot.get("layout_title").toString(),queryDocumentSnapshot.get("layout_background").toString(),horizontalProductModelList,ViewAllProductList));

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
                    homePageAdapter.notifyDataSetChanged();
                }
                else
                {
                    String error= task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    public static void loadWishList(final Context context,final boolean loadproductdata){
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHLIST").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            for(long x = 0 ; x < (long)task.getResult().get("list_size"); x++){
                                WishList.add(task.getResult().get("product_ID_"+x).toString());
                                if(DBqueries.WishList.contains(productdetails.productID)){
                                    Log.d("called", "onComplete: "+"added "+"got size"+DBqueries.WishList.size());
                                    productdetails.Added_to_wish_list = true;
                                    if(productdetails.Addtowislist!=null) {
                                        productdetails.Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F8021F")));
                                    }
                                }
                                else {
                                    productdetails.Added_to_wish_list = false;
                                    if (productdetails.Addtowislist != null) {
                                        productdetails.Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#807F7F")));
                                    }
                                }
                                if(loadproductdata) {
                                    firebaseFirestore.collection("PRODUCTS").document(task.getResult().get("product_ID_" + x).toString())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                wishListModelList.add(new wish_list_model(task.getResult().get("product_image_1").toString()
                                                        , task.getResult().get("product_title").toString(),
                                                        (long) task.getResult().get("free_coupens"),
                                                        task.getResult().get("average_rating").toString(),
                                                        (long) task.getResult().get("total_ratings"),
                                                        task.getResult().get("product_price").toString(),
                                                        task.getResult().get("cutted_price").toString(),
                                                        (boolean) task.getResult().get("COD")));
                                                My_wishListFragment.wishlistAdapter.notifyDataSetChanged();

                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static void removeFromWishList(final int index, final Context context){
        final String removedProductID = WishList.remove(index);
        if(WishList.size()!=0) {
            WishList.remove(index);
        }
        Map<String,Object> updateWhishList = new HashMap<>();
        for(int x=0;x< WishList.size(); x++){
            updateWhishList.put("product_ID_"+x,WishList.get(x));
        }
        updateWhishList.put("list_size",(long)WishList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHLIST").set(updateWhishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    productdetails.Added_to_wish_list=false;
                    Toast.makeText(context,"Removed successfully",Toast.LENGTH_SHORT).show();
                }else {
                    if(productdetails.Addtowislist!=null) {
                        productdetails.Addtowislist.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F8021F")));
                    }
                    WishList.add(index,removedProductID);
                    String error = task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
                if(productdetails.Addtowislist!=null) {
                    productdetails.Addtowislist.setEnabled(true);
                }
            }
        });
    }
    public static void loadCartList(final Context context, final boolean loadproductdata){
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_CART").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            for(long x = 0 ; x < (long)task.getResult().get("list_size"); x++){
                                cartList.add(task.getResult().get("product_ID_"+x).toString());
                                if(DBqueries.cartList.contains(productdetails.productID)){
//                                    Log.d("called", "onComplete: "+"added "+"got size"+DBqueries.WishList.size());
                                    productdetails.Added_to_cart = true;
                                }
                                else {
                                    productdetails.Added_to_cart = false;
                                }
                                if(loadproductdata) {
                                    firebaseFirestore.collection("PRODUCTS").document(task.getResult().get("product_ID_" + x).toString())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                int index=0;
                                                if(cartList.size()>=2){
                                                    index=cartList.size()-2;
                                                }
                                                cartItemModelList.add(index,new cart_item_model(cart_item_model.cart_item,productdetails.productID,task.getResult().get("product_image_1").toString()
                                                        , task.getResult().get("product_title").toString(),
                                                        (long) task.getResult().get("free_coupens"),
                                                        task.getResult().get("product_price").toString(),
                                                         task.getResult().get("cutted_price").toString(),
                                                        (long)1,
                                                        (long)0,
                                                        (long)0));

                                                if(cartList.size()==1){
                                                    cartItemModelList.add(new cart_item_model(cart_item_model.total_amount));
                                                }
                                                if(cartList.size()==0){
                                                    cartItemModelList.clear();
                                                }
                                                if(My_cart_Fragment.cartAdapter!=null) {
                                                    My_cart_Fragment.cartAdapter.notifyDataSetChanged();
                                                }

                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static void removeFromCartList(final int index, final Context context){
        final String removedProductID = cartList.remove(index);
        if(cartList.size()!=0) {
            cartList.remove(index);
        }
        Map<String,Object> updateCartList = new HashMap<>();
        for(int x=0;x< cartList.size(); x++){
            updateCartList.put("product_ID_"+x,cartList.get(x));
        }
        updateCartList.put("list_size",(long)cartList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_CART").set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(cartItemModelList.size()!=0){
                        cartItemModelList.remove(index);
                        My_cart_Fragment.cartAdapter.notifyDataSetChanged();
                    }
                    if(cartList.size()==0){
                        cartItemModelList.clear();
                    }

                    Toast.makeText(context,"Removed successfully",Toast.LENGTH_SHORT).show();
                }else {
                    cartList.add(index,removedProductID);
                    String error = task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
                if(productdetails.Addtowislist!=null) {
                    productdetails.Addtowislist.setEnabled(true);
                }
            }
        });
    }
    public static void loadNotificationdata(boolean remove){
        if(remove){
            registration.remove();
        }
        else{
            registration =  firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                    .document("MY_Notifications").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if(documentSnapshot!=null && documentSnapshot.exists()){
                                notificationModelList.clear();
                                for(long x = 0 ; x < (long)documentSnapshot.get("list_size"); x++){
                                    notificationModelList.add(new NotificationModel(documentSnapshot.get("image_"+x).toString(),documentSnapshot.get("Body_"+x).toString(),
                                            documentSnapshot.getBoolean("read_"+x)));
                                }
                                if(NotificationActivity.notificationadapter!=null)
                                {
                                    NotificationActivity.notificationadapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }

    }
    public static void loadOrders(final Context context, final My_order_adapter myOrderAdapter){
        myorder_item_models.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_ORDERS")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        firebaseFirestore.collection("ORDERS").document(documentSnapshot.getString("order_id")).
                                collection("order_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot Order_items : task.getResult().getDocuments()){
                                        Myorder_item_model myorder_item_model = new Myorder_item_model(Order_items.getString("productID"),
                                                Order_items.getString("OrderStatus"),Order_items.getString("Address"),
                                                Order_items.getDate("Date"),Order_items.getString("FullName"),
                                                Order_items.getString("productPrice"),Order_items.getString("OrderId"),
                                                Order_items.getString("PaymentMode"),Order_items.getString("Pincode"),
                                                Order_items.getString("UserID"),Order_items.getString("product image"),
                                                Order_items.getString("product title"));
                                        myorder_item_models.add(myorder_item_model);
                                    }
                                    myOrderAdapter.notifyDataSetChanged();
                                }
                                else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
