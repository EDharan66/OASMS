package com.demoapp.demo.Databases;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyNotYetValidException;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;


public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //session name
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";


    //user session variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_PHONENO = "phoneNo";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";

    //remember me variable
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONPHONENO = "phoneNo";
    public static final String KEY_SESSIONPASSWORD = "password";


    //constructor
    public SessionManager(Context _context,String sessionName) {

        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();

    }


    public void createLoginSession(String fullName, String phoneNo, String email, String password, String date) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_PHONENO, phoneNo);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE, date);

        editor.commit();

    }

    public HashMap<String, String> getUserDetailFromSession() {

        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_PHONENO, userSession.getString(KEY_PHONENO, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));

        return userData;
    }


    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {

            return true;
        } else
            return false;
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }


    public void createRememberMeSession(String phoneNo, String password) {

        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONPHONENO, phoneNo);
        editor.putString(KEY_SESSIONPASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {

        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONPHONENO, userSession.getString(KEY_SESSIONPHONENO, null));
        userData.put(KEY_SESSIONPASSWORD, userSession.getString(KEY_SESSIONPASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe() {
        if (userSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else
            return false;
    }

}
