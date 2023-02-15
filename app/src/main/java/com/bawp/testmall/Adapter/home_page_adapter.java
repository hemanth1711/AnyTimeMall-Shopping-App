package com.bawp.testmall.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bawp.testmall.R;
import com.bawp.testmall.ViewAllActivity;
import com.bawp.testmall.grid_product_view_adapter;
import com.bawp.testmall.model.Home_page_model;
import com.bawp.testmall.model.Horizontal_product_model;
import com.bawp.testmall.model.slider_model;
import com.bawp.testmall.model.wish_list_model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class home_page_adapter extends RecyclerView.Adapter {

    private List<Home_page_model> homePageModelList;

    public home_page_adapter(List<Home_page_model> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return Home_page_model.BANNER_SLIDER;
            case 1:
                return Home_page_model.STRIP_AD_BANNER;
            case 2:
                return Home_page_model.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return Home_page_model.Grid_Layout_view;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Home_page_model.BANNER_SLIDER:
                View banner_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_view_page, parent, false);
                return new BannersliderViewHolder(banner_view);
            case Home_page_model.STRIP_AD_BANNER:
                View Strip__view = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAddViewHolder(Strip__view);
            case Home_page_model.HORIZONTAL_PRODUCT_VIEW:
                View Horizontal_product_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll, parent, false);
                return new HorizontalProduct(Horizontal_product_view);
            case Home_page_model.Grid_Layout_view:
                View grid_product_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_view, parent, false);
                return new Grid_Product_View_Holder(grid_product_view);

            default:
                return null;
        }
