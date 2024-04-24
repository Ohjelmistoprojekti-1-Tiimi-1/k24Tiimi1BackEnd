package com.tiimi1.petshop.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tiimi1.petshop.model.AccountCredentials;
import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;


@Controller
public class LoginController {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final AuthenticationManager authenticationManager;

    public LoginController(CustomerRepository customerRepository, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
    }
    
    @GetMapping({ "/", "/login" })
    public String login() {
        return "login";
    }

    @GetMapping("/admin/home")
    public String home() {
        return "home";
    }

    // Customer signup
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

    // Customer login
    @PostMapping("/customerlogin")
    public ResponseEntity<?> loginCustomer(@RequestBody AccountCredentials credentials) {
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        Authentication auth = authenticationManager.authenticate(creds);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Authentication header")
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"Authorization")
                .build();
    }

}
