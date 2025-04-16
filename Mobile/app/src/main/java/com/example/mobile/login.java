package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText emailEditText, passwordEditText;
    Button btnSignIn;
    TextView btnSignUp;

    // URL de votre API backend
    private final String LOGIN_URL ="http://192.168.1.34:8091/api/auth/login";


    // File d'attente pour les requêtes réseau
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate: Activity créée");

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.nom);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Initialiser la file d'attente Volley
        requestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "File d'attente Volley initialisée");

        // Navigation vers l'écran d'inscription
        btnSignUp.setOnClickListener(v -> {
            Log.d(TAG, "Clic sur btnSignUp - Navigation vers Compte");
            Intent intent = new Intent(login.this, Compte.class);
            startActivity(intent);
        });

        // Gestion de la connexion
        btnSignIn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            Log.d(TAG, "Tentative de connexion avec email: " + email);

            if (email.isEmpty() || password.isEmpty()) {
                Log.w(TAG, "Champs vides détectés");
                Toast.makeText(login.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer l'objet JSON pour la requête
            JSONObject requestBody = new JSONObject();
            try {
                requestBody.put("email", email);
                requestBody.put("motDePasse", password);
                Log.d(TAG, "Corps de la requête créé: " + requestBody.toString());
            } catch (JSONException e) {
                Log.e(TAG, "Erreur création JSON: " + e.getMessage(), e);
                Toast.makeText(login.this, "Erreur de création de la requête", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer la requête POST avec headers
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    LOGIN_URL,
                    requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "Réponse reçue du serveur: " + response.toString());
                            try {
                                String token = response.getString("token");
                                Log.d(TAG, "Token reçu: " + token);

                                // Stocker le token si nécessaire (SharedPreferences, etc.)

                                // Rediriger vers l'écran principal
                                Log.d(TAG, "Redirection vers SpectacleListActivity");
                                Intent intent = new Intent(login.this, SpectacleListActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                Log.e(TAG, "Erreur parsing token: " + e.getMessage(), e);
                                Toast.makeText(login.this, "Erreur de traitement de la réponse", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Erreur Volley: " + error.getMessage(), error);

                            if (error.networkResponse != null) {
                                Log.e(TAG, "Code statut: " + error.networkResponse.statusCode);

                                if (error.networkResponse.data != null && error.networkResponse.data.length > 0) {
                                    try {
                                        String errorMessage = new JSONObject(new String(error.networkResponse.data))
                                                .getString("message");
                                        Toast.makeText(login.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        showAppropriateErrorMessage(error.networkResponse.statusCode);
                                    }
                                } else {
                                    showAppropriateErrorMessage(error.networkResponse.statusCode);
                                }
                            } else {
                                Toast.makeText(login.this,
                                        "Erreur de connexion au serveur",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    return headers;
                }
            };

            Log.d(TAG, "Envoi de la requête à: " + LOGIN_URL);
            requestQueue.add(jsonObjectRequest);
        });
    }

    private void showAppropriateErrorMessage(int statusCode) {
        switch (statusCode) {
            case 403:
                Toast.makeText(login.this,
                        "Accès refusé - Vérifiez vos identifiants ou les permissions",
                        Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText(login.this,
                        "Service non trouvé",
                        Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(login.this,
                        "Erreur serveur interne",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(login.this,
                        "Erreur serveur (HTTP " + statusCode + ")",
                        Toast.LENGTH_SHORT).show();
        }
    }
}