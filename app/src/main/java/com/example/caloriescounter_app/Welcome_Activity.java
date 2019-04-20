package com.example.caloriescounter_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

interface OnActivityFragmentCommunication {
    void onAddFragment(String tag);
    void onReplaceFragment(String tag);
    void onRemoveFragment(String tag);
}

public class Welcome_Activity extends AppCompatActivity implements OnActivityFragmentCommunication {

    static Welcome_Activity welcomeActivity;
    public static  Welcome_Activity getInstance(){
        return welcomeActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerWelcome,new Login_Fragment());
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void onAddFragment(String tag) {

    }

    @Override
    public void onReplaceFragment(String tag) {
        if(tag.equals("Login")) { //replace Login_Fragment with Register_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerWelcome,new Register_Fragment());
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }

        if(tag.equals("Register")) { //replace Register_Fragment with Login_Fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerWelcome,new Login_Fragment());
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRemoveFragment(String tag) {

    }

}
