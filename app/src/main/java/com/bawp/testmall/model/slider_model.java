package com.bawp.testmall.model;

public class slider_model {
    private String url;
    private String color_string;

    public slider_model(String url, String color_string) {
        this.url = url;
        this.color_string = color_string;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColor_string() {
        return color_string;
    }

    public void setColor_string(String color_string) {
        this.color_string = color_string;
    }
}
