package com.example.caloriescounter_app;

import android.os.AsyncTask;

import com.example.caloriescounter_app.database.Meal;
import com.example.caloriescounter_app.database.User;

public class MealInsertTask extends AsyncTask<Meal, Void, Void> {
    OnMealRepositoryActionListener listener;

    MealInsertTask(OnMealRepositoryActionListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Meal... meals) {
        ApplicationController.getAppDatabase().mealDao().insertTask(meals[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}
