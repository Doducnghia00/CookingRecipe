package com.example.cookingrecipe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreferences {
    private final android.content.SharedPreferences prefs;

    public MySharedPreferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setDisplayName(String name) {
        prefs.edit().putString("name", name).apply();
    }

    public String getDisplayName() {
        return prefs.getString("name","");
    }

    public void setEmail(String email) {
        prefs.edit().putString("email", email).apply();
    }

    public String getEmail() {
        return prefs.getString("email","");
    }


}
