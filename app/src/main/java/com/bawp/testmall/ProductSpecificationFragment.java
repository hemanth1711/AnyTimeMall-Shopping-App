package com.bawp.testmall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawp.testmall.Adapter.product_specification_adapter;
import com.bawp.testmall.model.product_specification_model;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {
    private RecyclerView product_specification_recyle;

    public ProductSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);
        product_specification_recyle = view.findViewById(R.id.product_specification_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        product_specification_recyle.setLayoutManager(linearLayoutManager);
        List<product_specification_model> product_specification_models = new ArrayList<>();
        product_specification_models.add(new product_specification_model("Ram","4gb"));
        product_specification_models.add(new product_specification_model("Ram","4gb"));
        product_specification_models.add(new product_specification_model("Ram","4gb"));
        product_specification_models.add(new product_specification_model("Ram","4gb"));
        product_specification_models.add(new product_specification_model("Ram","4gb"));
        product_specification_models.add(new product_specification_model("Ram","4gb"));
        product_specification_adapter product_specification_adapter = new product_specification_adapter(product_specification_models);
        product_specification_recyle.setAdapter(product_specification_adapter);
        product_specification_adapter.notifyDataSetChanged();
        return view;
    }
}
