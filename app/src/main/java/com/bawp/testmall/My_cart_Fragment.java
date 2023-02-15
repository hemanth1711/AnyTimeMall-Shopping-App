package com.bawp.testmall;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bawp.testmall.Adapter.Cart_Adapter;
import com.bawp.testmall.Adapter.wishlist_adapter;
import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.model.cart_item_model;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class My_cart_Fragment extends Fragment {

    public My_cart_Fragment() {
        // Required empty public constructor
    }
    private RecyclerView cart_recycler_view;
    private Button countiue;
    public static Cart_Adapter cartAdapter;
    private TextView cart_total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_my_cart, container, false);
        cart_recycler_view = view.findViewById(R.id.Cart_items_recycler_view);
        countiue = view.findViewById(R.id.cart_continue_btn);
        cart_total = view.findViewById(R.id.Total_cart_amount);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cart_recycler_view.setLayoutManager(layoutManager);

        if(DBqueries.cartItemModelList.size()==0) {
            DBqueries.cartList.clear();
            DBqueries.loadCartList(getContext(),true);
        }

//        List<cart_item_model> cart_item_modelList = new ArrayList<>();
//
////        cart_item_modelList.add(new cart_item_model(0,R.mipmap.phone,"Vivo v19",2,"Rs.49999/-","Rs.59999/-",1,0,0));
////        cart_item_modelList.add(new cart_item_model(0,R.mipmap.phone,"Vivo v19",2,"Rs.49999/-","Rs.59999/-",1,0,0));
////        cart_item_modelList.add(new cart_item_model(0,R.mipmap.phone,"Vivo v19",2,"Rs.49999/-","Rs.59999/-",1,0,0));
//        cart_item_modelList.add(new cart_item_model(1,"Vivo v19","Rs.9999/-","Free","Rs.169999/-","Rs.9999/-"));


         cartAdapter = new Cart_Adapter(DBqueries.cartItemModelList);
        cart_recycler_view.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        int Totalitemtext=0;
        int Total_item_price=0;
        int totalAmount;


        for(int x=0; x<Cart_Adapter.cart_item_modelList.size();x++){
            if(Cart_Adapter.cart_item_modelList.get(x).getType()==cart_item_model.cart_item){
                Totalitemtext++;
                Total_item_price = Total_item_price + Integer.parseInt(Cart_Adapter.cart_item_modelList.get(x).getProduct_price());
            }
        }
        if(Total_item_price>500){
            totalAmount = Total_item_price;
        }
        else
        {
            totalAmount = Total_item_price+60;
        }
        cart_total.setText("Rupees "+String.valueOf(Total_item_price));

        countiue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DelevaryActivity.CartItemList!=null){
                    DelevaryActivity.CartItemList.clear();
                }
                DelevaryActivity.CartItemList = new ArrayList<>();
                for(int x=0 ; x<DBqueries.cartItemModelList.size();x++){
                    cart_item_model Cart_item = DBqueries.cartItemModelList.get(x);
                    DelevaryActivity.CartItemList.add(Cart_item);
                }
                DelevaryActivity.CartItemList.add(new cart_item_model(cart_item_model.total_amount));
                Intent intent = new Intent(view.getContext(),DelevaryActivity.class);
                view.getContext().startActivity(intent);
            }
        });


        return view;
    }
}
