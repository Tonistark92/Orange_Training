package com.example.finalorangeproject.screens.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import com.example.finalorangeproject.R;

public class WelcomeActivity extends AppCompatActivity {

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        b = findViewById(R.id.getstarted);
        b.setOnClickListener(view -> {
            Intent myIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(myIntent);
        });
    }
}