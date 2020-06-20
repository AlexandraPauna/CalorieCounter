package com.example.caloriescounter_app.repository.user;

import android.content.Context;

import com.example.caloriescounter_app.ApplicationController;
import com.example.caloriescounter_app.database.AppDatabase;
import com.example.caloriescounter_app.database.User;
import com.example.caloriescounter_app.repository.user.InsertTask;
import com.example.caloriescounter_app.repository.user.OnUserRepositoryActionListener;
import com.example.caloriescounter_app.repository.user.UpdateTask;

public class UserRepository {
    private AppDatabase appDatabase;

    public UserRepository(Context context){
        appDatabase = ApplicationController.getAppDatabase();
    }

    public void insertTask(final User user,
                           final OnUserRepositoryActionListener listener){
        new InsertTask(listener).execute(user);
    }

    public void updateTask(final User user, final OnUserRepositoryActionListener listener){
        new UpdateTask(listener).execute(user);
    }


    public User getUser(String userName, String password) {
        return appDatabase.userDao().getUser(userName, password);
    }

    public float getWeight(int id) {
        return appDatabase.userDao().getWeight(id);
    }

    public float getGoalWeight(int id) {
        return appDatabase.userDao().getGoalWeight(id);
    }

    public int getBmr(int id) {
        return appDatabase.userDao().getBmr(id);
    }

    public User getUserByNameString (String userName){
        return appDatabase.userDao().findByUserName(userName);
    }

    public User getUserById(int id) {
        return appDatabase.userDao().getUserById(id);
    }

    public int getAge(int id) { return appDatabase.userDao().getAge(id); }

    public String getGender(int id) { return appDatabase.userDao().getGender(id); }

    public int getHeight(int id) { return appDatabase.userDao().getHeight(id); }

    /*public void deleteTask(final User user,
                           final OnUserRepositoryActionListener listener){
        new DeleteTask(listener).execute(user);
    }*/
}
