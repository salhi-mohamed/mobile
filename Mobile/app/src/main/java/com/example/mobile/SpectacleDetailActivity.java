package com.example.mobile;

import android.content.SharedPreferences;
import android.graphics.Color;
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

                        long clientId = 5L; // ID statique du client
                        showPaiementDialog(spectacleId, clientId, categorieChoisie);

                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });

    }

    private void showPaiementDialog(long spectacleId, long clientId, String categorie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informations de paiement");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        TextView labelNom = new TextView(this);
        labelNom.setText("Nom du propriétaire :");
        labelNom.setTextSize(16);
        layout.addView(labelNom);

        final android.widget.EditText nomProprietaire = new android.widget.EditText(this);
        nomProprietaire.setHint("Ex : Jean Dupont");
        layout.addView(nomProprietaire);

        TextView labelCarte = new TextView(this);
        labelCarte.setText("Numéro de carte :");
        labelCarte.setTextSize(16);
        layout.addView(labelCarte);

        final android.widget.EditText numeroCarte = new android.widget.EditText(this);
        numeroCarte.setHint("Ex : 1234 5678 9012 3456");
        numeroCarte.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(numeroCarte);

        TextView labelDate = new TextView(this);
        labelDate.setText("Date d'expiration (MM/AA) :");
        labelDate.setTextSize(16);
        layout.addView(labelDate);

        final android.widget.EditText dateExpiration = new android.widget.EditText(this);
        dateExpiration.setHint("Ex : 06/26");
        layout.addView(dateExpiration);

        builder.setView(layout);

        builder.setPositiveButton("Valider", (dialog, which) -> {
            String nom = nomProprietaire.getText().toString().trim();
            String numero = numeroCarte.getText().toString().trim();
            String dateExp = dateExpiration.getText().toString().trim();

            if (nom.isEmpty() || numero.isEmpty() || dateExp.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs de paiement.", Toast.LENGTH_SHORT).show();
            } else {
                reserverBillet(spectacleId, clientId, categorie);
            }
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
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
                            rubText.setTextSize(16);
                            rubText.setTextColor(Color.parseColor("#333333"));
                            rubText.setBackgroundColor(Color.parseColor("#F1F1F1"));
                            rubText.setPadding(24, 16, 24, 16);
                            rubText.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(0, 8, 0, 8);
                            rubText.setLayoutParams(params);

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
                        Log.d("SpectacleDetailActivity", "Réponse JSON reçue : " + response.toString());

                        boolean hasAvailableTickets = false;

                        Iterator<String> keys = response.keys();
                        while (keys.hasNext()) {
                            String categorie = keys.next();
                            int nbDisponibles = response.getInt(categorie);

                            Log.d("SpectacleDetailActivity", "Catégorie : " + categorie + ", Billets disponibles : " + nbDisponibles);

                            TextView categorieText = new TextView(this);
                            if (nbDisponibles > 0) {
                                categorieText.setText(categorie.toUpperCase() + " : " + nbDisponibles + " billets disponibles");
                                hasAvailableTickets = true;
                            } else {
                                categorieText.setText(categorie.toUpperCase() + " : Aucun billet disponible");
                            }
                            rubriquesContainer.addView(categorieText);
                        }

                        if (!hasAvailableTickets) {
                            TextView noTicketsText = new TextView(this);
                            noTicketsText.setText("Désolé, aucun billet n'est disponible pour ce spectacle.");
                            rubriquesContainer.addView(noTicketsText);

                            Button btnReserver = findViewById(R.id.btnReserver);
                            btnReserver.setEnabled(false);
                        } else {
                            Button btnReserver = findViewById(R.id.btnReserver);
                            btnReserver.setEnabled(true);
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
                response -> {
                    Toast.makeText(this, "Réservation réussie ✅", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                        Toast.makeText(this, "La catégorie de billet choisie n'est plus disponible.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Erreur lors de la réservation. Vérifiez la disponibilité des billets.", Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }

}
