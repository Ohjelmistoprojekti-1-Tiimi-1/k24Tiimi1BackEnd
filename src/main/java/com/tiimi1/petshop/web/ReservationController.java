package com.tiimi1.petshop.web;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.tiimi1.petshop.model.ManufacturerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ReservationRepository;
import com.tiimi1.petshop.model.ProductTypeRepository;
import com.tiimi1.petshop.model.Reservation;

import jakarta.validation.Valid;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/admin/reservations")
    public String showReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
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
