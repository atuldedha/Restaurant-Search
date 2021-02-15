package com.example.hungry;

public class Restaurant {
    private String name;
    private String image;
    private String costForTwo;

    public Restaurant(String name, String image, String costForTwo) {
        this.name = name;
        this.image = image;
        this.costForTwo = costForTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCostForTwo() {
        return costForTwo;
    }

    public void setCostForTwo(String costForTwo) {
        this.costForTwo = costForTwo;
    }
}
