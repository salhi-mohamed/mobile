package com.enicarthage.Spectacles.Billet.Repository;

import com.enicarthage.Spectacles.Billet.Model.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {
    List<Billet> findBySpectacleId(Long spectacleId);
    Optional<Billet> findFirstBySpectacle_IdAndVenduFalse(Long spectacleId);
    List<Billet> findBySpectacle_IdAndVenduFalse(Long spectacleId);

    Optional<Billet> findFirstBySpectacle_IdAndCategorieAndVenduFalse(Long spectacleId, String categorie);
}
