package com.example.foodonors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    Button next;
    TextInputLayout fullName, email, password, confirmpassword, phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.signup_full_name);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_pass);
        confirmpassword = findViewById(R.id.signup_con_pass);
        phoneNumber = findViewById(R.id.phone_num);
        countryCodePicker = findViewById(R.id.country_code_picker);

        next = findViewById(R.id.btn_signup_next);
        next.setOnClickListener(view -> CallNextSigupScreen(view));
    }

    private void CallNextSigupScreen(View view) {
        if (!validateFullName() | !validateEmail() | !validatePassword() | !validatePhoneNumber()) {
            return;
        }

        String _fullname = fullName.getEditText().getText().toString().trim();
        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

        Intent i = new Intent(getApplicationContext(), VerifyOTP.class);

        i.putExtra("fullName", _fullname);
        i.putExtra("email", _email);
        i.putExtra("password", _password);
        i.putExtra("phoneno", _phoneNo);

        startActivity(i);
    }


    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("Field Cannot be Empty !");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field Cannot be Empty !");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email !");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        Pattern pattern;
        Matcher matcher;

        String val1 = password.getEditText().getText().toString().trim();
        String val2 = confirmpassword.getEditText().getText().toString().trim();

        String checkPassword = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-zA-Z])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        pattern = Pattern.compile(checkPassword);
        matcher = pattern.matcher(val1);

        if (val1.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val1.equals(val2)) {
            password.setError("Passwords does not match");
            return false;
        } else if (!matcher.matches()) {
            password.setError("Password should contain 4 characters");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
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