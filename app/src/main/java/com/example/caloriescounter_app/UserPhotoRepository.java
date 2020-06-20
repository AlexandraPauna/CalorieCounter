package com.example.caloriescounter_app;

import android.content.Context;

import com.example.caloriescounter_app.database.AppDatabase;
import com.example.caloriescounter_app.database.UserPhoto;

public class UserPhotoRepository {
    private AppDatabase appDatabase;

    public UserPhotoRepository(Context context){
        appDatabase = ApplicationController.getAppDatabase();
    }

    public void insertTask(final UserPhoto userPhoto,
                           final OnUserPhotoRepositoryActionListener listener){
        new UserPhotoInsertTask(listener).execute(userPhoto);
    }

    public String getImage(int user_id) { return appDatabase.userPhotoDao().getImage(user_id); }

}
