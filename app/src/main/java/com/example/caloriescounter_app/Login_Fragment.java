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
import android.widget.Toast;

import com.example.caloriescounter_app.database.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_Fragment extends Fragment {

    OnActivityFragmentCommunication onActivityFragmentCommunication;

    public Login_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_, container, false);
        Button RegisterBtn = (Button) view.findViewById(R.id.btn_go_to_register);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Ceva");
                onActivityFragmentCommunication.onReplaceFragment("Login");

            }
        });



        final EditText userName = view.findViewById(R.id.et_username);
        final EditText password = view.findViewById(R.id.et_password);

        Button MainActivityBtn  = (Button) view.findViewById(R.id.btn_login);
        MainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Atlceva");
                UserRepository userRep = new UserRepository(getContext());
                System.out.println("Hellooo");

                    System.out.println("Username: " + userName.getText().toString());
                    System.out.println("Password: " + password.getText().toString());

                    User user = userRep.getUser(userName.getText().toString(), password.getText().toString());
                    if (user != null) {
                        Toast.makeText(getContext(), "User found!", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("com.example.caloriescounter_app", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("com.example.caloriescounter_app.userId", user.uid);
                        editor.apply();
                        Intent intent = new Intent(getActivity(), Main_Activity.class);
                        startActivity(intent);
                    } else {
                        System.out.println("aaaaa");
                        Toast.makeText(getContext(), "User not found!", Toast.LENGTH_SHORT).show();
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
