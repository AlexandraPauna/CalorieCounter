package com.example.caloriescounter_app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Meal.class, Activity.class, UserPhoto.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public  abstract MealDao mealDao();
    public abstract ActivityDao activityDao();
    public abstract UserPhotoDao userPhotoDao();
}
