package com.example.foodonors.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_SESSIONPHONENUMBER = "phoneNumber";
    public static final String KEY_SESSIONPASSWORD = "password";
    private static final String IS_LOGIN = "isLoggedIn";
    private static final String IS_REMEMBERME = "isRememberMe";
    static SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context _context, String sessionName) {
        context = _context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public static HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_FULLNAME, usersSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_PHONENUMBER, usersSession.getString(KEY_PHONENUMBER, null));

        return userData;
    }

    public static HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_SESSIONPHONENUMBER, usersSession.getString(KEY_SESSIONPHONENUMBER, null));
        userData.put(KEY_SESSIONPASSWORD, usersSession.getString(KEY_SESSIONPASSWORD, null));

        return userData;
    }

    public void createLoginSession(String fullName, String email, String password, String phoneNo) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONENUMBER, phoneNo);

        editor.commit();
    }

    public void updateName(String fullName, String email, String password, String phoneNo) {
        editor.clear();
        editor.remove(IS_LOGIN);
        editor.commit();

        createLoginSession(fullName, email, password, phoneNo);
    }

    public boolean checkLogin() {
        return usersSession.getBoolean(IS_LOGIN, false);
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.remove(IS_LOGIN);
        editor.commit();
    }

    public void createRememberMeSession(String phoneNo, String password) {

        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONPHONENUMBER, phoneNo);
        editor.putString(KEY_SESSIONPASSWORD, password);

        editor.commit();
    }

    public boolean checkRememberMe() {
        return usersSession.getBoolean(IS_REMEMBERME, false);
    }

}
