package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    EditText emailEditText, passwordEditText ;
    Button btnSignIn;

    TextView btnSignUp ;

    // Identifiants valides (statiques)
    private final String correctEmail = "spectaclemood@gmail.com";
    private final String correctPassword = "123456";

    TextView SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.nom);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp); // Changed to btnSignUp

        // Set click listener for the "Créer compte" text
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, Compte.class);
            startActivity(intent);
            // Don't call finish() here if you want to keep the login activity in the back stack
        });


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