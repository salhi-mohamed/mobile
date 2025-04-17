package com.enicarthage.Spectacles.Reservation.Models;

import com.enicarthage.Spectacles.Billet.Model.Billet;
import com.enicarthage.Spectacles.User.Model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDBILLET", nullable = false)
    private Billet billet;

    @ManyToOne
    @JoinColumn(name = "IDCLT", nullable = false)
    private User client;

    @Column(name = "DATE_RESERVATION", nullable = false)
    private LocalDateTime dateReservation;

    public Billet getBillet() {
        return billet;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

