package com.example.caloriescounter_app.repository.user;

import android.os.AsyncTask;

import com.example.caloriescounter_app.ApplicationController;
import com.example.caloriescounter_app.database.User;
import com.example.caloriescounter_app.repository.user.OnUserRepositoryActionListener;

public class UpdateTask extends AsyncTask<User, Void, Void> {
    OnUserRepositoryActionListener listener;

    UpdateTask(OnUserRepositoryActionListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(User... users) {
        ApplicationController.getAppDatabase().userDao().updateUserWeight(users[0].uid, users[0].weight, users[0].bmr);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}
