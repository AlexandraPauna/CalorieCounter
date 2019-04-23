package com.example.caloriescounter_app.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_name LIKE :userName LIMIT 1")
    User findByUserName(String userName);

    @Insert
    void insertAll(User...users);

    @Insert
    void insertTask(User user);

    @Delete
    void delete(User user);

    @Query("UPDATE user SET weight = :newWeight WHERE uid = :userId")
    void updateUserWeight(int userId, float newWeight);

    @Query("SELECT * FROM user WHERE user_name = :userName AND password = :password")
    User getUser(String userName, String password);
}