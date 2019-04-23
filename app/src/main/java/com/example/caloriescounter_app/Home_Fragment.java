package com.example.caloriescounter_app;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriescounter_app.database.Activity;
import com.example.caloriescounter_app.database.Converters;
import com.example.caloriescounter_app.database.Meal;
import com.example.caloriescounter_app.database.User;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    OnActivityFragmentCommunication onActivityFragmentCommunication;

    ArrayList<Meal> meals = new ArrayList<Meal>();
    ArrayList<Activity> activities = new ArrayList<Activity>();
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        SharedPreferences prefs = getActivity().getSharedPreferences(
                "com.example.caloriescounter_app", Context.MODE_PRIVATE);
        final int userId = prefs.getInt("com.example.caloriescounter_app.userId", 0);
        TextView message = (TextView) view.findViewById(R.id.tv_date);
        message.setText("Today " + Converters.getCurrentDate());

        TextView displayWeight = (TextView) view.findViewById(R.id.tv_display_weight);
        displayWeight.setText(((Float)new UserRepository(getContext()).getWeight(userId)).toString());

        TextView displayIntake = (TextView) view.findViewById(R.id.tv_cal_intake);
        TextView displayBurned = (TextView) view.findViewById(R.id.tv_cal_burned);
        TextView displayBalance = (TextView) view.findViewById(R.id.tv_cal_left);

        meals = (ArrayList<Meal>) new MealRepository(getContext()).getMeals(userId, Converters.getCurrentDate());

        int caloriesIntake = 0;
        for(Meal meal: meals) {
            caloriesIntake = caloriesIntake + meal.calories;
        }
        displayIntake.setText(((Integer) caloriesIntake).toString());

        activities = (ArrayList<Activity>) new ActivityRepository(getContext()).getActivities(userId, Converters.getCurrentDate());
        int caloriesBurned = 0;
        for(Activity activity: activities) {
            caloriesBurned = caloriesBurned + activity.caloriesBurned;
        }
        int bmr = (int) new UserRepository(getContext()).getBmr(userId);
        displayBurned.setText(((Integer) caloriesBurned).toString());
        displayBalance.setText(((Integer) (bmr-caloriesIntake+caloriesBurned)).toString());

        Button DisplayMealsBtn = (Button) view.findViewById(R.id.btn_meals);
        DisplayMealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityFragmentCommunication.onReplaceFragment("DisplayMeals");

            }
        });

        Button DisplayActivitiesBtn = (Button) view.findViewById(R.id.btn_activities);
        DisplayActivitiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityFragmentCommunication.onReplaceFragment("DisplayActivities");

            }
        });

        final EditText weightNew = view.findViewById(R.id.et_new_weight);

        Button updateWeight = (Button) view.findViewById(R.id.btn_update_weight);
        updateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(weightNew.getText().toString().isEmpty()){
                    weightNew.setError("Input required");
                    weightNew.requestFocus();


                } else {

                    User user = new UserRepository(getContext()).getUserById(userId);

                    int bmrulet = 0;
                    if (user.gender.equals("female")) {
                        bmrulet = (int) (655 + 9 * Float.parseFloat(weightNew.getText().toString()) +
                                1 * user.height - user.age);
                    } else if (user.gender.equals("male")) {
                        bmrulet = (int) (66 + 13 * Float.parseFloat(weightNew.getText().toString()) +
                                5 * user.height - user.age);
                    }

                    final User newUser = new User(user.userName, user.password, user.gender, user.age, user.height, Float.parseFloat(weightNew.getText().toString()), user.goal_weight, bmrulet);
                    newUser.setUid(userId);
                    new UserRepository(getContext()).updateTask(newUser, new OnUserRepositoryActionListener() {
                        @Override
                        public void actionSucces() {
                            Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void actionFailed() {
                            Toast.makeText(getContext(), "User could not be updated!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mNotificationManager = (NotificationManager)
                getContext().getSystemService(NOTIFICATION_SERVICE);

        ToggleButton alarmToggle = view.findViewById(R.id.alarmToggle);
        // Set up the Notification Broadcast Intent.
        Intent notifyIntent = new Intent(getContext(), MyReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(getContext(), NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (getContext(), NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) getContext().getSystemService
                (ALARM_SERVICE);

        // Set the click listener for the toggle button.
        alarmToggle.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged
                            (CompoundButton buttonView, boolean isChecked) {
                        String toastMessage;
                        if (isChecked) {

                            long repeatInterval = 10*1000;

                            long triggerTime = SystemClock.elapsedRealtime()
                                    + repeatInterval;

                            if (alarmManager != null) {
                                alarmManager.setInexactRepeating
                                        (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                                triggerTime, repeatInterval,
                                                notifyPendingIntent);
                            }
                            toastMessage = "Alarm is on";

                        } else {
                            mNotificationManager.cancelAll();

                            if (alarmManager != null) {
                                alarmManager.cancel(notifyPendingIntent);
                            }
                            toastMessage = "Alarm is off";

                        }

                        Toast.makeText(getContext(), toastMessage,
                                Toast.LENGTH_SHORT).show();
                    }
                });

        createNotificationChannel();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnActivityFragmentCommunication){
            onActivityFragmentCommunication = (OnActivityFragmentCommunication) context;
        }
    }

    public void createNotificationChannel() {

        mNotificationManager =
                (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to " +
                    "stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
