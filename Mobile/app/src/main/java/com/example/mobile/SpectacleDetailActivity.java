package com.example.mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class SpectacleDetailActivity extends AppCompatActivity {

    private TextView titre, date, heureDebut, duree, nbSpectateurs, lieu;
    private LinearLayout rubriquesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_detail);

        titre = findViewById(R.id.titre);
        date = findViewById(R.id.date);
        heureDebut = findViewById(R.id.heureDebut);
        duree = findViewById(R.id.duree);
        nbSpectateurs = findViewById(R.id.nbSpectateurs);
        lieu = findViewById(R.id.lieu);
        rubriquesContainer = findViewById(R.id.rubriquesContainer);

        long spectacleId = getIntent().getLongExtra("spectacle_id", -1);
        if (spectacleId != -1) {
            fetchSpectacleDetails(spectacleId);
            fetchBilletsDisponiblesParCategorie(spectacleId);
        } else {
            Toast.makeText(this, "Spectacle invalide", Toast.LENGTH_SHORT).show();
        }

        Button btnReserver = findViewById(R.id.btnReserver);
        btnReserver.setOnClickListener(v -> {
            String[] categories = {"eco", "vip", "normal"};

            new AlertDialog.Builder(this)
                    .setTitle("Choisir une catégorie")
                    .setItems(categories, (dialog, which) -> {
                        String categorieChoisie = categories[which];

                        // 🔁 Récupération dynamique du client ID
                        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                        long clientId = prefs.getLong("client_id", -1);

                        if (clientId == -1) {
                            Toast.makeText(this, "Erreur : utilisateur non connecté", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        reserverBillet(spectacleId, clientId, categorieChoisie);
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });
    }

    private void fetchSpectacleDetails(long id) {
        String url = "http://10.0.2.2:8091/api/spectacles/" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        titre.setText("Titre : " + response.getString("titre"));
                        date.setText("Date : " + response.getString("date"));
                        heureDebut.setText("Heure : " + response.getString("heureDebut"));
                        duree.setText("Durée : " + response.getString("duree"));
                        nbSpectateurs.setText("Nombre de spectateurs : " + response.getInt("nbSpectateurs"));
                        lieu.setText("Lieu : " + response.getString("nomLieu"));

                        JSONArray rubriques = response.getJSONArray("rubriques");
                        for (int i = 0; i < rubriques.length(); i++) {
                            JSONObject r = rubriques.getJSONObject(i);

                            TextView rubText = new TextView(this);
                            rubText.setText("🔸 " + r.getString("type") + " à " + r.getString("heureDebut") +
                                    " (" + r.getString("duree") + "h)");
                            rubriquesContainer.addView(rubText);
                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "Erreur de parsing", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }

    private void fetchBilletsDisponiblesParCategorie(long spectacleId) {
        String url = "http://10.0.2.2:8091/api/billets/disponibles-par-categorie/" + spectacleId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Log pour vérifier la réponse brute
                        Log.d("SpectacleDetailActivity", "Réponse JSON reçue : " + response.toString());

                        // Variable pour vérifier s'il y a au moins un billet disponible
                        boolean hasAvailableTickets = false;

                        // Parcours du JSONObject pour obtenir les catégories et leur nombre de billets disponibles
                        Iterator<String> keys = response.keys();
                        while (keys.hasNext()) {
                            String categorie = keys.next();
                            int nbDisponibles = response.getInt(categorie);

                            // Log pour chaque catégorie et son nombre de billets
                            Log.d("SpectacleDetailActivity", "Catégorie : " + categorie + ", Billets disponibles : " + nbDisponibles);

                            // Affichage du nombre de billets disponibles pour chaque catégorie
                            TextView categorieText = new TextView(this);
                            if (nbDisponibles > 0) {
                                categorieText.setText(categorie.toUpperCase() + " : " + nbDisponibles + " billets disponibles");
                                hasAvailableTickets = true;
                            } else {
                                categorieText.setText(categorie.toUpperCase() + " : Aucun billet disponible");
                            }
                            rubriquesContainer.addView(categorieText);
                        }

                        // Si aucun billet n'est disponible, affiche un message
                        if (!hasAvailableTickets) {
                            TextView noTicketsText = new TextView(this);
                            noTicketsText.setText("Désolé, aucun billet n'est disponible pour ce spectacle.");
                            rubriquesContainer.addView(noTicketsText);

                            // Désactive le bouton de réservation
                            Button btnReserver = findViewById(R.id.btnReserver);
                            btnReserver.setEnabled(false); // Désactive le bouton
                        } else {
                            // Si des billets sont disponibles, assure-toi que le bouton soit activé
                            Button btnReserver = findViewById(R.id.btnReserver);
                            btnReserver.setEnabled(true); // Active le bouton
                        }

                    } catch (Exception e) {
                        Log.e("SpectacleDetailActivity", "Erreur lors du parsing des catégories", e);
                        Toast.makeText(this, "Erreur de parsing des catégories", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("SpectacleDetailActivity", "Erreur de requête : " + error.getMessage(), error);
                    Toast.makeText(this, "Erreur de chargement des billets disponibles", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }



    private void reserverBillet(long spectacleId, long clientId, String categorie) {
        String url = "http://10.0.2.2:8091/api/reservations"
                + "?spectacleId=" + spectacleId
                + "&clientId=" + clientId
                + "&categorie=" + categorie;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> Toast.makeText(this, "Réservation réussie ✅", Toast.LENGTH_SHORT).show(),
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Erreur : " + error.getMessage(), Toast.LENGTH_LONG).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
