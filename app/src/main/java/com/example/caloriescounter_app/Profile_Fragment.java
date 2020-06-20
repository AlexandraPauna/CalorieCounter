package com.example.caloriescounter_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

public class Profile_Fragment extends Fragment {
    OnActivityFragmentCommunication onActivityFragmentCommunication;

    public Profile_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(
                "com.example.caloriescounter_app", Context.MODE_PRIVATE);
        final int userId = prefs.getInt("com.example.caloriescounter_app.userId", 0);

        final TextView displayGender = (TextView) view.findViewById(R.id.tv_user_gender);
        displayGender.setText("Gender: " + ((String)new UserRepository(getContext()).getGender(userId)));

        final TextView displayAge = (TextView) view.findViewById(R.id.tv_user_birthday);
        displayAge.setText("Age: " + ((Integer)new UserRepository(getContext()).getAge(userId)).toString());

        final TextView displayHeight = (TextView) view.findViewById(R.id.tv_user_height);
        displayHeight.setText("Height: " + ((Integer)new UserRepository(getContext()).getHeight(userId)).toString());

        final TextView displayWeight = (TextView) view.findViewById(R.id.tv_user_weight);
        displayWeight.setText("Weight: " + ((Float)new UserRepository(getContext()).getWeight(userId)).toString());

        final TextView displayGoalWeight = (TextView) view.findViewById(R.id.tv_user_goal_weight);
        displayGoalWeight.setText("Goal Weight: " + ((Float)new UserRepository(getContext()).getGoalWeight(userId)).toString());

        final TextView displayBmr = (TextView) view.findViewById(R.id.tv_bmi);
        displayBmr.setText("BMR: " + ((Integer)new UserRepository(getContext()).getBmr(userId)).toString());

        Button ChangePhotoBtn = (Button) view.findViewById(R.id.btn_change_photo);
        ChangePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO

            }
        });

        Button SharePhotoBtn = (Button) view.findViewById(R.id.btn_share);

        final ImageView displayUserPhoto = (ImageView) view.findViewById(R.id.iv_user_image);
        final String imagePath = (String)new UserPhotoRepository(getContext()).getImage(userId);
        if(imagePath != null){
            displayUserPhoto.setImageURI(Uri.fromFile(new File(imagePath)));
        }
        else{ //daca nu exista nicio poza salvata se ia din drawable
            System.out.println("User does not have profile photo!");
            Drawable myDrawable = getResources().getDrawable(R.drawable.no_profile);
            displayUserPhoto.setImageDrawable(myDrawable);

            //se ascunde butonul de share poza daca nu exista poza stocata
            SharePhotoBtn.setVisibility(View.INVISIBLE);
        }

        SharePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    File myFile = new File(imagePath);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    Intent sharingIntent = new Intent("android.intent.action.SEND");
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    sharingIntent.setType(type);
                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                            getContext().getApplicationContext().getPackageName() + ".provider", myFile);
                    sharingIntent.putExtra("android.intent.extra.STREAM", photoURI);
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

//        double weight = (double)new UserRepository(getContext()).getWeight(userId);
//        double height = (double)new UserRepository(getContext()).getHeight(userId)/100;
//        double bmi = weight/(height * height);
//        DecimalFormat twoDForm = new DecimalFormat("#.##");
//        double bmiResult = Double.valueOf(twoDForm.format(bmi));
//        final TextView displayBmi = (TextView) view.findViewById(R.id.tv_bmi);
//        displayBmi.setText("BMI: " + String.valueOf(bmiResult));

        return view;
    }

}
