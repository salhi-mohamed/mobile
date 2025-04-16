package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Compte extends AppCompatActivity {

    EditText passwordEditText, passwordrepeatEditText;
    EditText NomEditText, PrenomEditText;
    Button btnSignIn;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        passwordrepeatEditText = findViewById(R.id.passwordrepeat);
        passwordEditText = findViewById(R.id.password2);
        NomEditText = findViewById(R.id.nom) ;
        PrenomEditText = findViewById(R.id.prenom) ;
        btnSignIn = findViewById(R.id.btnSignIn);
        login = findViewById(R.id.btnSignUp) ;

        login.setOnClickListener(v -> {
            Intent intent = new Intent(Compte.this, login.class);
            startActivity(intent);
            finish();
        });

        btnSignIn.setOnClickListener(v -> {
            String enteredPasswordrepeat = passwordrepeatEditText.getText().toString().trim();
            String enteredPassword = passwordEditText.getText().toString();
            String nom = NomEditText.getText().toString();
            String prenom = PrenomEditText.getText().toString();

            if (enteredPassword.equals(enteredPasswordrepeat)) {
                Toast.makeText(Compte.this, nom + " " +prenom + " inscrit avec succès", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Compte.this, login.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Compte.this, "Mot de passe incorrectes", Toast.LENGTH_SHORT).show();
            }
        });
    }


}