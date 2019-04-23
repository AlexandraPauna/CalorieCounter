package com.example.caloriescounter_app.database;


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "height")
    public int height;

    @ColumnInfo(name = "weight")
    public float weight;

    @ColumnInfo(name = "goal_weight")
    public float goal_weight;

    @ColumnInfo(name = "bmr")
    public int bmr;

    public User(String userName, String password, String gender, int age, int height, float weight, float goal_weight, int bmr){
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal_weight = goal_weight;
        this.bmr = bmr;
    }

}


