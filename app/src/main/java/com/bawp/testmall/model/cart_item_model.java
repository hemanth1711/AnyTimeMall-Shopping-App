package com.bawp.testmall.model;

public class cart_item_model {
    public static final int cart_item = 0;
    public static final int total_amount = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //cart item
    private String product_ID;
    private String product_image;
    private String product_title;
    private Long free_coupons;
    private String product_price;
    private String cutted_price;
    private Long product_Qty;
    private Long Offers_applied;
    private Long coupens_applied;

    public cart_item_model(int type, String product_ID,String product_image, String product_title, Long free_coupons, String product_price, String cutted_price, Long product_Qty, Long offers_applied, Long coupens_applied) {
        this.type = type;
        this.product_ID = product_ID;
        this.product_image = product_image;
        this.product_title = product_title;
        this.free_coupons = free_coupons;
        this.product_price = product_price;
        this.cutted_price = cutted_price;
        this.product_Qty = product_Qty;
        Offers_applied = offers_applied;
        this.coupens_applied = coupens_applied;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public static int getCart_item() {
        return cart_item;
    }

    public static int getTotal_amount() {
        return total_amount;
    }

    public String getProduct_image() {
        return product_image;
    }


    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public Long getFree_coupons() {
        return free_coupons;
    }

    public void setFree_coupons(Long free_coupons) {
        this.free_coupons = free_coupons;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getCutted_price() {
        return cutted_price;
    }

    public void setCutted_price(String cutted_price) {
        this.cutted_price = cutted_price;
    }

    public Long getProduct_Qty() {
        return product_Qty;
    }

    public void setProduct_Qty(Long product_Qty) {
        this.product_Qty = product_Qty;
    }

    public Long getOffers_applied() {
        return Offers_applied;
    }

    public void setOffers_applied(Long offers_applied) {
        Offers_applied = offers_applied;
    }

    public Long getCoupens_applied() {
        return coupens_applied;
    }

    public void setCoupens_applied(Long coupens_applied) {
        this.coupens_applied = coupens_applied;
    }

    // cart item ended


    // total amount of cart


    private int Total_items,TotalItemPrice,TotalAmount,SavedAmount;
    private String DelivaryPrice;
    public cart_item_model(int type) {
        this.type = type;
    }
    public int getTotal_items() {
        return Total_items;
    }

    public void setTotal_items(int total_items) {
        Total_items = total_items;
    }

    public int getTotalItemPrice() {
        return TotalItemPrice;
    }

    public void setTotalItemPrice(int totalItemPrice) {
        TotalItemPrice = totalItemPrice;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        TotalAmount = totalAmount;
    }

    public int getSavedAmount() {
        return SavedAmount;
    }

    public void setSavedAmount(int savedAmount) {
        SavedAmount = savedAmount;
    }

    public String getDelivaryPrice() {
        return DelivaryPrice;
    }

    public void setDelivaryPrice(String delivaryPrice) {
        DelivaryPrice = delivaryPrice;
    }
}




