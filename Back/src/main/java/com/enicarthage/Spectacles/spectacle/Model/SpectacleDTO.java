package com.enicarthage.Spectacles.spectacle.Model;


import java.math.BigDecimal;
import java.time.LocalDate;

public class SpectacleDTO {

    private Long id;
    private String titre;
    private LocalDate date;
    private BigDecimal heureDebut;
    private BigDecimal duree;
    private Integer nbSpectateurs;
    private String nomLieu;

    // ✅ Constructeur complet
    public SpectacleDTO(Long id, String titre, LocalDate date, BigDecimal heureDebut,
                        BigDecimal duree, Integer nbSpectateurs, String nomLieu) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.nbSpectateurs = nbSpectateurs;
        this.nomLieu = nomLieu;
    }

    // ✅ Getters & setters
    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getHeureDebut() {
        return heureDebut;
    }

    public BigDecimal getDuree() {
        return duree;
    }

    public Integer getNbSpectateurs() {
        return nbSpectateurs;
    }

    public String getNomLieu() {
        return nomLieu;
    }
}

