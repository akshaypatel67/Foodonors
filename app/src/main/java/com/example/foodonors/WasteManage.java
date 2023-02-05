package com.example.foodonors;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonors.HelperClasses.FoodHelperClass;
import com.example.foodonors.HelperClasses.SessionManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class WasteManage extends AppCompatActivity {

    RecyclerView recyclerView;
    DonateAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_manage);

        HashMap<String, String> hashMap = SessionManager.getUsersDetailFromSession();
        String phoneNumber = hashMap.get("phoneNumber");

        recyclerView = findViewById(R.id.recyclerDonate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<FoodHelperClass> options =
                new FirebaseRecyclerOptions.Builder<FoodHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Food").child(phoneNumber), FoodHelperClass.class)
                        .build();

        adapter = new DonateAdapter(options);
        recyclerView.setAdapter(adapter);

        adapter.startListening();
    }
}