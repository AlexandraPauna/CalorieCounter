package com.example.caloriescounter_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;


public class Main_Activity extends AppCompatActivity implements OnActivityFragmentCommunication {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerMain,new Home_Fragment(),FRAGMENT_TAG);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        String backStateName = Home_Fragment.class.getName();
        fragmentTransaction.addToBackStack(backStateName);
        fragmentTransaction.commit();
    }

    @Override
    public void onAddFragment(String tag) {

    }

    @Override
    public void onReplaceFragment(String tag) {
        if(tag.equals("DisplayMeals")) { //replace Home_Fragment with Meals_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMain,new Meals_Fragment(), FRAGMENT_TAG);
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);

            String backStateName = Home_Fragment.class.getName();
            fragmentTransaction.addToBackStack(backStateName);

            fragmentTransaction.commit();
        }

        if(tag.equals("DisplayActivities")) { //replace Home_Fragment with Meals_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerMain,new Activities_Fragment(), FRAGMENT_TAG);
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);

            String backStateName = Home_Fragment.class.getName();
            fragmentTransaction.addToBackStack(backStateName);

            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRemoveFragment(String tag) {

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            if (fragment instanceof Meals_Fragment || fragment instanceof  Activities_Fragment) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerMain,new Home_Fragment(),FRAGMENT_TAG);
                fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
                String backStateName = Home_Fragment.class.getName();
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
            }
            else{
                //Welcome_Activity.getInstance().finish();
                finish();
                //super.onBackPressed();
            }
        }
    }
}
