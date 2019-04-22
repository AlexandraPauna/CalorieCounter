package com.example.caloriescounter_app.database;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Activity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "duration")
    public int duration;

    @ColumnInfo(name = "calories_burned")
    public int caloriesBurned;

    public Activity(String name, Date date, int userId, int duration, int caloriesBurned){
        this.name = name;
        this.date = date;
        this.userId = userId;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }
}
