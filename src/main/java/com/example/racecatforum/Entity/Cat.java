package com.example.racecatforum.Entity;

import lombok.*;

public class Cat {
    private int catId;
    private int ownerId;
    private String catName;
    private String catDescription;
    private String catGender;
    private String catImage;
    private int catAge;

    public Cat(int catId, int ownerId, String catName, String catDescription, String catGender, String catImage, int catAge) {
        this.catId = catId;
        this.ownerId = ownerId;
        this.catName = catName;
        this.catDescription = catDescription;
        this.catGender = catGender;
        this.catImage = catImage;
        this.catAge = catAge;
    }
    public Cat() {}

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getCatGender() {
        return catGender;
    }

    public void setCatGender(String catGender) {
        this.catGender = catGender;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public int getCatAge() {
        return catAge;
    }

    public void setCatAge(int catAge) {
        this.catAge = catAge;
    }
}



