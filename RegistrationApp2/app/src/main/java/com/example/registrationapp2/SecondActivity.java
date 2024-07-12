package com.example.registrationapp2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // Retrieve user data from intent
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String gender = getIntent().getStringExtra("gender");

        // Display user data
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + username + "\nUser: " + username + "\nEmail: " + email + "\nGender: " + gender);
    }
}
