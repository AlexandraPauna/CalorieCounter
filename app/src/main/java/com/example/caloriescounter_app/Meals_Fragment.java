package com.example.caloriescounter_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.caloriescounter_app.database.Converters;
import com.example.caloriescounter_app.database.Meal;
import com.example.caloriescounter_app.repository.meal.MealRepository;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Meals_Fragment extends Fragment {


    ArrayList<Meal> meals = new ArrayList<Meal>();
    MealsAdapter mealsAdapter;

    public Meals_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rec_view_meals);

        Button GoToAddMealBtn  = (Button) view.findViewById(R.id.btn_add_meal);
        GoToAddMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMeal_Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        SharedPreferences prefs = getActivity().getSharedPreferences(
                "com.example.caloriescounter_app", Context.MODE_PRIVATE);
        int userId = prefs.getInt("com.example.caloriescounter_app.userId", 0);

        meals = (ArrayList<Meal>) new MealRepository(getContext()).getMeals(userId, Converters.getCurrentDate());

        //meals.addAll(new ApplicationController().getAppDatabase().userDao().getAll());

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rec_view_meals);
        mealsAdapter = new MealsAdapter(getContext(), meals);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealsAdapter);

        EditText editText = (EditText) view.findViewById(R.id.search_meal);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text){
        ArrayList<Meal> filteredMeals = new ArrayList<Meal>();

        for(Meal meal: meals){
            if(meal.getName().toLowerCase().contains(text.toLowerCase())){
                filteredMeals.add(meal);
            }
        }
        mealsAdapter.filterList(filteredMeals);
    }

}
