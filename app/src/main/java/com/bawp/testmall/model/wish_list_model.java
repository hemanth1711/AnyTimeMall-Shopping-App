package com.bawp.testmall.model;

public class wish_list_model {
    private String product_image;
    private String product_title;
    private Long freecoupons;
    private String ratings;
    private long total_ratings;
    private String product_price;
    private String  cutted_price;
    private boolean COD;

    public wish_list_model(String product_image, String product_title, Long freecoupons, String ratings, long total_ratings, String product_price, String cutted_price, boolean COD) {
        this.product_image = product_image;
        this.product_title = product_title;
        this.freecoupons = freecoupons;
        this.ratings = ratings;
        this.total_ratings = total_ratings;
        this.product_price = product_price;
        this.cutted_price = cutted_price;
        this.COD = COD;
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

    public Long getFreecoupons() {
        return freecoupons;
    }

    public void setFreecoupons(Long freecoupons) {
        this.freecoupons = freecoupons;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public long getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(long total_ratings) {
        this.total_ratings = total_ratings;
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

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }
}
