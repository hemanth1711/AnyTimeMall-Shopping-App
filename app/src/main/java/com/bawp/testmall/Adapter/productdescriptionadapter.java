package com.bawp.testmall.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bawp.testmall.ProductDescriptionFragment;
import com.bawp.testmall.ProductSpecificationFragment;

public class productdescriptionadapter extends FragmentPagerAdapter {
    private int totaltabs;
    public productdescriptionadapter(@NonNull FragmentManager fm ,int totaltabs) {
        super(fm);
        this.totaltabs=totaltabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                return productDescriptionFragment1;

            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                return productSpecificationFragment;

            case 2:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                return productDescriptionFragment2;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
