package com.example.caloriescounter_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
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

import com.google.android.material.navigation.NavigationView;


public class Main_Activity extends AppCompatActivity implements OnActivityFragmentCommunication, NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private DrawerLayout drawer;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new MessageFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_message);
//        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new Home_Fragment(),FRAGMENT_TAG);
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

        if(tag.equals("DisplayProfile")) { //replace Home_Fragment with Profile_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout,new Profile_Fragment(), FRAGMENT_TAG);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        String backStateName;
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragmentTransaction.replace(R.id.fragment_container,
                        new Home_Fragment());
                fragmentTransaction.replace(R.id.fragment_container,
                        new Home_Fragment(), FRAGMENT_TAG);
                backStateName = Home_Fragment.class.getName();
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
                break;
            case R.id.nav_meal:
                fragmentTransaction.replace(R.id.fragment_container,
                        new Meals_Fragment(), FRAGMENT_TAG);
                backStateName = Meals_Fragment.class.getName();
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
                break;
            case R.id.nav_activity:
                fragmentTransaction.replace(R.id.fragment_container,
                        new Activities_Fragment(), FRAGMENT_TAG);
                backStateName = Activities_Fragment.class.getName();
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
                break;
            case R.id.nav_user:
                fragmentTransaction.replace(R.id.fragment_container,
                        new Profile_Fragment(), FRAGMENT_TAG);
                backStateName = Profile_Fragment.class.getName();
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                if (fragment instanceof Home_Fragment) {
                    super.onBackPressed();
                    finish();
                }
                else {
                    showHome();
                }
//                if (fragment instanceof Meals_Fragment || fragment instanceof  Activities_Fragment
//                        || fragment instanceof Profile_Fragment) {
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.drawer_layout,new Home_Fragment(),FRAGMENT_TAG);
//                    fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    String backStateName = Home_Fragment.class.getName();
//                    fragmentTransaction.addToBackStack(backStateName);
//                    fragmentTransaction.commit();
//                }
//                else {
//                    //super.onBackPressed();
//                    finish();
//                }
            }
            else {
                finish();
            }
        }

    }

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


    private void showHome(){
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.fragment_container,
//                new Home_Fragment()).commit();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        String backStateName;
        fragmentTransaction.replace(R.id.fragment_container,
                new Home_Fragment());
        fragmentTransaction.replace(R.id.fragment_container,
                new Home_Fragment(), FRAGMENT_TAG);
        backStateName = Home_Fragment.class.getName();
        fragmentTransaction.addToBackStack(backStateName);
        fragmentTransaction.commit();
    }


}
