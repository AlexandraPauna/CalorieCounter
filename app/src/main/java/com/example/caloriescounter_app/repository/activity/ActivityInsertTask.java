package com.example.caloriescounter_app.repository.activity;

import android.os.AsyncTask;

import com.example.caloriescounter_app.ApplicationController;
import com.example.caloriescounter_app.database.Activity;

public class ActivityInsertTask extends AsyncTask<Activity, Void, Void> {
    OnActivityRepositoryActionListener listener;

    ActivityInsertTask(OnActivityRepositoryActionListener listener) {this.listener = listener;}

    @Override
    protected Void doInBackground(Activity... activities) {
        ApplicationController.getAppDatabase().activityDao().insertTask(activities[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}
