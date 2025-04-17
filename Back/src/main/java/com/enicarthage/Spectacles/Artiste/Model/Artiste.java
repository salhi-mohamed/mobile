package com.enicarthage.Spectacles.Artiste.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "ARTISTE")
public class Artiste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDART")
    private Long id;

    @Column(name = "NOMART", nullable = false)
    private String nom;

    @Column(name = "PRENOMART", nullable = false)
    private String prenom;

    @Column(name = "SPECIALITE", nullable = false)
    private String specialite;

    // Getter et Setter pour l'ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter et Setter pour le nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter et Setter pour le prénom
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter et Setter pour la spécialité
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
