package com.example.caloriescounter_app;

import android.app.Application;

import com.example.caloriescounter_app.database.AppDatabase;
import com.example.caloriescounter_app.utils.Constants;

import androidx.room.Room;

public class ApplicationController extends Application {
    private static ApplicationController mInstance;

    private static AppDatabase mAppDatabase;

    public static ApplicationController getInstance(){
        return  mInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        mInstance  = this;

        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, Constants.DB_NAME).allowMainThreadQueries().build();
    }

    public static AppDatabase getAppDatabase(){
        return mAppDatabase;
    }

}
