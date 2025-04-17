package com.enicarthage.Spectacles.spectacle.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.enicarthage.Spectacles.Rubrique.Models.RubriqueDTO;

public class SpectacleAvecRubriquesDTO {
    private Long id;
    private String titre;
    private LocalDate date;
    private BigDecimal heureDebut;
    private BigDecimal duree;
    private Integer nbSpectateurs;
    private String nomLieu;
    private List<RubriqueDTO> rubriques;

    public SpectacleAvecRubriquesDTO(Long id, String titre, LocalDate date, BigDecimal heureDebut,
                                     BigDecimal duree, Integer nbSpectateurs, String nomLieu,
                                     List<RubriqueDTO> rubriques) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.nbSpectateurs = nbSpectateurs;
        this.nomLieu = nomLieu;
        this.rubriques = rubriques;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public LocalDate getDate() { return date; }
    public BigDecimal getHeureDebut() { return heureDebut; }
    public BigDecimal getDuree() { return duree; }
    public Integer getNbSpectateurs() { return nbSpectateurs; }
    public String getNomLieu() { return nomLieu; }
    public List<RubriqueDTO> getRubriques() { return rubriques; }
}
