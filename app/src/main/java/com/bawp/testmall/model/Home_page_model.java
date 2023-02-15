package com.bawp.testmall.model;

import java.util.ArrayList;
import java.util.List;

public class Home_page_model {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int Grid_Layout_view =3;
    private int type;
    private String background_color;

    //////////////Banner Slider

    private List<slider_model> sliderModelList= new ArrayList<>();

    public Home_page_model(int type, List<slider_model> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<slider_model> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<slider_model> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //////////////Banner Slider

    //////Strip Ad
    private String resource;


    public Home_page_model(int type, String resource, String background_color) {
        this.type = type;
        this.resource = resource;
        this.background_color = background_color;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    /////Strip Ad


    private String title;
    private List<Horizontal_product_model> horizontalProductModelList;
    /////Horizontal product view
    private List<wish_list_model> ViewAllList;
    public Home_page_model(int type, String title, String background_color,List<Horizontal_product_model> horizontalProductModelList,List<wish_list_model> ViewAllList) {
        this.ViewAllList = ViewAllList;
        this.background_color = background_color;
        this.type = type;
        this.title = title;
        this.horizontalProductModelList = horizontalProductModelList;
    }

    public List<wish_list_model> getViewAllList() {
        return ViewAllList;
    }

    public void setViewAllList(List<wish_list_model> viewAllList) {
        ViewAllList = viewAllList;
    }
    /////Horizontal product view

    ////////// Grid Layout

    public Home_page_model(int type, String title, String background_color, List<Horizontal_product_model> horizontalProductModelList) {
        this.background_color = background_color;
        this.type = type;
        this.title = title;
        this.horizontalProductModelList = horizontalProductModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Horizontal_product_model> getHorizontalProductModelList() {
        return horizontalProductModelList;
    }

    public void setHorizontalProductModelList(List<Horizontal_product_model> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }






    ////////// Grid Layout


}
