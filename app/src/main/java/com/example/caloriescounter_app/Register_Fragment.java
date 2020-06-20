package com.example.caloriescounter_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.caloriescounter_app.database.Converters;
import com.example.caloriescounter_app.database.User;
import com.example.caloriescounter_app.database.UserPhoto;
import com.example.caloriescounter_app.repository.user.OnUserRepositoryActionListener;
import com.example.caloriescounter_app.repository.user.UserRepository;
import com.example.caloriescounter_app.repository.userPhoto.OnUserPhotoRepositoryActionListener;
import com.example.caloriescounter_app.repository.userPhoto.UserPhotoRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


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


        String gender = null;
        final RadioButton radioBtnFemale = (RadioButton) view.findViewById(R.id.radio_female);
        final RadioButton radioBtnMale = (RadioButton) view.findViewById(R.id.radio_male);
        final String userGender = gender;

        final EditText age = view.findViewById(R.id.et_birthday);
        final EditText height = view.findViewById(R.id.et_height);
        final EditText weight = view.findViewById(R.id.et_weight);
        final EditText goalWeight = view.findViewById(R.id.et_goal_weight);

        /*int bmr = 0;
        if (radioBtnFemale.isChecked()) {
            gender = "female";
            bmr = (int) (655 + 9 * Float.parseFloat(weight.getText().toString()) +
                    1 * Integer.parseInt(height.getText().toString()) - Integer.parseInt(age.getText().toString()));
        } else if (radioBtnMale.isChecked()) {
            bmr = (int) (66 + 13 * Float.parseFloat(weight.getText().toString()) +
                    5 * Integer.parseInt(height.getText().toString()) - Integer.parseInt(age.getText().toString()));
            gender = "male";
        }

        final int bmrValue = bmr;*/

        Button addUser = (Button) view.findViewById(R.id.btn_sign_up);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User userFound = new UserRepository(getContext()).getUserByNameString(userName.getText().toString());

                String genulet = null;
                int bmrulet = 0;
                if (radioBtnFemale.isChecked()) {
                    genulet = "female";
                    bmrulet = (int) (655 + 9 * Float.parseFloat(weight.getText().toString()) +
                            1 * Integer.parseInt(height.getText().toString()) - Integer.parseInt(age.getText().toString()));
                } else if (radioBtnMale.isChecked()) {
                    bmrulet = (int) (66 + 13 * Float.parseFloat(weight.getText().toString()) +
                            5 * Integer.parseInt(height.getText().toString()) - Integer.parseInt(age.getText().toString()));
                    genulet = "male";

                }
                if (userName.getText().toString().isEmpty()) {
                    userName.setError("Field required");
                    userName.requestFocus();
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Field required");
                    password.requestFocus();

                } else if (age.getText().toString().isEmpty()) {
                    age.setError("Field required");
                    age.requestFocus();

                } else if (height.getText().toString().isEmpty()) {
                    height.setError("Field required");
                    height.requestFocus();

                } else if (weight.getText().toString().isEmpty()) {
                    weight.setError("Field required");
                    weight.requestFocus();

                } else if (goalWeight.getText().toString().isEmpty()) {
                    goalWeight.setError("Field required");
                    goalWeight.requestFocus();

                } else if (genulet == null) {
                    radioBtnMale.setError("Selection Required");
                    radioBtnFemale.setError("Selection Required");
                    radioBtnFemale.requestFocus();
                    radioBtnMale.requestFocus();
                } else if (userFound != null) {
                    userName.setError("UserName already exists!");
                    userName.requestFocus();

                } else {
                    System.out.println("Data curenta: " + Converters.getCurrentDate());
                    final User user = new User(userName.getText().toString(), password.getText().toString(),
                            genulet, Integer.parseInt(age.getText().toString()),
                            Integer.parseInt(height.getText().toString()),
                            Float.parseFloat(weight.getText().toString()),
                            Float.parseFloat(goalWeight.getText().toString()), bmrulet);

                    new UserRepository(getContext()).insertTask(user, new OnUserRepositoryActionListener() {
                        @Override
                        public void actionSucces() {
                            Toast.makeText(getContext(), "Successfully registered", Toast.LENGTH_SHORT).show();

                            UserRepository userRep = new UserRepository(getContext());
                            User userAdded = userRep.getUser(userName.getText().toString(), password.getText().toString());
                            SharedPreferences sharedPref = getActivity().getSharedPreferences("com.example.caloriescounter_app", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt("com.example.caloriescounter_app.userId", userAdded.uid);
                            editor.apply();
                            Intent intent = new Intent(getActivity(), Main_Activity.class);

                            //save default photo
                            Drawable myDrawable = getResources().getDrawable(R.drawable.no_profile);
                            Bitmap anImage      = ((BitmapDrawable) myDrawable).getBitmap();
                            try {
                                saveImageToExternal("userPhoto", anImage, getContext(), userAdded.uid);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            startActivity(intent);
                        }

                        @Override
                        public void actionFailed() {
                            Toast.makeText(getContext(), "User could not be added!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityFragmentCommunication) {
            onActivityFragmentCommunication = (OnActivityFragmentCommunication) context;
        }
    }

    public void saveImageToExternal(String imgName, Bitmap bm, Context context, int uid) throws IOException {
        //Create Path to save Image
        String appFolderName = "Calorie_Counter";
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + File.separator + appFolderName);
        //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, imgName+".png"); // Imagename.png
        FileOutputStream out = new FileOutputStream(imageFile);
        try{
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image

            //addPhotoToDatabase();
            String filePath = imageFile.getAbsolutePath();
            final UserPhoto userPhoto = new UserPhoto(uid, filePath);

            new UserPhotoRepository(getContext()).insertTask(userPhoto, new OnUserPhotoRepositoryActionListener() {
                        @Override
                        public void actionSucces() {
                            Toast.makeText(getContext(), "Successfully default photo added", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void actionFailed() {
                            Toast.makeText(getContext(), "Default photo could not be added!", Toast.LENGTH_SHORT).show();
                        }
                    });
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(context,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } catch(Exception e) {
            throw new IOException();
        }
    }

}
