package com.example.foodonors;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class DonateFood extends AppCompatActivity {
    TextInputEditText etPrepTime;
    Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_food);

        locationButton = findViewById(R.id.btn_location);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
//                startActivity(i);
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
    }


}