package com.example.mobile;

public class Spectacle {
    private Long id;
    private String titre;
    private String date;
    private String heureDebut;
    private String duree;
    private int nbSpectateurs;
    private String lieu; // ici, c'est le nom du lieu

    public Spectacle(Long id, String titre, String date, String heureDebut, String duree, int nbSpectateurs, String lieu) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.nbSpectateurs = nbSpectateurs;
        this.lieu = lieu;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getDate() { return date; }
    public String getHeureDebut() { return heureDebut; }
    public String getDuree() { return duree; }
    public int getNbSpectateurs() { return nbSpectateurs; }
    public String getLieu() { return lieu; }
}
