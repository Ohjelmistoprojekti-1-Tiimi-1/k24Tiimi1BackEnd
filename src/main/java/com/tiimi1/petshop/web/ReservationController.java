package com.tiimi1.petshop.web;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.tiimi1.petshop.model.ReservationRepository;
import com.tiimi1.petshop.model.Reservation;


@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/admin/reservations")
    public String showReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findByOrderByReservationIdAsc());
        return "reservations";
    }

    @GetMapping("/admin/reservationdelivered/{id}")
    public String deliverReservation(@PathVariable("id") Long reservationId) {
        Objects.requireNonNull(reservationId);
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.setDelivered(new Date(System.currentTimeMillis()));
        reservationRepository.save(reservation);
        return "redirect:/admin/reservations";
    }

    @GetMapping("/admin/reservationcancelled/{id}")
    public String cancelReservation(@PathVariable("id") Long reservationId) {
        Objects.requireNonNull(reservationId);
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.setCancelled(new Date(System.currentTimeMillis()));
        reservationRepository.save(reservation);
        return "redirect:/admin/reservations";
    }
}
