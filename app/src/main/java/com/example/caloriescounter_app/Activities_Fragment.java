package com.example.caloriescounter_app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.caloriescounter_app.database.Activity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Activities_Fragment extends Fragment {

    ArrayList<Activity> activities = new ArrayList<Activity>();

    ActivitiesAdapter activitiesAdapter;

    public Activities_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        Button GoToAddActivityBtn  = (Button) view.findViewById(R.id.btn_add_activity);
        GoToAddActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        activities.addAll(new ApplicationController().getAppDatabase().activityDao().getAll());

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rec_view_activities);
        activitiesAdapter = new ActivitiesAdapter(getContext(), activities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(activitiesAdapter);

    }

}
