package com.example.caloriescounter_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;



public class Main_Activity extends AppCompatActivity implements OnActivityFragmentCommunication {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout,new Home_Fragment(),FRAGMENT_TAG);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        String backStateName = Home_Fragment.class.getName();
        fragmentTransaction.addToBackStack(backStateName);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddFragment(String tag) {

    }

    @Override
    public void onReplaceFragment(String tag) {
        if(tag.equals("DisplayMeals")) { //replace Home_Fragment with Meals_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout,new Meals_Fragment(), FRAGMENT_TAG);
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);

            String backStateName = Home_Fragment.class.getName();
            fragmentTransaction.addToBackStack(backStateName);

            fragmentTransaction.commit();
        }

        if(tag.equals("DisplayActivities")) { //replace Home_Fragment with Meals_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout,new Activities_Fragment(), FRAGMENT_TAG);
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
                fragmentTransaction.replace(R.id.drawer_layout,new Home_Fragment(),FRAGMENT_TAG);
                fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
                String backStateName = Home_Fragment.class.getName();
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
            }
            else {
                //super.onBackPressed();
                finish();
            }
        }
    }


}
