package com.bawp.testmall.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.testmall.R;
import com.bawp.testmall.model.product_specification_model;

import java.util.List;

public class product_specification_adapter extends RecyclerView.Adapter<product_specification_adapter.ViewHolder> {

    List<product_specification_model> productSpecificationModels;

    public product_specification_adapter(List<product_specification_model> productSpecificationModels) {
        this.productSpecificationModels = productSpecificationModels;
    }

    @NonNull
    @Override
    public product_specification_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull product_specification_adapter.ViewHolder holder, int position) {
        String featuretitle = productSpecificationModels.get(position).getFeature_name();
        String featuredetail = productSpecificationModels.get(position).getFeature_value();

        holder.set_features(featuretitle,featuredetail);
    }

    @Override
    public int getItemCount() {
        return productSpecificationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView feature_name;
        private TextView feature_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feature_name = itemView.findViewById(R.id.feature_name);
            feature_value = itemView.findViewById(R.id.feature_value);
        }
        private void set_features(String featuretitle, String featuredetail)
        {
            feature_name.setText(featuretitle);
            feature_value.setText(featuredetail);
        }
    }
}
