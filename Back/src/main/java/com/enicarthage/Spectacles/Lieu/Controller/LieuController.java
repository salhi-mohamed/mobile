package com.enicarthage.Spectacles.Lieu.Controller;

import com.enicarthage.Spectacles.Lieu.Model.Lieu;
import com.enicarthage.Spectacles.Lieu.Service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lieux")
public class LieuController {

    @Autowired
    private LieuService lieuService;

    @GetMapping
    public List<Lieu> getAll() {
        return lieuService.getAllLieux();
    }

    @GetMapping("/{id}")
    public Lieu getById(@PathVariable Long id) {
        return lieuService.getLieuById(id);
    }

    @GetMapping("/search")
    public List<Lieu> search(@RequestParam String nom) {
        return lieuService.searchByNom(nom);
    }

    @PostMapping
    public Lieu save(@RequestBody Lieu lieu) {
        return lieuService.saveLieu(lieu);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lieuService.deleteLieu(id);
    }
}
