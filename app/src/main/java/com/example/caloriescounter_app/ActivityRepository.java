package com.example.caloriescounter_app;

import android.content.Context;

import com.example.caloriescounter_app.database.Activity;
import com.example.caloriescounter_app.database.AppDatabase;

import java.util.Date;
import java.util.List;

public class ActivityRepository {
    private AppDatabase appDatabase;

    public ActivityRepository(Context context){
        appDatabase = ApplicationController.getAppDatabase();
    }

    public void insertTask(final Activity activity,
                           final OnActivityRepositoryActionListener listener){
        new ActivityInsertTask(listener).execute(activity);
    }

    public List<Activity> getActivities (int uid, String date){
        return appDatabase.activityDao().loadAllForUserByDate(uid, date);
    }

    /*public void deleteTask(final User user,
                           final OnUserRepositoryActionListener listener){
        new DeleteTask(listener).execute(user);
    }*/
}
