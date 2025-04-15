package com.example.mobile;

import java.io.Serializable;

import java.io.Serializable;

public class Spectacle implements Serializable {
    private String titre;
    private String lieu;
    private String heure;
    private String places;
    private int imageResId;

    public Spectacle(String titre, String lieu, String heure, String places, int imageResId) {
        this.titre = titre;
        this.lieu = lieu;
        this.heure = heure;
        this.places = places;
        this.imageResId = imageResId;
    }

    public String getTitre() { return titre; }
    public String getLieu() { return lieu; }
    public String getHeure() { return heure; }
    public String getPlaces() { return places; }
    public int getImageResId() { return imageResId; }
}
