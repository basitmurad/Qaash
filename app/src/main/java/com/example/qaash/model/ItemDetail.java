package com.example.qaash.model;

public class ItemDetail {
    private String name;
    private String price;
    private int imageUri;

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    public ItemDetail(String name, String price, int imageUri) {
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
    }

    public ItemDetail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
