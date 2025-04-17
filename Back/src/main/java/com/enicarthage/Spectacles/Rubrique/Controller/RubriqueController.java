package com.enicarthage.Spectacles.Rubrique.Controller;

import com.enicarthage.Spectacles.Artiste.Repository.ArtisteRepository;
import com.enicarthage.Spectacles.Rubrique.Models.Rubrique;
import com.enicarthage.Spectacles.Rubrique.Repository.RubriqueRepository;
import com.enicarthage.Spectacles.spectacle.Repository.SpectacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/rubriques")
public class RubriqueController {

    @Autowired
    private RubriqueRepository rubriqueRepository;

    @Autowired
    private SpectacleRepository spectacleRepository;

    @Autowired
    private ArtisteRepository artisteRepository;

    // Ajouter une rubrique à un spectacle
    @PostMapping
    public ResponseEntity<?> ajouterRubrique(@RequestBody Rubrique rubrique) {
        if (rubrique.getSpectacle() == null || rubrique.getSpectacle().getId() == null) {
            return ResponseEntity.badRequest().body("ID du spectacle requis.");
        }

        if (rubrique.getArtiste() == null || rubrique.getArtiste().getId() == null) {
            return ResponseEntity.badRequest().body("ID de l'artiste requis.");
        }

        Optional<?> spectacle = spectacleRepository.findById(rubrique.getSpectacle().getId());
        Optional<?> artiste = artisteRepository.findById(rubrique.getArtiste().getId());

        if (spectacle.isEmpty()) return ResponseEntity.badRequest().body("Spectacle introuvable.");
        if (artiste.isEmpty()) return ResponseEntity.badRequest().body("Artiste introuvable.");

        rubrique.setSpectacle((com.enicarthage.Spectacles.spectacle.Model.Spectacle) spectacle.get());
        rubrique.setArtiste((com.enicarthage.Spectacles.Artiste.Model.Artiste) artiste.get());

        Rubrique saved = rubriqueRepository.save(rubrique);
        return ResponseEntity.ok(saved);
    }

    // Supprimer une rubrique
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerRubrique(@PathVariable Long id) {
        Optional<Rubrique> rubrique = rubriqueRepository.findById(id);
        if (rubrique.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        rubriqueRepository.deleteById(id);
        return ResponseEntity.ok("Rubrique supprimée avec succès.");
    }
}
