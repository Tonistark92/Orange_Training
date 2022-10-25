package com.example.finalorangeproject.database.favourite;

import androidx.room.PrimaryKey;


@androidx.room.Entity(tableName = "favtable")
public class FavouriteEntity  {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String title ;
    private Double price;
    private String img ;
    private int count;
    private Double rate ;


    public FavouriteEntity(String title, Double price, String img, int count, Double rate) {
        this.title = title;
        this.price = price;
        this.img = img;
        this.count = count;
        this.rate = rate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
