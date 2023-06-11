package com.example.finalmobile.local.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorites_tv")
public class FavoriteTv implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "img_path")
    private String imgPath;


    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @ColumnInfo(name = "rate")
    private Float rate;

    public FavoriteTv(){}

    public FavoriteTv(int id){
        this.id = id;
    }
    public FavoriteTv(int id, String title, String imgPath, Float rate){
        this.id = id;
        this.title = title;
        this.imgPath = imgPath;
        this.rate = rate;
    }


}
