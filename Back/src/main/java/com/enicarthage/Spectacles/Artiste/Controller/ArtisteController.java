package com.enicarthage.Spectacles.Artiste.Controller;

import com.enicarthage.Spectacles.Artiste.Model.Artiste;
import com.enicarthage.Spectacles.Artiste.Repository.ArtisteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artistes")
public class ArtisteController {

    @Autowired
    private ArtisteRepository artisteRepository;

    // ➕ Ajouter un artiste
    @PostMapping
    public ResponseEntity<?> ajouterArtiste(@RequestBody Artiste artiste) {
        Artiste savedArtiste = artisteRepository.save(artiste);
        return ResponseEntity.ok(savedArtiste);
    }

    // 📃 Récupérer tous les artistes
    @GetMapping
    public ResponseEntity<List<Artiste>> getAllArtistes() {
        List<Artiste> artistes = artisteRepository.findAll();
        return ResponseEntity.ok(artistes);
    }

    // 🔍 Récupérer un artiste par son ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getArtisteById(@PathVariable Long id) {
        Optional<Artiste> artiste = artisteRepository.findById(id);
        return artiste.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 📝 Modifier un artiste
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierArtiste(@PathVariable Long id, @RequestBody Artiste artisteDetails) {
        Optional<Artiste> artisteOpt = artisteRepository.findById(id);
        if (artisteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Artiste artiste = artisteOpt.get();
        artiste.setNom(artisteDetails.getNom());
        artiste.setPrenom(artisteDetails.getPrenom());
        artiste.setSpecialite(artisteDetails.getSpecialite());

        Artiste updatedArtiste = artisteRepository.save(artiste);
        return ResponseEntity.ok(updatedArtiste);
    }

    // ❌ Supprimer un artiste
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerArtiste(@PathVariable Long id) {
        Optional<Artiste> artiste = artisteRepository.findById(id);
        if (artiste.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        artisteRepository.deleteById(id);
        return ResponseEntity.ok().body("Artiste supprimé avec succès.");
    }
}
