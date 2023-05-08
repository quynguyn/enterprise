package com.enterprise.enterprise.DataWeb;

public class GearVNData {
    String name;
    Float price;
    String imgURL;

    
    public GearVNData() {
        this.name = "";
        this.price = 0.f;
        this.imgURL = "";
    }

    public GearVNData(String name, Float price, String imgURL) {
        this.name = name;
        this.price = price;
        this.imgURL = imgURL;
    }
}
