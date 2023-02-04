package com.example.foodonors;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonors.HelperClasses.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button next;
    CountryCodePicker countryCodePicker;
    CheckBox rememberMe;
    TextInputLayout phoneLayout, passLayout;
    TextInputEditText phone, pass;
    Button login, createacc;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        next = findViewById(R.id.btn_next);
        countryCodePicker = findViewById(R.id.country_code_picker);
        rememberMe = findViewById(R.id.cb_remember_me);

        phoneLayout = findViewById(R.id.phone_num);
        passLayout = findViewById(R.id.pass_layout);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.btn_login);
        createacc = findViewById(R.id.btn_create_acc);
        rememberMe = findViewById(R.id.cb_remember_me);
        countryCodePicker = findViewById(R.id.country_code_picker);

        phone.setTranslationX(800);
        pass.setTranslationX(800);
        login.setTranslationX(800);
        createacc.setTranslationX(800);
        rememberMe.setTranslationX(800);
        countryCodePicker.setTranslationX(800);

        phone.setAlpha(v);
        pass.setAlpha(v);
        login.setAlpha(v);
        createacc.setAlpha(v);
        rememberMe.setAlpha(v);
        countryCodePicker.setAlpha(v);

        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        createacc.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        rememberMe.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        countryCodePicker.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();

        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                phone.setHint("Phone Number");
            }
        });
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pass.setHint("Password");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letTheUserLogin(view);
            }
        });

        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> rememberMeDetails = SessionManager.getRememberMeDetailFromSession();
            phone.setText(rememberMeDetails.get(SessionManager.KEY_PHONENUMBER));
            pass.setText(rememberMeDetails.get(SessionManager.KEY_PASSWORD));
        }
    }


    private void letTheUserLogin(View view) {

        if (!isConnected(getApplicationContext())) {
            showCustomDialog(getApplicationContext()).show();
        } else {
            if (!validateFields()) {
                return;
            }

            String _phoneNumber = phoneLayout.getEditText().getText().toString().trim();
            final String _password = passLayout.getEditText().getText().toString().trim();

            if (_phoneNumber.charAt(0) == '0') {
                _phoneNumber = _phoneNumber.substring(1);
            }

            final String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;

            if (rememberMe.isChecked()) {
                SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_REMEMBERME);
                sessionManager.createRememberMeSession(_phoneNumber, _password);
            }

            Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
            Log.d("Login", _completePhoneNumber);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Log.d("Login", "YES");
                        phoneLayout.setError(null);
                        phoneLayout.setErrorEnabled(false);

                        String systemPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                        if (systemPassword.equals(_password)) {
                            passLayout.setError(null);
                            passLayout.setErrorEnabled(false);

                            String _fullname = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                            String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                            String _password = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                            String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);

                            SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
                            sessionManager.createLoginSession(_fullname, _email, _password, _phoneNo);

                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                            Toast.makeText(getApplicationContext(), _fullname + "\n" + "User Logged in", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Password does not Match!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("Login", "NO");
                        Toast.makeText(getApplicationContext(), "No such user exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
                        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                    }
                });

        return builder;
    }

    private boolean validateFields() {
        String _phoneNo = phoneLayout.getEditText().getText().toString().trim();
        String _password = passLayout.getEditText().getText().toString().trim();

        if (_phoneNo.isEmpty()) {
            phoneLayout.setError("Field can not be empty");
            phoneLayout.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            passLayout.setError("Field can not be empty");
            passLayout.requestFocus();
            return false;
        } else {
            phoneLayout.setError(null);
            phoneLayout.setErrorEnabled(false);
            passLayout.setError(null);
            passLayout.setErrorEnabled(false);
            return true;
        }
    }
}