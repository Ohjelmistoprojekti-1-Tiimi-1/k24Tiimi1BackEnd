package com.tiimi1.petshop.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;


@Controller
public class LoginController {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public LoginController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @GetMapping({ "/", "/login" })
    public String login() {
        return "login";
    }

    @GetMapping("/admin/home")
    public String home() {
        return "home";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> singUpCustomer (@RequestBody Customer signUpCustomer) {
        if (customerRepository.existsByUsername(signUpCustomer.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        Customer newCustomer = new Customer(signUpCustomer.getUsername(), encoder.encode(signUpCustomer.getPassword()), "USER");
        customerRepository.save(newCustomer);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
