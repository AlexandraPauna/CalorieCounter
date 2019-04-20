package com.example.caloriescounter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddMeal_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Button mainActivityBtn = (Button) findViewById(R.id.btn_add);
        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Main_Activity.class);

                //how to pass information to second activity
                //startIntent.putExtra("com.example.tutorial2.SOMETHING", "HELLO WORLD!");
                startActivity(startIntent);
            }
        });
    }
}
