package com.example.mobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.SpectacleViewHolder> {

    private List<Spectacle> spectacleList;
    private Context context;

    // Constructeur avec Context pour lancer l'Intent
    public SpectacleAdapter(List<Spectacle> spectacleList, Context context) {
        this.spectacleList = spectacleList;
        this.context = context;
    }

    @NonNull
    @Override
    public SpectacleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spectacle, parent, false);
        return new SpectacleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SpectacleViewHolder holder, int position) {
        Spectacle spectacle = spectacleList.get(position);

        // Charger le titre et le lieu
        holder.titreTextView.setText(spectacle.getTitre());
        holder.lieuTextView.setText(spectacle.getLieu());

        // Charger l'image en fonction du spectacle
        int imageResId = getImageForSpectacle(spectacle);
        holder.imageView.setImageResource(imageResId);

        // Ajouter un clickListener pour ouvrir l'activité des détails
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SpectacleDetailActivity.class);
            intent.putExtra("spectacle_id", spectacle.getId()); // Passer l'ID du spectacle
            intent.putExtra("user_id", 5L); // ID statique du client
            context.startActivity(intent);
        });

    ;
    }


    private int getImageForSpectacle(Spectacle spectacle) {
        // Exemple d'attribution d'images par titre
        switch (spectacle.getTitre()) {
            case "Concert de Jazz":
                return R.drawable.image_spectacle_1;
            case "Concert de tarab":
                return R.drawable.image_spectacle_2;
            default:
                return R.drawable.image_spectacle_default;  // Image par défaut
        }
    }

    @Override
    public int getItemCount() {
        return spectacleList.size();
    }

    public static class SpectacleViewHolder extends RecyclerView.ViewHolder {
        TextView titreTextView, lieuTextView;
        ImageView imageView;

        public SpectacleViewHolder(@NonNull View itemView) {
            super(itemView);
            titreTextView = itemView.findViewById(R.id.item_titre);
            lieuTextView = itemView.findViewById(R.id.item_lieu);
            imageView = itemView.findViewById(R.id.item_image); // Ajout de l'ImageView
        }
    }
}
