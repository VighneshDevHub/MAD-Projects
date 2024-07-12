package com.example.registrationapp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private UserDatabaseManager userDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDatabaseManager = new UserDatabaseManager(this);
        userDatabaseManager.open();

        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        RadioGroup genderGroup = findViewById(R.id.genderGroup);
        CheckBox terms = findViewById(R.id.terms);
        Button registerButton = findViewById(R.id.registerButton);
        TextView login = findViewById(R.id.login);

        registerButton.setOnClickListener(view -> {
            String usernameStr = username.getText().toString().trim();
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();
            String confirmPasswordStr = confirmPassword.getText().toString().trim();
            int selectedGenderId = genderGroup.getCheckedRadioButtonId();

            if (usernameStr.isEmpty() || emailStr.isEmpty() || passwordStr.isEmpty() || confirmPasswordStr.isEmpty() || selectedGenderId == -1 || !terms.isChecked()) {
                Toast.makeText(MainActivity.this, "Please fill all fields and accept terms.", Toast.LENGTH_SHORT).show();
            } else if (!passwordStr.equals(confirmPasswordStr)) {
                Toast.makeText(MainActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else {
                RadioButton selectedGender = findViewById(selectedGenderId);
                User newUser = new User(usernameStr, emailStr, passwordStr, selectedGender.getText().toString());
                long userId = userDatabaseManager.insertUser(newUser);
                if (userId != -1) {
                    Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("username", newUser.getUsername());
                    intent.putExtra("email", newUser.getEmail());
                    intent.putExtra("gender", newUser.getGender());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "User already exists or registration failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDatabaseManager.close();
    }
}
