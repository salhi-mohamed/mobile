package com.enicarthage.Spectacles.Reservation.Service;

import com.enicarthage.Spectacles.Billet.Model.Billet;
import com.enicarthage.Spectacles.Billet.Repository.BilletRepository;
import com.enicarthage.Spectacles.Reservation.Models.Reservation;
import com.enicarthage.Spectacles.Reservation.Repository.ReservationRepository;
import com.enicarthage.Spectacles.User.Model.User;
import com.enicarthage.Spectacles.User.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private final BilletRepository billetRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(BilletRepository billetRepository,
                              UserRepository userRepository,
                              ReservationRepository reservationRepository) {
        this.billetRepository = billetRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserverBillet(Long spectacleId, Long clientId, String categorie) throws Exception {
        // Vérifier l'existence du client
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new Exception("Client introuvable"));

        // Chercher un billet non vendu pour ce spectacle et la catégorie spécifiée
        Billet billetDispo = billetRepository
                .findFirstBySpectacle_IdAndCategorieAndVenduFalse(spectacleId, categorie)
                .orElseThrow(() -> new Exception("Aucun billet disponible pour ce spectacle et cette catégorie"));

        // Marquer le billet comme vendu
        billetDispo.setVendu(true);
        billetRepository.save(billetDispo);

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setBillet(billetDispo);
        reservation.setClient(client);
        reservation.setDateReservation(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }
}
