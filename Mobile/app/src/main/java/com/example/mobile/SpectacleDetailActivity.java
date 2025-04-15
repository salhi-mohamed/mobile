package com.example.mobile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpectacleDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_detail);

        Spectacle spectacle = (Spectacle) getIntent().getSerializableExtra("spectacle");

        ImageView imageView = findViewById(R.id.imageAffiche);
        TextView titre = findViewById(R.id.textTitre);
        TextView lieu = findViewById(R.id.textLieu);
        TextView heure = findViewById(R.id.textHeure);
        TextView places = findViewById(R.id.textPlaces);
        RecyclerView recyclerRubriques = findViewById(R.id.recyclerRubriques);
        recyclerRubriques.setLayoutManager(new LinearLayoutManager(this));

        List<Rubrique> rubriques = new ArrayList<>();
        rubriques.add(new Rubrique("Artistes"));
        rubriques.add(new Rubrique("Programme"));
        rubriques.add(new Rubrique("Infos pratiques"));

        RubriqueAdapter adapter = new RubriqueAdapter(rubriques);
        recyclerRubriques.setAdapter(adapter);





        imageView.setImageResource(spectacle.getImageResId());
        titre.setText(spectacle.getTitre());
        lieu.setText("Lieu : " + spectacle.getLieu());
        heure.setText("Heure : " + spectacle.getHeure());
        places.setText("Places disponibles : " + spectacle.getPlaces());
    }
}
