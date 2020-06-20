package com.example.caloriescounter_app.repository.userPhoto;

import android.os.AsyncTask;

import com.example.caloriescounter_app.ApplicationController;
import com.example.caloriescounter_app.database.UserPhoto;

public class UserPhotoUpdateTask extends AsyncTask<UserPhoto, Void, Void> {
    OnUserPhotoRepositoryActionListener listener;

    UserPhotoUpdateTask(OnUserPhotoRepositoryActionListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(UserPhoto... photos) {
        ApplicationController.getAppDatabase().userPhotoDao().updateUserPhotoImage(
                photos[0].getUser_id(), photos[0].getImage());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}
