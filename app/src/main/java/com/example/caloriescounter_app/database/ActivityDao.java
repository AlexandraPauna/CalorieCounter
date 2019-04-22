package com.example.caloriescounter_app.database;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ActivityDao {
    @Query("SELECT * FROM activity")
    List<Activity> getAll();

    @Query("SELECT * FROM activity WHERE uid IN (:activitiesIds)")
    List<Activity> loadAllByIds(int[]activitiesIds);

    @Query("SELECT * FROM activity WHERE user_id = :userId AND date = :date")
    List<Activity> loadAllForUserByDate(String userId, Date date);

    @Insert
    void insertAll(Activity...activities);

    @Insert
    void insertTask(Activity activity);

    @Delete
    void delete(Activity activity);
}
