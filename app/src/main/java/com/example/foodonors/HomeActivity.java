package com.example.foodonors;

import static com.example.foodonors.HelperClasses.SessionManager.SESSION_USERSESSION;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.foodonors.HelperClasses.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    CardView btnDonateFood, btnGarabageWaste, btnLearningCenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                SessionManager sessionManager = new SessionManager(this, SESSION_USERSESSION);
                sessionManager.logoutUserFromSession();

                FirebaseAuth.getInstance().signOut();

                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
            Intent intent = new Intent(getApplicationContext(), WasteManage.class);
            startActivity(intent);
        });

        btnLearningCenter.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LearnActivity.class);
            startActivity(intent);
        });
    }
}