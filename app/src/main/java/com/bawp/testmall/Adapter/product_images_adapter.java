package com.bawp.testmall.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bawp.testmall.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class product_images_adapter extends PagerAdapter {
    private List<String> product_images;

    public product_images_adapter(List<String> product_images) {
        this.product_images = product_images;
    }

    @Override
    public int getCount() {
        return product_images.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView product_image = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(product_images.get(position)).apply(new RequestOptions().placeholder(R.mipmap.hommes)).into(product_image);
        container.addView(product_image,0);
        return product_image;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }


}
