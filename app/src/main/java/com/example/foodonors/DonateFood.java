package com.example.foodonors;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonors.HelperClasses.FoodHelperClass;
import com.example.foodonors.HelperClasses.SessionManager;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DonateFood extends AppCompatActivity {
    TextInputEditText etPrepTime, quantity, contents;
    TextView locationText;
    Button locationButton, submitButton;
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_food);

        locationButton = findViewById(R.id.btn_location);
        locationText = findViewById(R.id.txt_address);

        quantity = findViewById(R.id.quantity);
        contents = findViewById(R.id.contents);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(i, 2);
            }
        });

        etPrepTime = findViewById(R.id.et_prep_time);
        etPrepTime.setOnClickListener((View view) -> {
            Calendar c = Calendar.getInstance();

            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a
            // variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            String dat = (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            etPrepTime.setText(dat);
                        }
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
            );

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            String tim = (hour + ":" + minute);
                            etPrepTime.setText(etPrepTime.getText() + " " + tim);
                        }
                    },
                    hour,
                    minute,
                    true
            );
            // at last we are calling show
            // to display our date picker dialog
            timePickerDialog.show();
            datePickerDialog.show();
        });

        submitButton = findViewById(R.id.btn_next);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        HashMap<String, String> hashMap = SessionManager.getUsersDetailFromSession();
        String phoneNumber = hashMap.get("phoneNumber");

        String preparationTime = etPrepTime.getText().toString();
        int qty = Integer.parseInt(quantity.getText().toString());
        String desc = contents.getText().toString();

        FoodHelperClass foodData = new FoodHelperClass(phoneNumber, preparationTime, desc, qty);

        FirebaseDatabase.getInstance().getReference("Food")
                .child(phoneNumber)
                .setValue(foodData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        setIntent(intent);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2) {
            if(resultCode == 2) {
                Log.d("test", "test");
                Double latitude = data.getDoubleExtra("latitude", 0);
                Double longitude = data.getDoubleExtra("longitude", 0);

                location = new LatLng(latitude, longitude);

                //            locationName.setText((int) (location.latitude + location.longitude));
                System.out.println("location" + location);


                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> addresses  = null;
                try {
                    addresses = geocoder.getFromLocation(location.latitude ,location.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = addresses.get(0).getAddressLine(0);

                locationText.setText(address);
            }

            else {
                location = null;
            }
        }
    }
}