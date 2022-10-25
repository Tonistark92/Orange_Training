package com.example.finalorangeproject.database.cart;

import androidx.room.PrimaryKey;


@androidx.room.Entity(tableName = "carttable")
public class CartEntity {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String title ;
    private String img ;
    private Double price;
    private int count;
    private Double rate ;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public CartEntity(String title, String img, Double price, int count, Double rate) {
        this.title = title;
        this.img = img;
        this.price = price;
        this.count = count;
        this.rate = rate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public Double getRate() {
        return rate;
    }
}