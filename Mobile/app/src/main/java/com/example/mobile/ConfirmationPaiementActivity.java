package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationPaiementActivity extends AppCompatActivity {

    private TextView infoSpectacle, infoCategorie, infoClient, infoReservationId;
    private Button btnImprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_paiement);

        infoSpectacle = findViewById(R.id.infoSpectacle);
        infoCategorie = findViewById(R.id.infoCategorie);
        infoClient = findViewById(R.id.infoClient);
        infoReservationId = findViewById(R.id.infoReservationId);
        btnImprimer = findViewById(R.id.btnImprimer);

        // Récupération des données passées
        Intent intent = getIntent();
        String spectacle = intent.getStringExtra("titreSpectacle");
        String categorie = intent.getStringExtra("categorie");
        String client = intent.getStringExtra("nomClient");
        long reservationId = intent.getLongExtra("reservationId", -1);

        // Affichage des données
        infoSpectacle.setText("🎭 Spectacle : " + spectacle);
        infoCategorie.setText("🎟️ Catégorie : " + categorie.toUpperCase());
        infoClient.setText("👤 Client : " + client);
        infoReservationId.setText("🆔 Réservation N° : " + reservationId);

        btnImprimer.setOnClickListener(v -> {
            Toast.makeText(this, "Fonction d'impression à venir 📄", Toast.LENGTH_SHORT).show();
            // Tu peux ici générer un PDF ou utiliser PrintHelper pour impression réelle
        });
    }
}