//        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case Home_page_model.BANNER_SLIDER:
                List<slider_model> slider_modelList = homePageModelList.get(position).getSliderModelList();
                ((BannersliderViewHolder) holder).setBanner_slider(slider_modelList);
                break;
            case Home_page_model.STRIP_AD_BANNER:
                String resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackground_color();
                ((StripAddViewHolder) holder).setStripAd(resource, color);
                break;
            case Home_page_model.HORIZONTAL_PRODUCT_VIEW:
                String colorr = homePageModelList.get(position).getBackground_color();
                String title = homePageModelList.get(position).getTitle();
                List<Horizontal_product_model> horizontal_product_models = homePageModelList.get(position).getHorizontalProductModelList();
                List<wish_list_model> ViewAllList = homePageModelList.get(position).getViewAllList();
                ((HorizontalProduct)holder).setHorizontal_product_view(horizontal_product_models,title,colorr,ViewAllList);
                break;
            case Home_page_model.Grid_Layout_view:
                String colorrr = homePageModelList.get(position).getBackground_color();
                String grid_title = homePageModelList.get(position).getTitle();
                List<Horizontal_product_model> grid_product_models = homePageModelList.get(position).getHorizontalProductModelList();
                ((Grid_Product_View_Holder)holder).setGridLayout(grid_product_models,grid_title,colorrr);
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannersliderViewHolder extends RecyclerView.ViewHolder {
        private ViewPager banner_slider;
        private Timer timer;
        private int current_index = 2;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public BannersliderViewHolder(@NonNull View itemView) {
            super(itemView);
            banner_slider = itemView.findViewById(R.id.view_pager);
        }

        private void setBanner_slider(final List<slider_model> sliderModelList) {
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
                    current_index = position;
//                    Toast.makeText(itemView.getContext(),,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        page_looper(sliderModelList);
                    }
                }
            };
            banner_slider.addOnPageChangeListener(onPageChangeListener);
            Start_banner_Animation(sliderModelList);

            banner_slider.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    page_looper(sliderModelList);
                    stop_bannaer();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Start_banner_Animation(sliderModelList);
                    }
                    return false;
                }
            });

        }

        private void page_looper(List<slider_model> sliderModelList) {
            if (current_index == sliderModelList.size() - 2) {
                current_index = 2;
                banner_slider.setCurrentItem(current_index, false);

            }
            if (current_index == 1) {
                current_index = sliderModelList.size() - 3;
                banner_slider.setCurrentItem(current_index, false);

            }
        }

        private void Start_banner_Animation(final List<slider_model> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (current_index >= sliderModelList.size()) {
                        current_index = 1;
                    }
                    banner_slider.setCurrentItem(current_index++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stop_bannaer() {
            timer.cancel();
        }
    }

    public class StripAddViewHolder extends RecyclerView.ViewHolder {
        private ImageView strip_ad;
        private ConstraintLayout strip_container;

        public StripAddViewHolder(@NonNull View itemView) {
            super(itemView);
            strip_ad = itemView.findViewById(R.id.strip_ad_img);
            strip_container = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(strip_ad);
            strip_container.setBackgroundColor(Color.parseColor(color));

        }
    }

    public class HorizontalProduct extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private TextView horizontal_title_text;
        private Button full_details;
        private RecyclerView horizontal_recycler_view;
        private horizontal_product_adapter horizontalProductAdapter;

        public HorizontalProduct(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            horizontal_title_text = itemView.findViewById(R.id.horizontal_scroll_title);
            full_details = itemView.findViewById(R.id.horizontal_scroll_button);
            horizontal_recycler_view = itemView.findViewById(R.id.horizontal_scroll_recycler_view);

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setHorizontal_product_view(List<Horizontal_product_model> horizonatal_product_models, final String title, String color, final List<wish_list_model> ViewAllList) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

            horizontal_title_text.setText(title);

            if(horizonatal_product_models.size()>8){
                full_details.setVisibility(View.VISIBLE);
                full_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wish_list_modelList = ViewAllList;
                        Intent intent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        intent.putExtra("layout_code",0);
                        intent.putExtra("title",title);
                        itemView.getContext().startActivity(intent);
                    }
                });
            }else
            {
                full_details.setVisibility(View.INVISIBLE);
            }

            horizontalProductAdapter = new horizontal_product_adapter(horizonatal_product_models);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontal_recycler_view.setLayoutManager(linearLayoutManager1);
            horizontal_recycler_view.setAdapter(horizontalProductAdapter);
            horizontalProductAdapter.notifyDataSetChanged();
        }
    }

    public class Grid_Product_View_Holder extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView grid_title;
        private Button grid_button;
        private GridView gridView;

        public Grid_Product_View_Holder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.containerr);
             grid_title = itemView.findViewById(R.id.grid_layout_title);
             grid_button = itemView.findViewById(R.id.grid_product_button);
             gridView = itemView.findViewById(R.id.grid_product_view);
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setGridLayout(final List<Horizontal_product_model> horizonatal_product_models, final String title, String color) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            grid_title.setText(title);
            gridView.setAdapter(new grid_product_view_adapter(horizonatal_product_models));
//            for( int x=0 ; x<4 ; x++) {
//                ImageView product_image = gridView.getChildAt(x).findViewById(R.id.hs_product_Image);
////                TextView product_title = gridView.getChildAt(x).findViewById(R.id.hs_product_title);
////                TextView product_description = gridView.getChildAt(x).findViewById(R.id.hs_product_des);
////                TextView product_price = gridView.getChildAt(x).findViewById(R.id.hs_product_rate);
//                Glide.with(itemView.getContext()).load(horizonatal_product_models.get(x)).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(product_image);
////                product_title.setText(horizonatal_product_models.get(x).getProduct_title());
////                product_description.setText(horizonatal_product_models.get(x).getProduct_discription());
////                product_price.setText(horizonatal_product_models.get(x).getProduct_prize());
//
//            }
            grid_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalProductModelList = horizonatal_product_models;
                    Intent intent = new Intent(itemView.getContext(), ViewAllActivity.class);
                    intent.putExtra("layout_code",1);
                    intent.putExtra("title", title);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
