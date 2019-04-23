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

    @Query("UPDATE user SET weight = :newWeight, bmr = :newBmr WHERE uid = :userId")
    void updateUserWeight(int userId, float newWeight, int newBmr);

    @Query("SELECT * FROM user WHERE user_name = :userName AND password = :password")
    User getUser(String userName, String password);

    @Query("SELECT * FROM user WHERE uid = :id")
    User getUserById(int id);

    @Query("SELECT weight FROM user WHERE uid = :id")
    float getWeight(int id);

    @Query("SELECT bmr FROM user WHERE uid = :id")
    int getBmr(int id);
}
