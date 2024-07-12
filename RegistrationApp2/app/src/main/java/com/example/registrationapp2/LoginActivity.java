package com.example.registrationapp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private UserDatabaseManager userDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userDatabaseManager = new UserDatabaseManager(this);
        userDatabaseManager.open();

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        TextView signUp = findViewById(R.id.signUp);
        TextView forgotPassword = findViewById(R.id.forgotpassword);

        loginButton.setOnClickListener(view -> {
            String usernameStr = username.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();

            if (usernameStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            } else {
                User user = userDatabaseManager.getUserByUsername(usernameStr);
                if (user != null) {
                    if (user.getPassword().equals(passwordStr)) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, LoggedActivity.class);
                        intent.putExtra("username", user.getUsername());
                        intent.putExtra("gender",user.getGender());
                        intent.putExtra("email",user.getEmail());
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        forgotPassword.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
        });


        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDatabaseManager.close();
    }
}
