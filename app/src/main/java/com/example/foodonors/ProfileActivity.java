package com.example.foodonors;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonors.HelperClasses.SessionManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_email, tv_phonenum;
    ImageButton btnSubmit;
    EditText et_name;
    ImageView editName;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        et_name = findViewById(R.id.et_profile_name);
        tv_email = findViewById(R.id.tv_profile_email);
        tv_phonenum = findViewById(R.id.tv_profile_phone_num);
        editName = findViewById(R.id.profile_edit_name);
        btnSubmit = findViewById(R.id.btn_submit);

        final SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
        final HashMap<String, String> userDetails = SessionManager.getUsersDetailFromSession();

        final String fullName = userDetails.get(SessionManager.KEY_FULLNAME);
        final String email = userDetails.get(SessionManager.KEY_EMAIL);
        phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);

        et_name.setText(fullName);
        tv_email.setText(email);
        tv_phonenum.setText(phoneNumber);

        editName.setOnClickListener(view -> {
            et_name.setEnabled(true);
            et_name.requestFocus();
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    editName.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.VISIBLE);
                } else {
                    editName.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                }
            }
        });

        et_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnSubmit.performClick();
                    handled = true;
                }
                return handled;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference();
                reference.child("Users").child(phoneNumber).child("fullName").setValue(et_name.getText().toString());

                et_name.setEnabled(false);
                et_name.clearFocus();

                editName.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.GONE);

                sessionManager.updateName(et_name.getText().toString(), email, userDetails.get(SessionManager.KEY_PASSWORD), phoneNumber);
            }
        });
    }
}