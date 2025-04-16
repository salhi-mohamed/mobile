package com.example.mobile;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpectacleDetailActivity extends AppCompatActivity {

    private TextView titre, date, heureDebut, duree, nbSpectateurs, lieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_detail);

        titre = findViewById(R.id.detail_titre);
        date = findViewById(R.id.detail_date);
        heureDebut = findViewById(R.id.detail_heureDebut);
        duree = findViewById(R.id.detail_duree);
        nbSpectateurs = findViewById(R.id.detail_nbSpectateurs);
        lieu = findViewById(R.id.detail_lieu);

        Intent intent = getIntent();
        if (intent != null) {
            titre.setText(intent.getStringExtra("titre"));
            date.setText(intent.getStringExtra("date"));
            heureDebut.setText(intent.getStringExtra("heureDebut"));
            duree.setText(intent.getStringExtra("duree"));
            nbSpectateurs.setText(intent.getStringExtra("nbSpectateurs"));
            lieu.setText(intent.getStringExtra("lieu"));
        }
    }
}
