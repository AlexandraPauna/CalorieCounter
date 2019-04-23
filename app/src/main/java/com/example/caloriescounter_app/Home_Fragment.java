package com.example.caloriescounter_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriescounter_app.database.User;

import java.text.DateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    OnActivityFragmentCommunication onActivityFragmentCommunication;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button DisplayMealsBtn = (Button) view.findViewById(R.id.btn_meals);
        DisplayMealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityFragmentCommunication.onReplaceFragment("DisplayMeals");

            }
        });

        Button DisplayActivitiesBtn = (Button) view.findViewById(R.id.btn_activities);
        DisplayActivitiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityFragmentCommunication.onReplaceFragment("DisplayActivities");

            }
        });

        final EditText weightNew = view.findViewById(R.id.et_new_weight);

        Button updateWeight = (Button) view.findViewById(R.id.btn_update_weight);
        updateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(weightNew.getText().toString().isEmpty()){
                    weightNew.setError("Input required");
                    weightNew.requestFocus();


                } else {
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

                    SharedPreferences prefs = getActivity().getSharedPreferences(
                            "com.example.caloriescounter_app", Context.MODE_PRIVATE);
                    int userId = prefs.getInt("com.example.caloriescounter_app.userId", 0);
                    User user = new UserRepository(getContext()).getUserById(userId);

                    int bmrulet = 0;
                    if (user.gender.equals("female")) {
                        bmrulet = (int) (655 + 9 * Float.parseFloat(weightNew.getText().toString()) +
                                1 * user.height - user.age);
                    } else if (user.gender.equals("male")) {
                        bmrulet = (int) (66 + 13 * Float.parseFloat(weightNew.getText().toString()) +
                                5 * user.height - user.age);
                    }

                    final User newUser = new User(user.userName, user.password, user.gender, user.age, user.height, Float.parseFloat(weightNew.getText().toString()), user.goal_weight, bmrulet);
                    newUser.setUid(userId);
                    new UserRepository(getContext()).updateTask(newUser, new OnUserRepositoryActionListener() {
                        @Override
                        public void actionSucces() {
                            Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void actionFailed() {
                            Toast.makeText(getContext(), "User could not be updated!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnActivityFragmentCommunication){
            onActivityFragmentCommunication = (OnActivityFragmentCommunication) context;
        }
    }

}
