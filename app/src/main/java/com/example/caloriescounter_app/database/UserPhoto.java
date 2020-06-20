package com.example.caloriescounter_app.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserPhoto {
    @PrimaryKey
    private int user_id;

//    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
//    private byte[] image;

    @ColumnInfo(name = "image")
    private String image;

    public UserPhoto(int user_id, String image) {
        this.user_id = user_id;
        this.image = image;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getImage() {
        return image;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
