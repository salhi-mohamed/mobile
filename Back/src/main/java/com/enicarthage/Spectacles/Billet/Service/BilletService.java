package com.enicarthage.Spectacles.Billet.Service;

import com.enicarthage.Spectacles.Billet.Model.Billet;
import com.enicarthage.Spectacles.Billet.Repository.BilletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BilletService {

    @Autowired
    private BilletRepository billetRepository;

    public List<Billet> getAllBillets() {
        return billetRepository.findAll();
    }

    public Billet getBilletById(Long id) {
        return billetRepository.findById(id).orElse(null);
    }

    public List<Billet> getBilletsBySpectacleId(Long spectacleId) {
        return billetRepository.findBySpectacleId(spectacleId);
    }

    public Billet saveBillet(Billet billet) {
        return billetRepository.save(billet);
    }

    public void deleteBillet(Long id) {
        billetRepository.deleteById(id);
    }
}

