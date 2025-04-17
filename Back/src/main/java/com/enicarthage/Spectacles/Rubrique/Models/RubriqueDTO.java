package com.enicarthage.Spectacles.Rubrique.Models;

import java.math.BigDecimal;
import java.time.LocalTime;

public class RubriqueDTO {
    private Long id;
    private LocalTime heureDebut;
    private BigDecimal duree;
    private String type;
    private String nomArtiste;

    public RubriqueDTO(Long id, LocalTime heureDebut, BigDecimal duree, String type, String nomArtiste) {
        this.id = id;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.type = type;
        this.nomArtiste = nomArtiste;
    }

    public Long getId() { return id; }
    public LocalTime getHeureDebut() { return heureDebut; }
    public BigDecimal getDuree() { return duree; }
    public String getType() { return type; }
    public String getNomArtiste() { return nomArtiste; }
}
