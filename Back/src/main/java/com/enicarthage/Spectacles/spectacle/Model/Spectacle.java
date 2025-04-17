package com.enicarthage.Spectacles.spectacle.Model;

import com.enicarthage.Spectacles.Lieu.Model.Lieu;
import com.enicarthage.Spectacles.Rubrique.Models.Rubrique; // 👉 Import de Rubrique
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "SPECTACLE")
public class Spectacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSPEC")
    private Long id;

    @Column(name = "TITRE", nullable = false)
    private String titre;

    @Column(name = "DATES")
    private LocalDate date;

    @Column(name = "H_DEBUT", nullable = false)
    private BigDecimal heureDebut;

    @Column(name = "DUREES", nullable = false)
    private BigDecimal duree;

    @Column(name = "NBRSPECTATEUR", nullable = false)
    private Integer nbSpectateurs;

    @ManyToOne
    @JoinColumn(name = "idlieu", nullable = false)
    private Lieu lieu;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL)
    private List<Rubrique> rubriques;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lieu getIdLieu() {
        return lieu;
    }

    public void setIdLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public Integer getNbSpectateurs() {
        return nbSpectateurs;
    }

    public void setNbSpectateurs(Integer nbSpectateurs) {
        this.nbSpectateurs = nbSpectateurs;
    }

    public BigDecimal getDuree() {
        return duree;
    }

    public void setDuree(BigDecimal duree) {
        this.duree = duree;
    }

    public BigDecimal getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(BigDecimal heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Transient
    public String getNomLieu() {
        if (lieu != null) {
            return lieu.getNom();
        } else {
            return "Lieu non spécifié";
        }
    }

    public List<Rubrique> getRubriques() {
        return rubriques;
    }

    public void setRubriques(List<Rubrique> rubriques) {
        this.rubriques = rubriques;
    }
}
