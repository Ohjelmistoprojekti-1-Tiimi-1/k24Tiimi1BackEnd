package com.tiimi1.petshop.web;

import org.springframework.web.bind.annotation.RestController;

import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;
import com.tiimi1.petshop.model.Reservation;
import com.tiimi1.petshop.model.ReservationProduct;
import com.tiimi1.petshop.model.ReservationProductRepository;
import com.tiimi1.petshop.model.ReservationRepository;
import com.tiimi1.petshop.service.JwtService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
public class ReservationRestController {
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ReservationProductRepository reservationProductRepository;
    private final JwtService jwtService;

    public ReservationRestController(ReservationRepository reservationRepository, ProductRepository productRepository, CustomerRepository customerRepository, JwtService jwtService, ReservationProductRepository reservationProductRepository) {
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.reservationProductRepository = reservationProductRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("logget/newreservation")
    public ResponseEntity<?> newReservation(@RequestHeader("Authorization") String bearerToken, @RequestBody ReservationJson[] reservationJson) {
        String username = jwtService.getUser(bearerToken);
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
        if(optionalCustomer.isPresent()) {

            Reservation reservation = new Reservation(optionalCustomer.get());

            List<ReservationProduct> reservationProducts = new ArrayList<>();

            // create new ReservationProduct, add it to the list that will be added to the reservation, save it to the reservationProductRepository
            Arrays.asList(reservationJson).forEach(r -> {
                ReservationProduct reservationProduct = new ReservationProduct(r.getCount(), productRepository.findById(r.getProductId()).get(), reservation );
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

            return ResponseEntity.ok().body("New Reservation saved successfully!");
        } else {
            return ResponseEntity.badRequest().body("Customer not found.");
        }
        
    }
    



}
