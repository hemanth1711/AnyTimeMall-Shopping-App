package com.bawp.testmall.model;

import java.util.Date;

public class Myorder_item_model {

    private String productId;
    private String product_image;
    private String product_title;


    private int Rating;
    private String Delivary_status;
    private String Address;
    private Date Date;
    private String FullName;
    private String ProductPrice;
    private String orderID;
    private String Payment_method;
    private String pincode;
    private String userID;

    public Myorder_item_model(String productId, String delivary_status, String address, java.util.Date date, String fullName, String productPrice, String orderID, String payment_method, String pincode, String userID,String product_image,String product_title) {
        this.product_image = product_image;
        this.product_title = product_title;
        this.productId = productId;
        Delivary_status = delivary_status;
        Address = address;
        Date = date;
        FullName = fullName;
        ProductPrice = productPrice;
        this.orderID = orderID;
        Payment_method = payment_method;
        this.pincode = pincode;
        this.userID = userID;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getDelivary_status() {
        return Delivary_status;
    }

    public void setDelivary_status(String delivary_status) {
        Delivary_status = delivary_status;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPayment_method() {
        return Payment_method;
    }

    public void setPayment_method(String payment_method) {
        Payment_method = payment_method;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
