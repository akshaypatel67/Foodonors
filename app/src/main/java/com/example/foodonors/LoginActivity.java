package com.example.foodonors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {
    Button next;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        next = findViewById(R.id.btn_next);
        phoneNumber = findViewById(R.id.phone_num);
        countryCodePicker = findViewById(R.id.country_code_picker);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompleteSignup(view);
            }
        });
    }


    private void CompleteSignup(View view) {

        if (!validatePhoneNumber()) {
            return;
        }

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

        Intent i = new Intent(getApplicationContext(), VerifyOTP.class);
        i.putExtra("phoneno", _phoneNo);

        startActivity(i);
    }

    private boolean validatePhoneNumber() {

        String val = phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError("Field can not be empty");
            return false;
        } else if (!android.util.Patterns.PHONE.matcher(val).matches()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}