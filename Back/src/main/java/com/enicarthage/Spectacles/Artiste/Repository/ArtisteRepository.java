package com.enicarthage.Spectacles.Artiste.Repository;

import com.enicarthage.Spectacles.Artiste.Model.Artiste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtisteRepository extends JpaRepository<Artiste, Long> {
}
