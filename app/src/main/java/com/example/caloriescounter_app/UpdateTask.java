package com.example.caloriescounter_app;

import android.os.AsyncTask;

import com.example.caloriescounter_app.database.User;

public class UpdateTask extends AsyncTask<User, Void, Void> {
    OnUserRepositoryActionListener listener;

    UpdateTask(OnUserRepositoryActionListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(User... users) {
        ApplicationController.getAppDatabase().userDao().updateTask(users[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}
