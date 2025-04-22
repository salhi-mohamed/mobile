package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

       // Button btnGoToSecond = findViewById(R.id.btnGoToSecond);
       // ImageView logo = findViewById(R.id.logoImage);
        TextView sloganText = findViewById(R.id.sloganText);
        //logo.setScaleX(0.5f);
        //logo.setScaleY(0.5f);
       // logo.setAlpha(0f);
        ///logo.animate()
               // .scaleX(1f)
                //.scaleY(1f)
                //.alpha(1f)
                //.setDuration(1500)
               // .start();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, SpectacleListActivity.class));
            finish();
        }, 2500); // 2.5s

       /* btnGoToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpectacleListActivity.class);
                startActivity(intent);*/
            }
        }


      /**  // 1. Initialiser la liste des spectacles
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
        recyclerView.setAdapter(new SpectacleAdapter(spectacles));**/


