package com.example.mobile;

public class Spectacle {
    private String titre;
    private String lieu;
    private String heure;
    private String places;

    public Spectacle(String titre, String lieu, String heure, String places) {
        this.titre = titre;
        this.lieu = lieu;
        this.heure = heure;
        this.places = places;
    }

    // Getters
    public String getTitre() { return titre; }
    public String getLieu() { return lieu; }
    public String getHeure() { return heure; }
    public String getPlaces() { return places; }
}
