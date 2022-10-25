package com.example.finalorangeproject.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class SaveData {
    SharedPreferences mSharedPreferences;
    public SaveData(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences("myData", Context.MODE_PRIVATE);
    }

    public void updateUserStatus(boolean status) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("status", status);
        editor.apply();
    }
    public void setUserData(String email,String name) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.apply();
    }
    public String getUserEmail() {
        return mSharedPreferences.getString("email","Email fot found" );
    }
    public String getUserName() {
        return mSharedPreferences.getString("name","Name fot found" );
    }

    public boolean getUserStatus() {
        return mSharedPreferences.getBoolean("status", false);
    }
}
