package com.example.mobile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
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
    private Button btnReserver;

    private final String BASE_URL = "http://10.0.2.2:8091/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_detail);

        initViews();

        long spectacleId = getIntent().getLongExtra("spectacle_id", -1);
        if (spectacleId != -1) {
            fetchSpectacleDetails(spectacleId);
            fetchBilletsDisponiblesParCategorie(spectacleId);
        } else {
            Toast.makeText(this, "Spectacle invalide", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.lienSiteWeb).setOnClickListener(v -> openWebsite("https://www.festival-officiel.com"));

        btnReserver.setOnClickListener(v -> showCategorieDialog(spectacleId));
    }

    private void initViews() {
        titre = findViewById(R.id.titre);
        date = findViewById(R.id.date);
        heureDebut = findViewById(R.id.heureDebut);
        duree = findViewById(R.id.duree);
        nbSpectateurs = findViewById(R.id.nbSpectateurs);
        lieu = findViewById(R.id.lieu);
        rubriquesContainer = findViewById(R.id.rubriquesContainer);
        btnReserver = findViewById(R.id.btnReserver);
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Aucune application pour ouvrir le lien", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCategorieDialog(long spectacleId) {
        String[] categories = {"eco", "vip", "normal"};
        new AlertDialog.Builder(this)
                .setTitle("Choisir une catégorie")
                .setItems(categories, (dialog, which) -> showPaiementDialog(spectacleId, 5L, categories[which]))
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showPaiementDialog(long spectacleId, long clientId, String categorie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informations de paiement");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        EditText nomProprietaire = createEditText("Nom du propriétaire");
        EditText numeroCarte = createEditText("Numéro de carte");
        numeroCarte.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        EditText dateExpiration = createEditText("Date d'expiration (MM/AA)");

        layout.addView(createLabel("Nom du propriétaire :"));
        layout.addView(nomProprietaire);
        layout.addView(createLabel("Numéro de carte :"));
        layout.addView(numeroCarte);
        layout.addView(createLabel("Date d'expiration (MM/AA) :"));
        layout.addView(dateExpiration);

        builder.setView(layout);
        builder.setPositiveButton("Valider", (dialog, which) -> {
            if (nomProprietaire.getText().toString().isEmpty() ||
                    numeroCarte.getText().toString().isEmpty() ||
                    dateExpiration.getText().toString().isEmpty()) {

                Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            } else {
                reserverBillet(spectacleId, clientId, categorie);
            }
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private TextView createLabel(String text) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(16);
        return label;
    }

    private EditText createEditText(String hint) {
        EditText input = new EditText(this);
        input.setHint(hint);
        return input;
    }

    private void fetchSpectacleDetails(long id) {
        String url = BASE_URL + "spectacles/" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        titre.setText("🎭 " + response.getString("titre"));
                        date.setText("📅 " + response.getString("date"));
                        heureDebut.setText("🕒 " + response.getString("heureDebut"));
                        duree.setText("⏱ " + response.getString("duree") + "h");
                        nbSpectateurs.setText("👥 " + response.getInt("nbSpectateurs") + " spectateurs");
                        lieu.setText("📍 " + response.getString("nomLieu"));

                        rubriquesContainer.removeAllViews();
                        JSONArray rubriques = response.getJSONArray("rubriques");

                        for (int i = 0; i < rubriques.length(); i++) {
                            JSONObject r = rubriques.getJSONObject(i);
                            String content = "🔸 " + r.getString("type") + " à " + r.getString("heureDebut") + " (" + r.getString("duree") + "h)";
                            rubriquesContainer.addView(createCard(content));
                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "Erreur de parsing", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Erreur de chargement", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }

    private void fetchBilletsDisponiblesParCategorie(long spectacleId) {
        String url = BASE_URL + "billets/disponibles-par-categorie/" + spectacleId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    boolean hasAvailable = false;
                    Iterator<String> keys = response.keys();

                    while (keys.hasNext()) {
                        String categorie = keys.next();
                        int nb = response.optInt(categorie, 0);
                        String text = (nb > 0)
                                ? categorie.toUpperCase() + " : " + nb + " billets disponibles 🎟️"
                                : categorie.toUpperCase() + " : Aucun billet disponible ❌";
                        rubriquesContainer.addView(createLabel(text));
                        if (nb > 0) hasAvailable = true;
                    }

                    btnReserver.setEnabled(hasAvailable);
                    if (!hasAvailable) {
                        rubriquesContainer.addView(createLabel("Désolé, aucun billet n'est disponible pour ce spectacle."));
                    }
                },
                error -> {
                    Log.e("Billets", "Erreur : ", error);
                    Toast.makeText(this, "Erreur chargement billets", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }

    private LinearLayout createCard(String text) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(Color.WHITE);
        card.setPadding(32, 24, 32, 24);
        card.setElevation(6f);
        card.setBackgroundResource(R.drawable.card_background);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 12, 0, 12);
        card.setLayoutParams(params);

        TextView content = new TextView(this);
        content.setText(text);
        content.setTextSize(16);
        content.setTextColor(Color.parseColor("#444444"));
        card.addView(content);

        return card;
    }

    private void reserverBillet(long spectacleId, long clientId, String categorie) {
        String url = BASE_URL + "reservations?spectacleId=" + spectacleId + "&clientId=" + clientId + "&categorie=" + categorie;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> Toast.makeText(this, "Réservation réussie ✅", Toast.LENGTH_SHORT).show(),
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                        Toast.makeText(this, "La catégorie de billet n'est plus disponible.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Erreur lors de la réservation.", Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }
}
