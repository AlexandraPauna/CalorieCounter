package com.example.caloriescounter_app;

import android.content.Context;

import com.example.caloriescounter_app.database.AppDatabase;
import com.example.caloriescounter_app.database.User;

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

    public User getUserByNameString (String userName){
        return appDatabase.userDao().findByUserName(userName);
    }

    /*public void deleteTask(final User user,
                           final OnUserRepositoryActionListener listener){
        new DeleteTask(listener).execute(user);
    }*/
}
