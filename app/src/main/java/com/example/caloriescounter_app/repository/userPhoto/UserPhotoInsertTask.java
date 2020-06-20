package com.example.caloriescounter_app.repository.userPhoto;

import android.os.AsyncTask;

import com.example.caloriescounter_app.ApplicationController;
import com.example.caloriescounter_app.database.UserPhoto;
import com.example.caloriescounter_app.repository.userPhoto.OnUserPhotoRepositoryActionListener;

public class UserPhotoInsertTask extends AsyncTask<UserPhoto, Void, Void> {
    OnUserPhotoRepositoryActionListener listener;

    UserPhotoInsertTask(OnUserPhotoRepositoryActionListener listener) {this.listener = listener;}

    @Override
    protected Void doInBackground(UserPhoto... photos) {
        ApplicationController.getAppDatabase().userPhotoDao().insertTask(photos[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}
