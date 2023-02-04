package com.example.foodonors;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btnDonateFood, btnGarabageWaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDonateFood = findViewById(R.id.btn_donate_food);
        btnGarabageWaste = findViewById(R.id.btn_garbage_waste);

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