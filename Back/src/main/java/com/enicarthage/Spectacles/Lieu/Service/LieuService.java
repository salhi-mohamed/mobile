package com.enicarthage.Spectacles.Lieu.Service;

import com.enicarthage.Spectacles.Lieu.Model.Lieu;
import com.enicarthage.Spectacles.Lieu.Repository.LieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LieuService {

    @Autowired
    private LieuRepository lieuRepository;

    public List<Lieu> getAllLieux() {
        return lieuRepository.findAll();
    }

    public Lieu getLieuById(Long id) {
        return lieuRepository.findById(id).orElse(null);
    }

    public List<Lieu> searchByNom(String nom) {
        return lieuRepository.findByNomContainingIgnoreCase(nom);
    }


    public Lieu saveLieu(Lieu lieu) {
        if (lieu.getVille() == null || lieu.getVille().isEmpty()) {
            throw new IllegalArgumentException("La ville ne peut pas être vide");
        }
        return lieuRepository.save(lieu);
    }
    public void deleteLieu(Long id) {
        lieuRepository.deleteById(id);
    }
}

