package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Compte extends AppCompatActivity {

    EditText passwordEditText, passwordrepeatEditText;
    EditText NomEditText, PrenomEditText, emailEditText, telEditText;
    Button btnSignIn;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        // Autoriser les appels réseau sur le thread principal (à éviter en prod !)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Initialisation des champs
        passwordrepeatEditText = findViewById(R.id.passwordrepeat);
        passwordEditText = findViewById(R.id.password2);
        NomEditText = findViewById(R.id.nom);
        PrenomEditText = findViewById(R.id.prenom);
        emailEditText = findViewById(R.id.email);
        telEditText = findViewById(R.id.tel);
        btnSignIn = findViewById(R.id.btnSignIn);
        login = findViewById(R.id.btnSignUp);

        login.setOnClickListener(v -> {
            Intent intent = new Intent(Compte.this, login.class);
            startActivity(intent);
            finish();
        });

        btnSignIn.setOnClickListener(v -> {
            String nom = NomEditText.getText().toString().trim();
            String prenom = PrenomEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String tel = telEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();
            String repeatPassword = passwordrepeatEditText.getText().toString();

            // Vérification des mots de passe
            if (!password.equals(repeatPassword)) {
                Toast.makeText(Compte.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Créer un JSON à envoyer
                JSONObject json = new JSONObject();
                json.put("nom", nom);
                json.put("prenom", prenom);
                json.put("email", email);
                json.put("tel", tel);
                json.put("motDePasse", password);

                // URL de ton backend
                URL url = new URL("http://192.168.1.34:8091/api/auth/register");
                Log.d("Compte", "URL de l'API : " + url.toString());

                // Configurer la connexion HTTP
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                Log.d("Compte", "Tentative d'envoi des données : " + json.toString());

                // Envoyer les données
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                Log.d("Compte", "Code de réponse de l'API : " + responseCode);

                if (responseCode == 403) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Log.e("Compte", "Réponse de l'API (Erreur 403) : " + response.toString());
                }

                // Vérification de la réponse de l'API
                if (responseCode == 200 || responseCode == 201) {
                    Log.d("Compte", "Inscription réussie");
                    Toast.makeText(Compte.this, "Inscription réussie", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Compte.this, login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("Compte", "Erreur lors de l'inscription, code : " + responseCode);
                    Toast.makeText(Compte.this, "Erreur d'inscription : " + responseCode, Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Log.e("Compte", "Exception lors de l'inscription", e);
                e.printStackTrace();
                Toast.makeText(Compte.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
