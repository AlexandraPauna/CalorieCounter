package com.example.caloriescounter_app;

import android.content.Context;

import com.example.caloriescounter_app.database.AppDatabase;
import com.example.caloriescounter_app.database.Meal;
import com.example.caloriescounter_app.database.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MealRepository {
    private AppDatabase appDatabase;

    public MealRepository(Context context){
        appDatabase = ApplicationController.getAppDatabase();
    }

    public void insertTask(final Meal meal,
                           final OnMealRepositoryActionListener listener){
        new MealInsertTask(listener).execute(meal);
    }
    
    public List<Meal> getMeals(int userID, String date) {
        return appDatabase.mealDao().loadAllForUserByDate(userID, date);
    }

}
