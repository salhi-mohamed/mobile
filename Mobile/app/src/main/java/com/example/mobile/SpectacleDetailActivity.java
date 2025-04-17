package com.example.mobile;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

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
        } else {
            Toast.makeText(this, "Spectacle invalide", Toast.LENGTH_SHORT).show();
        }
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
}