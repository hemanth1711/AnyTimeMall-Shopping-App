package com.bawp.testmall.model;

public class category_model {

    private String link;
    private String name_icon;

    public category_model(String link, String name_icon) {
        this.link = link;
        this.name_icon = name_icon;
    }

    public category_model() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName_icon() {
        return name_icon;
    }

    public void setName_icon(String name_icon) {
        this.name_icon = name_icon;
    }
}
