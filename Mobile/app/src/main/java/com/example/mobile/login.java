package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button btnSignIn;

    // Identifiants valides (statiques)
    private final String correctEmail = "spectaclemood@gmail.com";
    private final String correctPassword = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {
            String enteredEmail = emailEditText.getText().toString().trim();
            String enteredPassword = passwordEditText.getText().toString();

            if (enteredEmail.equals(correctEmail) && enteredPassword.equals(correctPassword)) {
                Intent intent = new Intent(login.this, spectacles_list.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(login.this, "Email ou mot de passe incorrectes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}