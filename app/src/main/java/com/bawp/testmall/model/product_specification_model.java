package com.bawp.testmall.model;

public class product_specification_model {

    String feature_name;
    String Feature_value;

    public product_specification_model(String feature_name, String feature_value) {
        this.feature_name = feature_name;
        Feature_value = feature_value;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public String getFeature_value() {
        return Feature_value;
    }

    public void setFeature_value(String feature_value) {
        Feature_value = feature_value;
    }
}
