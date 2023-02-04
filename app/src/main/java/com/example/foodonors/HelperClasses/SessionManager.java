package com.example.foodonors.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_SESSIONPHONENUMBER = "phoneNumber";
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

        userData.put(KEY_PHONENUMBER, usersSession.getString(KEY_PHONENUMBER, null));

        return userData;
    }

    public static HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_SESSIONPHONENUMBER, usersSession.getString(KEY_SESSIONPHONENUMBER, null));

        return userData;
    }

    public void createLoginSession(String phoneNo) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PHONENUMBER, phoneNo);

        editor.commit();
    }

    public void updateName(String phoneNo) {
        editor.clear();
        editor.remove(IS_LOGIN);
        editor.commit();

        createLoginSession(phoneNo);
    }

    public boolean checkLogin() {
        return usersSession.getBoolean(IS_LOGIN, false);
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.remove(IS_LOGIN);
        editor.commit();
    }

    public void createRememberMeSession(String phoneNo) {
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONPHONENUMBER, phoneNo);

        editor.commit();
    }

    public boolean checkRememberMe() {
        return usersSession.getBoolean(IS_REMEMBERME, false);
    }

}
