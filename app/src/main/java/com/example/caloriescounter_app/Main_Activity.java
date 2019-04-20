package com.example.caloriescounter_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;


public class Main_Activity extends AppCompatActivity implements OnActivityFragmentCommunication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMain,new Home_Fragment());
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void onAddFragment(String tag) {

    }

    @Override
    public void onReplaceFragment(String tag) {
        if(tag.equals("DisplayMeals")) { //replace Home_Fragment with Meals_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMain,new Meals_Fragment());
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }

        if(tag.equals("DisplayActivities")) { //replace Home_Fragment with Meals_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMain,new Activities_Fragment());
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRemoveFragment(String tag) {

    }
}
