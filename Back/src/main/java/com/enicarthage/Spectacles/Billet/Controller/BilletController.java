package com.enicarthage.Spectacles.Billet.Controller;

import com.enicarthage.Spectacles.Billet.Model.Billet;
import com.enicarthage.Spectacles.Billet.Repository.BilletRepository;
import com.enicarthage.Spectacles.Billet.Service.BilletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/billets")
public class BilletController {

    @Autowired
    private BilletService billetService;
   @Autowired
   private BilletRepository billetRepository;
    @GetMapping
    public List<Billet> getAll() {
        return billetService.getAllBillets();
    }

    @GetMapping("/{id}")
    public Billet getById(@PathVariable Long id) {
        return billetService.getBilletById(id);
    }

    @GetMapping("/by-spectacle/{spectacleId}")
    public List<Billet> getBySpectacleId(@PathVariable Long spectacleId) {
        return billetService.getBilletsBySpectacleId(spectacleId);
    }
    @GetMapping("/disponibles/{spectacleId}")
    public List<Billet> getBilletsDisponiblesBySpectacleId(@PathVariable Long spectacleId) {
        // Appel direct à la méthode du repository pour récupérer les billets disponibles
        return billetRepository.findBySpectacle_IdAndVenduFalse(spectacleId);
    }
    @PostMapping
    public Billet save(@RequestBody Billet billet) {
        return billetService.saveBillet(billet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        billetService.deleteBillet(id);
    }
}
