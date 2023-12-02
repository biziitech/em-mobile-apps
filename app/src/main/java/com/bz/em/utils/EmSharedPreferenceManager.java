package com.bz.em.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmSharedPreferenceManager {
    public static String MY_LOCATION_PREFERENCE = "com.em.loc_preference";
    private static final String LOGIN_PREFERENCE = "login_session";
    private static SharedPreferences userLoginPreference;
    private static SharedPreferences USER_LOCATION_PREFERENCE;
    private static SharedPreferences.Editor editor;
    private static Context context;


    //for save String value in sharedPreference
    public static void saveString(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static void saveDoubleVal(String key, double value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, Double.doubleToRawLongBits(value));
        editor.apply();
    }

    //get double value from sharedPreference
    public static double getDoubleVal(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(key, Double.doubleToLongBits(0));
    }

    //for save Long value in sharedPreference
    public static void saveLong(String key, Long value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }


    //get String value from sharedPreference
    public static String getString(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    //get Long value from sharedPreference
    public static Long getLongVal(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, 0);
    }


    //for save Int value in sharedPreference
    public static void saveInt(String key, int value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //get int value from sharedPreference
    public static int getIntVal(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }


    //for save user id and UserPassword in shared preferences
    public static void saveLoginPreferences(String key, String value, Context context) {
        userLoginPreference = context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        editor = userLoginPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getLoginPreferences(String key, Context context) {
        userLoginPreference = context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        // userLoginPreference.getString(key, null);
        return userLoginPreference.getString(key, null);
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);

    public static void saveDateVal(String key, Date value, Context context) {
        userLoginPreference = context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        editor = userLoginPreference.edit();
        editor.putString(key, sdf.format(value));
        editor.apply();
    }


}
