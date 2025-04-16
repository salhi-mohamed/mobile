package com.enicarthage.Spectacles.spectacle.Controller;

import com.enicarthage.Spectacles.Lieu.Model.Lieu;
import com.enicarthage.Spectacles.spectacle.Model.Spectacle;
import com.enicarthage.Spectacles.spectacle.Model.SpectacleDTO;
import com.enicarthage.Spectacles.spectacle.Repository.SpectacleRepository;
import com.enicarthage.Spectacles.Lieu.Repository.LieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spectacles")
public class SpectacleController {

    @Autowired
    private SpectacleRepository spectacleRepository;

    @Autowired
    private LieuRepository lieuRepository;

    // ➕ Ajouter un spectacle
    @PostMapping
    public ResponseEntity<?> ajouterSpectacle(@RequestBody Spectacle spectacle) {
        // Vérifie que le lieu existe avant d'ajouter
        Optional<Lieu> lieuOpt = lieuRepository.findById(spectacle.getIdLieu().getId());
        if (lieuOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Lieu avec ID " + spectacle.getIdLieu().getId() + " introuvable.");
        }
        spectacle.setIdLieu(lieuOpt.get());
        Spectacle saved = spectacleRepository.save(spectacle);
        return ResponseEntity.ok(saved);
    }

    // 📃 Récupérer tous les spectacles
    // 📝 Récupérer tous les spectacles avec le nom du lieu inclus
    @GetMapping
    public ResponseEntity<List<SpectacleDTO>> getAllSpectacles() {
        List<Spectacle> spectacles = spectacleRepository.findAll();

        List<SpectacleDTO> spectacleDTOs = spectacles.stream()
                .map(s -> new SpectacleDTO(
                        s.getId(),
                        s.getTitre(),
                        s.getDate(),
                        s.getHeureDebut(),
                        s.getDuree(),
                        s.getNbSpectateurs(),
                        s.getNomLieu() // Seul le nom du lieu
                ))
                .toList();

        return ResponseEntity.ok(spectacleDTOs);
    }



    // 🔍 Récupérer un spectacle par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSpectacleById(@PathVariable Long id) {
        Optional<Spectacle> spectacle = spectacleRepository.findById(id);
        return spectacle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 📝 Modifier un spectacle
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierSpectacle(@PathVariable Long id, @RequestBody Spectacle spectacleDetails) {
        Optional<Spectacle> spectacleOpt = spectacleRepository.findById(id);
        if (spectacleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Spectacle spectacle = spectacleOpt.get();
        spectacle.setTitre(spectacleDetails.getTitre());
        spectacle.setDate(spectacleDetails.getDate());
        spectacle.setHeureDebut(spectacleDetails.getHeureDebut());
        spectacle.setDuree(spectacleDetails.getDuree());
        spectacle.setNbSpectateurs(spectacleDetails.getNbSpectateurs());

        // Vérifie que le nouveau lieu existe
        Long newLieuId = spectacleDetails.getIdLieu().getId();
        Optional<Lieu> lieuOpt = lieuRepository.findById(newLieuId);
        if (lieuOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Lieu avec ID " + newLieuId + " introuvable.");
        }

        spectacle.setIdLieu(lieuOpt.get());

        Spectacle updated = spectacleRepository.save(spectacle);
        return ResponseEntity.ok(updated);
    }

    // ❌ Supprimer un spectacle
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerSpectacle(@PathVariable Long id) {
        Optional<Spectacle> spectacle = spectacleRepository.findById(id);
        if (spectacle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        spectacleRepository.deleteById(id);
        return ResponseEntity.ok().body("Spectacle supprimé avec succès.");
    }
    // 🏙️ Récupérer le nom du lieu à partir de son ID
    @GetMapping("/lieu/{idLieu}")
    public ResponseEntity<?> getLieuNomById(@PathVariable Long idLieu) {
        Optional<Lieu> lieuOpt = lieuRepository.findById(idLieu);

        if (lieuOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Lieu introuvable
        }

        // Récupérer le nom du lieu
        Lieu lieu = lieuOpt.get();
        return ResponseEntity.ok(lieu.getNom()); // Retourner le nom du lieu
    }

}
