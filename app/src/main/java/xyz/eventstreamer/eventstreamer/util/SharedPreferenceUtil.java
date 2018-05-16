package xyz.eventstreamer.eventstreamer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SharedPreferenceUtil {

    private SharedPreferences sharedPreferences;

    public SharedPreferenceUtil(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String retrieveString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean retrieveBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void saveLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long retrieveLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public int retrieveInteger(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void saveInteger(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveObject(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.apply();
    }

    public <T> T retrieveObject(String key, Type type) {
        Gson gson = new Gson();
        String json = retrieveString(key, null);
        return gson.fromJson(json, type);
    }

}
