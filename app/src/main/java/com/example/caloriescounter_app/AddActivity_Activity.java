package com.example.caloriescounter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caloriescounter_app.database.Activity;
import com.example.caloriescounter_app.database.Converters;

import java.util.Calendar;
import java.util.Date;

public class AddActivity_Activity extends AppCompatActivity {

    /*@BindView(R.id.et_activity_name)
    EditText activityName;

    @BindView(R.id.et_duration)
    EditText duration;

    @BindView(R.id.et_cal_burned)
    EditText caloriesBurned;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        final Date currentTime = Calendar.getInstance().getTime();

        final EditText activityName = findViewById(R.id.et_activity_name);
        final EditText duration = findViewById(R.id.et_duration);
        final EditText caloriesBurned = findViewById(R.id.et_cal_burned);

        Button addActivity = findViewById(R.id.btn_add_a);
        addActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(
                        "com.example.caloriescounter_app", Context.MODE_PRIVATE);
                int userId = prefs.getInt("com.example.caloriescounter_app.userId", 0);

                if(activityName.getText().toString().isEmpty()){
                    activityName.setError("Field required");
                    activityName.requestFocus();
                } else if(duration.getText().toString().isEmpty()) {
                    duration.setError("Field required");
                    duration.requestFocus();
                } else if(caloriesBurned.getText().toString().isEmpty()){
                    caloriesBurned.setError("Field required");
                    caloriesBurned.requestFocus();
                } else{

                    Activity activity = new Activity(activityName.getText().toString(), Converters.getCurrentDate(),
                                                userId, Integer.parseInt(duration.getText().toString()),
                                                Integer.parseInt(caloriesBurned.getText().toString())
                                        );

                    new ActivityRepository(AddActivity_Activity.this).insertTask(activity, new OnActivityRepositoryActionListener() {
                        @Override
                        public void actionSucces() {
                            Toast.makeText(AddActivity_Activity.this, "Activity successfully added", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddActivity_Activity.this, Main_Activity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void actionFailed() {
                            Toast.makeText(AddActivity_Activity.this, "Activity could not be added!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    /*@Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Button addActivity = (Button)

        return super.onCreateView(name, context, attrs);
    }*/
}
