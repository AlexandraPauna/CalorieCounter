package com.example.caloriescounter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriescounter_app.database.Converters;
import com.example.caloriescounter_app.database.Meal;

import java.util.Calendar;

public class AddMeal_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        final EditText nameText = findViewById(R.id.et_meal_name);
        //final EditText dateText = new EditText(Calendar.getInstance().getTime().toString());
        final EditText brandText = findViewById(R.id.et_brand);
        final EditText servingSizeText = findViewById(R.id.et_serving_size);
        final EditText caloriesText = findViewById(R.id.et_calories);
        final EditText proteinText = findViewById(R.id.et_protein);
        final EditText fatText = findViewById(R.id.et_fat);
        final EditText carbsText = findViewById(R.id.et_carbs);

        final Context context = this;
        Button mainActivityBtn = (Button) findViewById(R.id.btn_add);
        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(
                        "com.example.caloriescounter_app", Context.MODE_PRIVATE);
                int userId = prefs.getInt("com.example.caloriescounter_app.userId", 0);

                if (nameText.getText().toString().isEmpty()) {
                    nameText.setError("Input required!");
                    nameText.requestFocus();
                } else if (brandText.getText().toString().isEmpty()) {
                    brandText.setError("Input required!");
                    brandText.requestFocus();
                } else if (servingSizeText.getText().toString().isEmpty()) {
                    servingSizeText.setError("Input required");
                    servingSizeText.requestFocus();
                } else if (caloriesText.getText().toString().isEmpty()) {
                    caloriesText.setError("Field required");
                    caloriesText.requestFocus();
                } else if (proteinText.getText().toString().isEmpty()) {
                    proteinText.setError("Field required!");
                    proteinText.requestFocus();
                } else if (fatText.getText().toString().isEmpty()) {
                    fatText.setError("Field required");
                    fatText.requestFocus();
                } else if (carbsText.getText().toString().isEmpty()) {
                    carbsText.setError("Field required");
                    carbsText.requestFocus();
                } else {

                    Meal meal = new Meal(nameText.getText().toString(), Converters.getCurrentDate(),
                            userId, brandText.getText().toString(), servingSizeText.getText().toString(),
                            Integer.parseInt(caloriesText.getText().toString()), Integer.parseInt(proteinText.getText().toString()),
                            Integer.parseInt(fatText.getText().toString()), Integer.parseInt(carbsText.getText().toString()));
                    MealRepository mealRepository = new MealRepository(context);

                    mealRepository.insertTask(meal, new OnMealRepositoryActionListener() {
                        @Override
                        public void actionSucces() {
                            Toast.makeText(context, "Meal added", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context, Main_Activity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void actionFailed() {
                            Toast.makeText(context, "Meal could not be added!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //how to pass information to second activity
                //startIntent.putExtra("com.example.tutorial2.SOMETHING", "HELLO WORLD!");
            }
        });
    }
}
