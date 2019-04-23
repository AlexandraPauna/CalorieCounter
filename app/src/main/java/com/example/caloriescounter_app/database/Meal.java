package com.example.caloriescounter_app.database;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Meal {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "brand")
    public String brand;

    @ColumnInfo(name = "serving_size")
    public String servingSize;

    @ColumnInfo(name = "calories")
    public int calories;

    @ColumnInfo(name = "protein")
    public int protein;

    @ColumnInfo(name = "fat")
    public int fat;

    @ColumnInfo(name = "carbs")
    public int carbs;

    public Meal(String name, String date, int userId, String brand, String servingSize, int calories, int protein, int fat, int carbs){
        this.name = name;
        this.date = date;
        this.userId = userId;
        this.brand = brand;
        this.servingSize = servingSize;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }
}
