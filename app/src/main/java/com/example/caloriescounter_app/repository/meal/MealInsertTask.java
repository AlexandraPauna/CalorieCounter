package com.example.caloriescounter_app.repository.meal;

import android.os.AsyncTask;

import com.example.caloriescounter_app.ApplicationController;
import com.example.caloriescounter_app.database.Meal;

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
