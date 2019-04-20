package com.example.caloriescounter_app;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Meals_Fragment extends Fragment {


    public Meals_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

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

}
