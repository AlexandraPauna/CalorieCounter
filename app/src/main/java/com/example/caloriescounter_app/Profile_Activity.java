package com.example.caloriescounter_app;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriescounter_app.database.Converters;
import com.example.caloriescounter_app.database.Meal;
import com.example.caloriescounter_app.database.UserPhoto;
import com.example.caloriescounter_app.repository.user.UserRepository;
import com.example.caloriescounter_app.repository.userPhoto.OnUserPhotoRepositoryActionListener;
import com.example.caloriescounter_app.repository.userPhoto.UserPhotoRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Profile_Activity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button mCaptureBtn;
    Button mSaveBtn;
    ImageView mImageView;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImageView = findViewById(R.id.profile_image);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mSaveBtn = findViewById(R.id.save_image_btn);
        mSaveBtn.setVisibility(View.INVISIBLE);

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE} ;
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }
            }
        });
    }

    private void openCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                }
                else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            mImageView.setImageURI(image_uri);
            //de inserat imaginea in bd
            mSaveBtn.setVisibility(View.VISIBLE);
            mSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //String path = getRealPathFromURI(getApplicationContext(), image_uri);
                   UserRepository userRep = new UserRepository(getApplicationContext());

                    SharedPreferences prefs = getSharedPreferences(
                            "com.example.caloriescounter_app", Context.MODE_PRIVATE);
                    int userId = prefs.getInt("com.example.caloriescounter_app.userId", 0);

                    final String imagePath = (String)new UserPhotoRepository(getApplicationContext()).getImage(userId);
                    String filePathNewImg = image_uri.getPath();
                    final UserPhoto userPhoto = new UserPhoto(userId, filePathNewImg);

                    //save default photo
                    Bitmap bm =((BitmapDrawable)mImageView.getDrawable()).getBitmap();

                    try {
                        saveImageToExternal("userPhoto"+userId, bm, getApplicationContext(), userId, imagePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void saveImageToExternal(String imgName, Bitmap bm, Context context, int uid, String imagePath) throws IOException {
        //Create Path to save Image
        String appFolderName = "Calorie_Counter";
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + File.separator + appFolderName);
        //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, imgName+".png"); // Imagename.png
        FileOutputStream out = new FileOutputStream(imageFile);
        try{

            bm.compress(Bitmap.CompressFormat.PNG, 100, out);

            //addPhotoToDatabase();
            String filePath = imageFile.getAbsolutePath();
            final UserPhoto userPhoto = new UserPhoto(uid, filePath);

            if(imagePath != null){
                //update
                new UserPhotoRepository(getApplicationContext()).updateTask(userPhoto, new OnUserPhotoRepositoryActionListener() {
                    @Override
                    public void actionSucces() {
                        Toast.makeText(getApplicationContext(), "Successfully photo updated", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Profile_Activity.this, Main_Activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void actionFailed() {
                        Toast.makeText(getApplicationContext(), "Photo could not be updated!", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                //insert
                new UserPhotoRepository(getApplicationContext()).insertTask(userPhoto, new OnUserPhotoRepositoryActionListener() {
                    @Override
                    public void actionSucces() {
                        Toast.makeText(getApplicationContext(), "Successfully photo added", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void actionFailed() {
                        Toast.makeText(getApplicationContext(), "Photo could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

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
