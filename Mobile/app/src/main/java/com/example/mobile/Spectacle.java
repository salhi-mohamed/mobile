package com.example.mobile;

public class Spectacle {
    private String titre;
    private String lieu;
    private String heure;
    private String places;

    private int imageResId;
    public Spectacle(String titre, String lieu, String heure, String places,int imageResId) {
        this.titre = titre;
        this.lieu = lieu;
        this.heure = heure;
        this.places = places;
        this.imageResId = imageResId;
    }

    // Getters
    public String getTitre() { return titre; }
    public String getLieu() { return lieu; }
    public String getHeure() { return heure; }
    public String getPlaces() { return places; }

    public int getImageResId() { return imageResId; }
}
