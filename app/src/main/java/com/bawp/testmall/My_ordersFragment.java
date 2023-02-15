package com.bawp.testmall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawp.testmall.Adapter.My_order_adapter;
import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.model.Myorder_item_model;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class My_ordersFragment extends Fragment {
    private RecyclerView my_order_recyler;

    public My_ordersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        my_order_recyler = view.findViewById(R.id.My_orders_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        my_order_recyler.setLayoutManager(layoutManager);
        My_order_adapter my_order_adapter= new My_order_adapter(DBqueries.myorder_item_models);
        my_order_recyler.setAdapter(my_order_adapter);
        if(DBqueries.myorder_item_models.size() == 0){
            DBqueries.loadOrders(getContext(),my_order_adapter);
        }
        my_order_adapter.notifyDataSetChanged();

        return view;
    }
}
