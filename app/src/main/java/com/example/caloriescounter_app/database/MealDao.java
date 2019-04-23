package com.example.caloriescounter_app.database;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meal")
    List<Meal> getAll();

    @Query("SELECT * FROM meal WHERE uid IN (:mealsIds)")
    List<Meal> loadAllByIds(int[] mealsIds);

    @Query("SELECT * FROM meal WHERE user_id = :userId AND date = :mealDate")
    List<Meal> loadAllForUserByDate(int userId, String mealDate);

    @Insert
    void insertAll(Meal...meals);

    @Insert
    void insertTask(Meal meal);

    @Delete
    void delete(Meal meal);

}
