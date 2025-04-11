package com.enicarthage.Spectacles.User.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "CLIENT")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCLT")
    private Long id;

    @Column(name = "NOMCLT", nullable = false)
    private String nom;

    @Column(name = "PRENOMCLT", nullable = false)
    private String prenom;

    @Column(name = "TEL", nullable = false)
    private String tel;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "MOTP", nullable = false)
    private String motDePasse;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    // Getters & Setters
}
