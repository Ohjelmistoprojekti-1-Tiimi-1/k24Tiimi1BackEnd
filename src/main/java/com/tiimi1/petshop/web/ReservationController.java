package com.tiimi1.petshop.web;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.tiimi1.petshop.model.ReservationRepository;
import com.tiimi1.petshop.model.ProductRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.Reservation;
import com.tiimi1.petshop.model.ReservationProduct;


@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;

    public ReservationController(ReservationRepository reservationRepository, ProductRepository productRepository) {
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/admin/reservations")
    public String showReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findByOrderByReservationIdAsc());
        return "reservations";
    }

    @GetMapping("/admin/reservationdelivered/{id}")
    public String deliverReservation(@PathVariable("id") Long reservationId) {
        Objects.requireNonNull(reservationId);
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (!reservationOptional.isPresent()) {
            return "redirect:/admin/reservations";
        }
        Reservation reservation = reservationOptional.get();
        reservation.setDelivered(new Date(System.currentTimeMillis()));
        reservationRepository.save(reservation);
        return "redirect:/admin/reservations";
    }

    @GetMapping("/admin/reservationcancelled/{id}")
    public String cancelReservation(@PathVariable("id") Long reservationId) {
        Objects.requireNonNull(reservationId);
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (!reservationOptional.isPresent()) {
            return "redirect:/admin/reservations";
        }
        Reservation reservation = reservationOptional.get();
        if (reservation.getCancelled() == null) {
            reservation.setCancelled(new Date(System.currentTimeMillis()));
            List<ReservationProduct>  products = reservation.getReservationProducts();
            
            for (ReservationProduct reservationproduct : products) 
            {
                Product product = reservationproduct.getProduct();
    
                product.setInStock(product.getInStock() - reservationproduct.getCount());
                productRepository.save(product);
            }
           
            reservationRepository.save(reservation);
        }
        return "redirect:/admin/reservations";
    }
}
