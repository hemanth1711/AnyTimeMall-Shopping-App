package com.bawp.testmall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawp.testmall.Adapter.wishlist_adapter;
import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.model.wish_list_model;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class My_wishListFragment extends Fragment {
    private RecyclerView my_wishlist_recycle;
    public static wishlist_adapter wishlistAdapter;

    public My_wishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        my_wishlist_recycle = view.findViewById(R.id.Mywish_list_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        my_wishlist_recycle.setLayoutManager(layoutManager);
//        wish_list_modelList.add(new wish_list_model(R.mipmap.phone,"Vivo v19",2,"3.4",187,"Rs.49999/-","Rs.59999/-","Online"));
//        wish_list_modelList.add(new wish_list_model(R.mipmap.phone,"Vivo v3",1,"3",18,"Rs.49999/-","Rs.59999/-","cash on delivary"));
//        wish_list_modelList.add(new wish_list_model(R.mipmap.phone,"Vivo v17",2,"3.4",187,"Rs.49999/-","Rs.59999/-","Online"));
//        wish_list_modelList.add(new wish_list_model(R.mipmap.phone,"Samsung m30s",0,"4.4",87,"Rs.49999/-","Rs.59999/-","cash on delivary"));
//        wish_list_modelList.add(new wish_list_model(R.mipmap.phone,"Vivo v19",2,"3.4",187,"Rs.49999/-","Rs.59999/-","Online"));
//        wish_list_modelList.add(new wish_list_model(R.mipmap.phone,"Vivo v15 pro",4,"3.4",187,"Rs.49999/-","Rs.59999/-","Online"));

        if(DBqueries.wishListModelList.size()==0) {
            DBqueries.WishList.clear();
            DBqueries.loadWishList(getContext(),true);
        }
         wishlistAdapter = new wishlist_adapter(DBqueries.wishListModelList,true);
        my_wishlist_recycle.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return  view;
    }
}
