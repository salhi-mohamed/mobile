package com.enicarthage.Spectacles.Lieu.Controller;

import com.enicarthage.Spectacles.Lieu.Model.Lieu;
import com.enicarthage.Spectacles.Lieu.Service.LieuService;
import com.enicarthage.Spectacles.spectacle.Model.SpectacleDTO;
import com.enicarthage.Spectacles.spectacle.Repository.SpectacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.enicarthage.Spectacles.spectacle.Model.Spectacle;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lieux")
public class LieuController {

    @Autowired
    private LieuService lieuService;
    private SpectacleRepository spectacleRepository;

    @GetMapping
    public List<SpectacleDTO> getAllSpectacles() {
        List<Spectacle> spectacles = spectacleRepository.findAll();

        return spectacles.stream()
                .map(s -> new SpectacleDTO(
                        s.getId(),
                        s.getTitre(),
                        s.getDate(),
                        s.getHeureDebut(),
                        s.getDuree(),
                        s.getNbSpectateurs(),
                        s.getNomLieu() // récupère le nom du lieu
                ))
                .toList();
    }


    @GetMapping("/{id}")
    public Lieu getById(@PathVariable Long id) {
        return lieuService.getLieuById(id);
    }

    @GetMapping("/search")
    public List<Lieu> search(@RequestParam String nom) {
        return lieuService.searchByNom(nom);
    }

    @PostMapping("/add")
    public Lieu save(@RequestBody Lieu lieu) {
        System.out.println("🔍 Reçu : " + lieu.getNom() + ", " + lieu.getAdresse());
        return lieuService.saveLieu(lieu);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lieuService.deleteLieu(id);
    }
}
