package com.bawp.testmall;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.testmall.Adapter.category_adapter;
import com.bawp.testmall.Adapter.home_page_adapter;
import com.bawp.testmall.Adapter.horizontal_product_adapter;
import com.bawp.testmall.Adapter.slider_adapter;
import com.bawp.testmall.model.Home_page_model;
import com.bawp.testmall.model.Horizontal_product_model;
import com.bawp.testmall.model.category_model;
import com.bawp.testmall.model.slider_model;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.bawp.testmall.Data.DBqueries.categoryModelList;
import static com.bawp.testmall.Data.DBqueries.homePageModelList;
import static com.bawp.testmall.Data.DBqueries.loadCat;
import static com.bawp.testmall.Data.DBqueries.loadFullData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private category_adapter categoryAdapter;
    private int current_index=2;

    ////////////BannerSlider
    private ViewPager banner_slider;
    private List<slider_model> sliderModelList= new ArrayList<>();
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;

    /////////////
    /////// Strip Ad layout
    private ImageView Strip_ad;
    private ConstraintLayout Strip_constraint;
    ///////

//    private List<category_model> categoryModelList;
    private FirebaseFirestore firebaseFirestore;

    ///////Horizontal product layout
    private TextView horizontal_title_text;
    private Button full_details;
    private RecyclerView horizontal_recycler_view;
    private horizontal_product_adapter horizontalProductAdapter;

    //////
    private home_page_adapter homePageAdapter;
    private  ImageView no_internet;

    /////grid product view

    /////grid product view

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        no_internet = view.findViewById(R.id.no_internet_conn);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnected()==true) {
            no_internet.setVisibility(View.GONE);
            recyclerView = view.findViewById(R.id.recycle);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);


//        categoryModelList = new ArrayList<category_model>();
//        categoryModelList.add(new category_model("link","Home"));
            categoryAdapter = new category_adapter(categoryModelList);
            recyclerView.setAdapter(categoryAdapter);
            if(categoryModelList.size() == 0){
                loadCat(categoryAdapter,getContext());
            }
            else{
                categoryAdapter.notifyDataSetChanged();
            }

//        firebaseFirestore = FirebaseFirestore.getInstance();
//
//        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
//                                categoryModelList.add(new category_model(queryDocumentSnapshot.get("icon").toString(),queryDocumentSnapshot.get("categoryName").toString()));
//                                categoryAdapter.notifyDataSetChanged();
//
//                            }
//                        }
//                        else
//                        {
//                            String error= task.getException().getMessage();
//                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//                });

//        categoryModelList.add(new category_model("link","Home"));
//        categoryModelList.add(new category_model("@mipmap/","Electronics"));
//        categoryModelList.add(new category_model("link","Fashion"));
//        categoryModelList.add(new category_model("link","Games"));
//        categoryModelList.add(new category_model("link","Drones"));
//        categoryModelList.add(new category_model("link","Opticals"));
//        categoryModelList.add(new category_model("link","Home"));
//        categoryModelList.add(new category_model("link","Home"));
//        categoryModelList.add(new category_model("link","Home"));
//        categoryModelList.add(new category_model("link","Home"));




            //////////////bannerSlider
            banner_slider = view.findViewById(R.id.view_pager);

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

            slider_adapter sliderAdapter = new slider_adapter(sliderModelList);
            banner_slider.setAdapter(sliderAdapter);
            banner_slider.setClipToPadding(false);
            banner_slider.setPageMargin(20);
            banner_slider.setCurrentItem(current_index);


            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    current_index=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state==ViewPager.SCROLL_STATE_IDLE){
//                    page_looper();
                    }
                }
            };
            banner_slider.addOnPageChangeListener(onPageChangeListener);
            Start_banner_Animation();

            banner_slider.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    page_looper();
                    stop_bannaer();
                    if(event.getAction() == MotionEvent.ACTION_UP)
                    {
//                    Start_banner_Animation();
                    }
                    return false;
                }
            });

            /////////////Banner Slider

            /////////Strip_ad
            Strip_ad = view.findViewById(R.id.strip_ad_img);
            Strip_constraint = view.findViewById(R.id.strip_ad_container);
            Strip_ad.setImageResource(R.mipmap.strip);
            Strip_constraint.setBackgroundColor(Color.parseColor("#000000"));

            //////strip_add
            ///////Horizontal product layout
            horizontal_title_text = view.findViewById(R.id.horizontal_scroll_title);
            full_details = view.findViewById(R.id.horizontal_scroll_button);
            horizontal_recycler_view = view.findViewById(R.id.horizontal_scroll_recycler_view);

            List<Horizontal_product_model> horizonatal_product_models = new ArrayList<>();
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.phone,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.ic_launcher,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.cart,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.app,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.myorders,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.myorders,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.myorders,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.phone,"Vivo v19","12 Gb Ram","12000"));
//        horizonatal_product_models.add(new Horizontal_product_model(R.mipmap.phone,"Vivo v19","12 Gb Ram","12000"));
            horizontalProductAdapter  = new horizontal_product_adapter(horizonatal_product_models);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontal_recycler_view.setLayoutManager(linearLayoutManager1);
            horizontal_recycler_view.setAdapter(horizontalProductAdapter);
            horizontalProductAdapter.notifyDataSetChanged();
            ///////Horizontal product layout


            /////grid product view
            TextView grid_title = view.findViewById(R.id.grid_layout_title);
            Button grid_button = view.findViewById(R.id.grid_product_button);
            GridView gridView = view.findViewById(R.id.grid_product_view);

