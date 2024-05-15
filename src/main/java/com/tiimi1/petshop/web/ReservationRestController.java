package com.tiimi1.petshop.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;
import com.tiimi1.petshop.model.Reservation;
import com.tiimi1.petshop.model.ReservationProduct;
import com.tiimi1.petshop.model.ReservationProductRepository;
import com.tiimi1.petshop.model.ReservationRepository;
import com.tiimi1.petshop.service.JwtService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ReservationRestController {
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ReservationProductRepository reservationProductRepository;
    private final JwtService jwtService;

    public ReservationRestController(ReservationRepository reservationRepository, ProductRepository productRepository,
            CustomerRepository customerRepository, JwtService jwtService,
            ReservationProductRepository reservationProductRepository) {
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.reservationProductRepository = reservationProductRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/logget/reservations")
    public ResponseEntity<?> getReservationsByCustomer(@RequestHeader("Authorization") String bearerToken) {
        String customerUsername = jwtService.getUser(bearerToken);
        Customer customer = customerRepository.findByUsername(customerUsername).get();
        List<Reservation> reservations = customer.getReservations();
        reservations.forEach(c -> c.setCustomer(null)); // probably not necessary
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/logget/reservationproducts")
    public ResponseEntity<?> getReservationProducts(@RequestHeader("Authorization") String bearerToken, @RequestParam Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        // Check if resrevation is customer's own reservation
        String username = jwtService.getUser(bearerToken);
        if (!username.equals(reservation.getCustomer().getUsername())) {
            return new ResponseEntity<String>("Not allowed", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(reservation.getReservationProducts());
    }

    @PostMapping("logget/newreservation")
    public ResponseEntity<?> newReservation(@RequestHeader("Authorization") String bearerToken, @RequestBody ReservationJson[] reservationJson) {
        String username = jwtService.getUser(bearerToken);
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
        if (optionalCustomer.isPresent()) {
            Reservation reservation = new Reservation(optionalCustomer.get());
            List<ReservationProduct> reservationProducts = new ArrayList<>();
            // create new ReservationProduct and add it to the list that will be added to
            // the reservation
            Arrays.asList(reservationJson).forEach(r -> {
                ReservationProduct reservationProduct = new ReservationProduct(r.getCount(),
                        productRepository.findById(r.getProductId()).get(), reservation);
                reservationProducts.add(reservationProduct);
            });
            // update products inStock to mach reservation count
            reservationProducts.forEach(r -> {
                Product product = productRepository.findById(r.getProduct().getProductId()).get();
                product.setInStock(product.getInStock() - r.getCount());
                productRepository.save(product);
            });
            reservation.setReservationProducts(reservationProducts);
            reservationRepository.save(reservation);
            reservationProductRepository.saveAll(reservationProducts);
            return ResponseEntity.ok(new MessageResponse("New reservatin added succesfully"));
        } else {
            return ResponseEntity.badRequest().body("Customer not found.");
        }
    }

    @PostMapping("logget/cancelreservation")
    public ResponseEntity<?> cancelReservation(@RequestHeader("Authorization") String bearerToken,
            @RequestParam Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            // Check if resrevation is customer's own reservation
            String username = jwtService.getUser(bearerToken);
            if (!username.equals(reservation.getCustomer().getUsername())) {
                return new ResponseEntity<String>("Not allowed", HttpStatus.FORBIDDEN);
            }
            List<ReservationProduct> reservationProducts = reservation.getReservationProducts();
            reservationProducts.forEach(r -> {
                Product product = productRepository.findById(r.getProduct().getProductId()).get();
                product.setInStock(product.getInStock() + r.getCount());
                productRepository.save(product);
            });
            reservation.setCancelled(new Date(System.currentTimeMillis()));
            reservationRepository.save(reservation);
            return ResponseEntity.ok(new MessageResponse("Reservation cancelled"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Reservation not found"));
    }

}
