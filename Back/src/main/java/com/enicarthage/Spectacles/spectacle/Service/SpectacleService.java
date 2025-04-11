package com.enicarthage.Spectacles.spectacle.Service;

import com.enicarthage.Spectacles.spectacle.Model.Spectacle;
import com.enicarthage.Spectacles.spectacle.Repository.SpectacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpectacleService {

    @Autowired
    private SpectacleRepository spectacleRepository;

    public List<Spectacle> getAllSpectacles() {
        return spectacleRepository.findAll();
    }

    public Spectacle getSpectacleById(Long id) {
        return spectacleRepository.findById(id).orElse(null);
    }

    public List<Spectacle> searchByTitre(String titre) {
        return spectacleRepository.findByTitreContainingIgnoreCase(titre);
    }

    public Spectacle saveSpectacle(Spectacle s) {
        return spectacleRepository.save(s);
    }

    public void deleteSpectacle(Long id) {
        spectacleRepository.deleteById(id);
    }
}

