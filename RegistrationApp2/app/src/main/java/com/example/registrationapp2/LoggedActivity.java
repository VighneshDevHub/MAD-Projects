// LoggedActivity.java
package com.example.registrationapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoggedActivity extends AppCompatActivity {
    private UserDatabaseManager userDatabaseManager;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_activity);

        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView userdetails  = findViewById(R.id.userdetails);
        Button logOutButton = findViewById(R.id.logoutButton);
        Button deleteAccountButton = findViewById(R.id.deleteAccountButton);


        userDatabaseManager = new UserDatabaseManager(this);
        userDatabaseManager.open();

        username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String gender = getIntent().getStringExtra("gender");

        welcomeText.setText("Welcome, " + username + "!");

        userdetails.setText("Name: " + username + "\nEmail: " + email + "\nGender: "+gender);




        logOutButton.setOnClickListener(v -> logOut());

        deleteAccountButton.setOnClickListener(v -> confirmDeleteAccount());
    }

    private void logOut() {
        Intent intent = new Intent(LoggedActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
    }

    private void confirmDeleteAccount() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteAccount())
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void deleteAccount() {
        boolean success = userDatabaseManager.deleteUserByUsername(username);
        if (success) {
            Toast.makeText(this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoggedActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to delete account.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDatabaseManager.close();
    }
}









































//package com.example.registrationapp2;
//
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class LoggedActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.logged_activity);
//
//        String username = getIntent().getStringExtra("username");
//        String email = getIntent().getStringExtra("email");
//        String gender = getIntent().getStringExtra("gender");
//
//        TextView welcomeText = findViewById(R.id.welcomeText);
//        welcomeText.setText("Welcome, " + username);
//
//        TextView userdetails  = findViewById(R.id.userdetails);
//        userdetails.setText("Name: " + username + "\nEmail: " + email + "\nGender: "+gender);
//    }
//}