//        gridView.setAdapter(new grid_product_view_adapter(horizonatal_product_models));

            /////grid product view

            ///// Recycler View
            RecyclerView testing = view.findViewById(R.id.test_recycle);
            LinearLayoutManager testinglinearLayoutManager =  new LinearLayoutManager(view.getContext());
            testinglinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            testing.setLayoutManager(testinglinearLayoutManager);
//        final List<Home_page_model> homePageModelList = new ArrayList<>();
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
            homePageAdapter = new home_page_adapter(homePageModelList);
            testing.setAdapter(homePageAdapter);
            if(homePageModelList.size() == 0){
                loadFullData(homePageAdapter,getContext());
            }
            else{
                categoryAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            Glide.with(this).load(R.mipmap.no_internet).into(no_internet);
            no_internet.setVisibility(View.VISIBLE);
        }


//        firebaseFirestore.collection("CATEGORIES").document("HOME")
//                .collection("TOP_DEALS").orderBy("index")
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
//                        if((long) queryDocumentSnapshot.get("view_type") ==0){
//
//                            List<slider_model> sliderModelList = new ArrayList<>();
//                            long no_of_banners = (long)queryDocumentSnapshot.get("no_of_banners");
//                            for(long x=1;x <no_of_banners+1;x++){
//                                sliderModelList.add(new slider_model(queryDocumentSnapshot.get("banner_"+x).toString(),
//                                        queryDocumentSnapshot.get("banner_"+x+"_background").toString()));
//                            }
//                            homePageModelList.add(new Home_page_model(0,sliderModelList));
//                        }else if((long) queryDocumentSnapshot.get("view_type") ==1){
//
//                            homePageModelList.add(new Home_page_model(1,queryDocumentSnapshot.get("strip_ad_banner").toString(),
//                                    queryDocumentSnapshot.get("background").toString()));
//                        }else if((long) queryDocumentSnapshot.get("view_type") ==2){
//
//                            List<Horizontal_product_model> horizontalProductModelList = new ArrayList<>();
//
//                            long no_of_products = (long)queryDocumentSnapshot.get("no_of_products");
//                            for(long x=1;x <no_of_products+1;x++){
//                                horizontalProductModelList.add(new Horizontal_product_model(queryDocumentSnapshot.get("product_ID_"+x).toString()
//                                        ,queryDocumentSnapshot.get("product_image_"+x).toString(),queryDocumentSnapshot.get("product_title_"+x).toString(),
//                                        queryDocumentSnapshot.get("product_subtitle_"+x).toString(),queryDocumentSnapshot.get("product_price_"+x).toString()));
//                            }
//                            homePageModelList.add(new Home_page_model(2,queryDocumentSnapshot.get("layout_title").toString(),queryDocumentSnapshot.get("layout_background").toString(),horizontalProductModelList));
//
//                        }else if((long) queryDocumentSnapshot.get("view_type") ==3){
//                            List<Horizontal_product_model> Gridlayoutlist = new ArrayList<>();
//
//                            long no_of_products = (long)queryDocumentSnapshot.get("no_of_products");
//                            for(long x=1;x <no_of_products+1;x++){
//                                Gridlayoutlist.add(new Horizontal_product_model(queryDocumentSnapshot.get("product_ID_"+x).toString()
//                                        ,queryDocumentSnapshot.get("product_image_"+x).toString(),queryDocumentSnapshot.get("product_title_"+x).toString(),
//                                        queryDocumentSnapshot.get("product_subtitle_"+x).toString(),queryDocumentSnapshot.get("product_price_"+x).toString()));
//                            }
//                            homePageModelList.add(new Home_page_model(3,queryDocumentSnapshot.get("layout_title").toString(),queryDocumentSnapshot.get("layout_background").toString(),Gridlayoutlist));
//
//
//                        }
//                    }
//                    homePageAdapter.notifyDataSetChanged();
//                }
//                else
//                {
//                    String error= task.getException().getMessage();
//                    Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });




        ///// Recycler View
        return view;
    }
    private void page_looper()
    {
//        if(current_index == sliderModelList.size()-2)
//        {
//            current_index=2;
//            banner_slider.setCurrentItem(current_index,false);
//
//        }
//        if(current_index == 1)
//        {
//            current_index=sliderModelList.size()-3;
//            banner_slider.setCurrentItem(current_index,false);
//
//        }
    }
    private void Start_banner_Animation()
    {
        final Handler handler =new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(current_index>=5){
                    current_index=1;
                }
            banner_slider.setCurrentItem(current_index++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }
    private void stop_bannaer()
    {
        timer.cancel();
    }
}
