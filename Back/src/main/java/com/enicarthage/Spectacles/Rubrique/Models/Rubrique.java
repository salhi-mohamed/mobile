package com.enicarthage.Spectacles.Rubrique.Models;

import java.time.LocalTime;
import java.math.BigDecimal;

import com.enicarthage.Spectacles.Artiste.Model.Artiste;
import com.enicarthage.Spectacles.spectacle.Model.Spectacle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "RUBRIQUE")
public class Rubrique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRUB")
    private Long id;

    @Column(name = "H_DEBUTR", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "DUREERUB", nullable = false)
    private BigDecimal duree;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "IDSPEC", nullable = false)
    @JsonIgnoreProperties("rubriques") // 👈 empêche la boucle infinie
    private Spectacle spectacle;


    @ManyToOne
    @JoinColumn(name = "IDART", nullable = false)
    private Artiste artiste;

    // ======== GETTERS & SETTERS ========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public BigDecimal getDuree() {
        return duree;
    }

    public void setDuree(BigDecimal duree) {
        this.duree = duree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }
}
