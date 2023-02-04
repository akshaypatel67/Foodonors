package com.example.foodonors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonors.HelperClasses.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button next;
    TextInputEditText phoneNumber;
    TextInputLayout phoneLayout;
    CountryCodePicker countryCodePicker;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        next = findViewById(R.id.btn_next);
        countryCodePicker = findViewById(R.id.country_code_picker);
        rememberMe = findViewById(R.id.cb_remember_me);

        phoneLayout = findViewById(R.id.phone_num);
        phoneNumber = findViewById(R.id.mbl);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> rememberMeDetails = SessionManager.getRememberMeDetailFromSession();
            phoneLayout.getEditText().setText(rememberMeDetails.get(SessionManager.KEY_PHONENUMBER));
        }

        next.setOnClickListener(view -> CompleteSignup(view));
    }


    private void CompleteSignup(View view) {

        if (!isConnected(getApplicationContext())) {
            showCustomDialog(getApplicationContext()).show();
        } else if (!validatePhoneNumber()) {
            return;
        } else {
            String _getUserEnteredPhoneNumber = phoneNumber.getText().toString().trim();
            String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

            final String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNo;

            if (rememberMe.isChecked()) {
                SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
                sessionManager.createRememberMeSession(_getUserEnteredPhoneNumber);
            }

            phoneLayout.setError(null);
            phoneLayout.setErrorEnabled(false);

            Intent i = new Intent(getApplicationContext(), VerifyOTP.class);
            i.putExtra("phoneno", _phoneNo);

            startActivity(i);
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if ((activeNetwork != null) && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else
            return (activeNetwork != null) && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public AlertDialog.Builder showCustomDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setMessage("Please Connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                });

        return builder;
    }

    private boolean validatePhoneNumber() {

        String val = phoneNumber.getText().toString().trim();

        if (val.isEmpty()) {
            phoneLayout.setError("Field can not be empty");
            return false;
        } else if (!android.util.Patterns.PHONE.matcher(val).matches()) {
            phoneLayout.setError("Enter valid phone number");
            return false;
        } else {
            phoneLayout.setError(null);
            phoneLayout.setErrorEnabled(false);
            return true;
        }
    }
}