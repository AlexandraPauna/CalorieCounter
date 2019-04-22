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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.caloriescounter_app.database.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register_Fragment extends Fragment {

    OnActivityFragmentCommunication onActivityFragmentCommunication;

    public Register_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_, container, false);

        Button BackToLoginBtn = (Button) view.findViewById(R.id.btn_back_login);
        BackToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityFragmentCommunication.onReplaceFragment("Register");

            }
        });

        final EditText userName = view.findViewById(R.id.et_username);
        final EditText password = view.findViewById(R.id.et_password);
        String gender;
        RadioButton radioBtnFemale = (RadioButton) view.findViewById(R.id.radio_female);
        RadioButton radioBtnMale = (RadioButton) view.findViewById(R.id.radio_male);
        if(radioBtnFemale.isChecked()){
            gender = "female";
        }
        else
            if(radioBtnMale.isChecked()){
                gender = "male";
            }
            else gender = null;

        final String userGender = gender;
        /*final RadioGroup radioGroupGender = view.findViewById(R.id.rg_gender);
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        final RadioButton selectedRadioButton = (RadioButton)view.findViewById(R.id.selectedId);
        selectedRadioButton.getText().toString()*/

        final EditText birthday = view.findViewById(R.id.et_birthday);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = format.parse(birthday.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Date birthdayDate = date;
        final EditText height = view.findViewById(R.id.et_height);
        final EditText weight = view.findViewById(R.id.et_weight);
        final EditText goalWeight = view.findViewById(R.id.et_goal_weight);

        Button addUser = (Button) view.findViewById(R.id.btn_sign_up);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(userName.getText().toString(), password.getText().toString(),
                        userGender, birthdayDate,
                        Integer.parseInt(height.getText().toString()),
                        Float.parseFloat(weight.getText().toString()),
                        Float.parseFloat(goalWeight.getText().toString()) );

                new UserRepository(getContext()).insertTask(user, new OnUserRepositoryActionListener() {
                    @Override
                    public void actionSucces() {
                        Toast.makeText(getContext(), "Successfully registered", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), Main_Activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void actionFailed() {
                        Toast.makeText(getContext(), "User could not be aded!", Toast.LENGTH_SHORT).show();
                    }
                });
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
