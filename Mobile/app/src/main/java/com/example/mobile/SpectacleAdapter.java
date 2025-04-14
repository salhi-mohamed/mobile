package com.example.mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.SpectacleViewHolder> {

    private final List<Spectacle> spectacles;

    // Constructeur
    public SpectacleAdapter(List<Spectacle> spectacles) {
        this.spectacles = spectacles;
    }

    // Crée la vue d'un élément
    @NonNull
    @Override
    public SpectacleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spectacle, parent, false);
        return new SpectacleViewHolder(view);
    }

    // Remplit les données dans la vue
    @Override
    public void onBindViewHolder(@NonNull SpectacleViewHolder holder, int position) {
        Spectacle spectacle = spectacles.get(position);
        holder.titre.setText(spectacle.getTitre());
        holder.lieu.setText(spectacle.getLieu());
        holder.heure.setText(spectacle.getHeure());
        holder.places.setText(spectacle.getPlaces());
    }

    // Nombre total d'éléments
    @Override
    public int getItemCount() {
        return spectacles.size();
    }

    // ViewHolder (optimise le RecyclerView)
    static class SpectacleViewHolder extends RecyclerView.ViewHolder {
        TextView titre, lieu, heure, places;

        public SpectacleViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre);
            lieu = itemView.findViewById(R.id.lieu);
            heure = itemView.findViewById(R.id.heure);
            places = itemView.findViewById(R.id.places);
        }
    }
}