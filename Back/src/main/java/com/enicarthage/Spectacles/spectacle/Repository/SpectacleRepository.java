package com.enicarthage.Spectacles.spectacle.Repository;

import com.enicarthage.Spectacles.spectacle.Model.Spectacle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpectacleRepository extends JpaRepository<Spectacle, Long> {
    List<Spectacle> findByTitreContainingIgnoreCase(String titre);
    @Query("SELECT s FROM Spectacle s " +
            "JOIN s.lieu l " +
            "WHERE (:titre IS NULL OR s.titre LIKE %:titre%) " +
            "AND (:date IS NULL OR s.date = :date) " +
            "AND (:heureDebut IS NULL OR s.heureDebut = :heureDebut) " +
            "AND (:nomLieu IS NULL OR l.nom LIKE %:nomLieu%) " +
            "AND (:ville IS NULL OR l.ville LIKE %:ville%)")
    List<Spectacle> searchSpectacles(String titre, LocalDate date, BigDecimal heureDebut, String nomLieu, String ville);
    @Query("SELECT s FROM Spectacle s JOIN s.lieu l " +
            "WHERE LOWER(s.titre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(l.nom) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(l.ville) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Spectacle> searchGlobal(String query);

}

