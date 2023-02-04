package com.example.foodonors;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {
    CardView btnDonateFood, btnGarabageWaste, btnLearningCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDonateFood = findViewById(R.id.btn_donate_food);
        btnGarabageWaste = findViewById(R.id.btn_garbage_waste);
        btnLearningCenter = findViewById(R.id.btn_learning_center);

        btnDonateFood.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DonateFood.class);
            startActivity(intent);
        });

        btnGarabageWaste.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);

        });
    }
}