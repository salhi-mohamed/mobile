package com.enicarthage.Spectacles.Lieu.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "LIEU")
public class Lieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLIEU")
    private Long id;

    @Column(name = "NOMLIEU", nullable = false)
    private String nom;

    @Column(name = "ADRESSE", nullable = false)
    private String adresse;

    @Column(name = "VILLE", nullable = false)
    private String ville;

    @Column(name = "CAPACITE", nullable = false)
    private Integer capacite;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }



   /** public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }**/

    // Getters & Setters
}

