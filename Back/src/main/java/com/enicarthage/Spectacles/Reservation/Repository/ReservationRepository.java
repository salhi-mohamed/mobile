package com.enicarthage.Spectacles.Reservation.Repository;
import com.enicarthage.Spectacles.Reservation.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

