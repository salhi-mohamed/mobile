package com.enicarthage.Spectacles.spectacle.Controller;

import com.enicarthage.Spectacles.Lieu.Model.Lieu;
import com.enicarthage.Spectacles.Rubrique.Models.Rubrique;
import com.enicarthage.Spectacles.Rubrique.Models.RubriqueDTO;
import com.enicarthage.Spectacles.Rubrique.Repository.RubriqueRepository;
import com.enicarthage.Spectacles.spectacle.Model.Spectacle;
import com.enicarthage.Spectacles.spectacle.Model.SpectacleAvecRubriquesDTO;
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
@Autowired private RubriqueRepository rubriqueRepository;
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
    @PostMapping("/{id}/rubriques")
    public ResponseEntity<?> ajouterRubriqueAuSpectacle(@PathVariable Long id, @RequestBody Rubrique rubrique) {
        Optional<Spectacle> spectacleOpt = spectacleRepository.findById(id);
        if (spectacleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Spectacle spectacle = spectacleOpt.get();
        rubrique.setSpectacle(spectacle); // associer la rubrique au spectacle
        rubriqueRepository.save(rubrique);

        return ResponseEntity.ok("Rubrique ajoutée au spectacle ID " + id);
    }
    @GetMapping("/with-rubriques")
    public List<SpectacleAvecRubriquesDTO> getSpectaclesAvecRubriques() {
        List<Spectacle> spectacles = spectacleRepository.findAll();

        return spectacles.stream().map(spectacle -> {
            List<RubriqueDTO> rubriqueDTOs = spectacle.getRubriques().stream().map(rubrique ->
                    new RubriqueDTO(
                            rubrique.getId(),
                            rubrique.getHeureDebut(),
                            rubrique.getDuree(),
                            rubrique.getType(),
                            rubrique.getArtiste().getPrenom() + " " + rubrique.getArtiste().getNom()
                    )
            ).toList();

            return new SpectacleAvecRubriquesDTO(
                    spectacle.getId(),
                    spectacle.getTitre(),
                    spectacle.getDate(),
                    spectacle.getHeureDebut(),
                    spectacle.getDuree(),
                    spectacle.getNbSpectateurs(),
                    spectacle.getNomLieu(),
                    rubriqueDTOs
            );
        }).toList();
    }


    // ❌ Supprimer une rubrique par son ID (lié à un spectacle)
    @DeleteMapping("/{id}/rubriques/{rubriqueId}")
    public ResponseEntity<?> supprimerRubrique(@PathVariable Long id, @PathVariable Long rubriqueId) {
        Optional<Rubrique> rubriqueOpt = rubriqueRepository.findById(rubriqueId);
        if (rubriqueOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Rubrique rubrique = rubriqueOpt.get();
        if (!rubrique.getSpectacle().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Cette rubrique n'appartient pas au spectacle ID " + id);
        }

        rubriqueRepository.deleteById(rubriqueId);
        return ResponseEntity.ok("Rubrique supprimée avec succès.");
    }
}