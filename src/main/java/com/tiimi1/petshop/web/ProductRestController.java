package com.tiimi1.petshop.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;
import com.tiimi1.petshop.model.Reservation;
import com.tiimi1.petshop.service.JwtService;

@RestController
public class ProductRestController {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final ProductRepository productRepository;

    public ProductRestController(CustomerRepository customerRepository, JwtService jwtService, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.productRepository = productRepository;
    }

    @GetMapping("/logget/reservations")
    public ResponseEntity<?> getReservationsByCustomer(@RequestHeader("Authorization") String bearerToken) {
        String customerUsername = jwtService.getUser(bearerToken);
        Customer customer = customerRepository.findByUsername(customerUsername).get();
        List<Reservation> reservations = customer.getReservations();
        reservations.forEach(c -> c.setCustomer(null)); // probably not necessary and customer informatin might be good?
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("all/productwithinfo")
    public Iterable<Product> getProductsWithAllInfo() {
        return productRepository.findAll();
    }

}