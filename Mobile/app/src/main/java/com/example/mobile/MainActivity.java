package com.example.mobile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialiser la liste des spectacles
        List<Spectacle> spectacles = new ArrayList<>();
        spectacles.add(new Spectacle("Festival de carthage", "l'amphitheatre Carthage", "18:49", "150 Places"));
        spectacles.add(new Spectacle("Mazzika orchestra", "THEÂTRE MUNICIPAL DE TUNIS", "", "60 Places"));
        spectacles.add(new Spectacle("Mazzika orchestra", "THEÂTRE MUNICIPAL DE TUNIS", "", "60 Places"));
        spectacles.add(new Spectacle("Mazzika orchestra", "THEÂTRE MUNICIPAL DE TUNIS", "", "60 Places"));
        spectacles.add(new Spectacle("Mazzika orchestra", "THEÂTRE MUNICIPAL DE TUNIS", "", "60 Places"));
        spectacles.add(new Spectacle("Mazzika orchestra", "THEÂTRE MUNICIPAL DE TUNIS", "", "60 Places"));
        spectacles.add(new Spectacle("Mazzika orchestra", "THEÂTRE MUNICIPAL DE TUNIS", "", "60 Places"));
        spectacles.add(new Spectacle("Mazzika orchestra", "THEÂTRE MUNICIPAL DE TUNIS", "", "60 Places"));

        // 2. Configurer le RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Lier l'Adapter au RecyclerView
        recyclerView.setAdapter(new SpectacleAdapter(spectacles));
    }
}