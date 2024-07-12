package com.example.registrationapp2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private UserDatabaseManager userDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);

        userDatabaseManager = new UserDatabaseManager(this);
        userDatabaseManager.open();

        EditText editTextUsername = findViewById(R.id.username);
        EditText editTextNewPassword = findViewById(R.id.newpassword);
        EditText editTextConfirmPassword = findViewById(R.id.confirmPassword);
        Button buttonResetPassword = findViewById(R.id.resetPasswordButton);

        buttonResetPassword.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String newPassword = editTextNewPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else {
                // Check if the username exists in the database
                User user = userDatabaseManager.getUserByUsername(username);
                if (user != null) {
                    // Update the user's password in the database
                    boolean success = userDatabaseManager.updatePassword(username, newPassword);
                    if (success) {
                        Toast.makeText(this, "Password reset successfully.", Toast.LENGTH_SHORT).show();
                        finish(); // Optionally, navigate back to login or main activity
                    } else {
                        Toast.makeText(this, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Username not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDatabaseManager.close();
    }
}
