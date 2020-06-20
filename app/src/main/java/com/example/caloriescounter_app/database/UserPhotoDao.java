package com.example.caloriescounter_app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserPhotoDao {
    @Insert
    void insertTask(UserPhoto userPhoto);

    @Query("SELECT image FROM UserPhoto WHERE user_id = :id")
    String getImage(int id);

    @Query("UPDATE UserPhoto SET image = :newImage  WHERE user_id = :userId")
    void updateUserPhotoImage(int userId, String newImage);
}
