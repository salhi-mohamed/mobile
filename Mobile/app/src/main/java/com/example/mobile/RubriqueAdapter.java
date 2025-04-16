package com.example.mobile;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RubriqueAdapter extends RecyclerView.Adapter<RubriqueAdapter.ViewHolder> {
    private List<Rubrique> rubriqueList;

    public RubriqueAdapter(List<Rubrique> rubriqueList) {
        this.rubriqueList = rubriqueList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRubrique;

        public ViewHolder(View view) {
            super(view);
            textRubrique = view.findViewById(R.id.textRubrique);
        }
    }

    @NonNull
    @Override
    public RubriqueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rubrique_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RubriqueAdapter.ViewHolder holder, int position) {
        Rubrique rubrique = rubriqueList.get(position);
        holder.textRubrique.setText(rubrique.getTitre());
    }

    @Override
    public int getItemCount() {
        return rubriqueList.size();
    }
}

