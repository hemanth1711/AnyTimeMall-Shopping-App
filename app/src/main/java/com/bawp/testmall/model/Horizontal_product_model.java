package com.bawp.testmall.model;

public class Horizontal_product_model {
    private String productID;
    private String product_image;
    private String product_title;
    private String product_discription;
    private String product_prize;

    public Horizontal_product_model(String productID,String product_image, String product_title, String product_discription, String product_prize) {
        this.productID = productID;
        this.product_image = product_image;
        this.product_title = product_title;
        this.product_discription = product_discription;
        this.product_prize = product_prize;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getProduct_discription() {
        return product_discription;
    }

    public void setProduct_discription(String product_discription) {
        this.product_discription = product_discription;
    }

    public String getProduct_prize() {
        return product_prize;
    }

    public void setProduct_prize(String product_prize) {
        this.product_prize = product_prize;
    }
}
