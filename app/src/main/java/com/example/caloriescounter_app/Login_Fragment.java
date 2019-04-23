package com.example.caloriescounter_app;


import android.content.Context;
import android.content.Intent;
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
                onActivityFragmentCommunication.onReplaceFragment("Login");

            }
        });



        final EditText userName = view.findViewById(R.id.et_username);
        final EditText password = view.findViewById(R.id.et_password);

        Button MainActivityBtn  = (Button) view.findViewById(R.id.btn_login);
        MainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository userRep = new UserRepository(getContext());
                try {
                    User user = userRep.getUser(userName.toString(), password.toString());
                    if (user != null) {
                        Toast.makeText(getContext(), "User found!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "User not found!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // nasol moment
                }
                Intent intent = new Intent(getActivity(), Main_Activity.class);
                startActivity(intent);
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
