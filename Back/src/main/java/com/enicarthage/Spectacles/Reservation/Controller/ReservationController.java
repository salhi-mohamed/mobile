package com.enicarthage.Spectacles.Reservation.Controller;

import com.enicarthage.Spectacles.Reservation.Models.Reservation;
import com.enicarthage.Spectacles.Reservation.Service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> reserver(
            @RequestParam Long spectacleId,
            @RequestParam Long clientId,
            @RequestParam String categorie) {
        try {
            Reservation reservation = reservationService.reserverBillet(spectacleId, clientId, categorie);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
