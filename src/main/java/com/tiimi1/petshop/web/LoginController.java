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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.security.core.Authentication;

import com.tiimi1.petshop.model.AccountCredentials;
import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;
import com.tiimi1.petshop.service.JwtService;

@Controller
public class LoginController {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(CustomerRepository customerRepository, AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // Thymeleaf
    @GetMapping({ "/", "/login" })
    public String login() {
        return "login";
    }

    // Thymeleaf
    @GetMapping("/admin/home")
    public String home() {
        return "home";
    }

    /* ****** Front end *********** */

    // Customer signup
    @PostMapping("/signup")
    public ResponseEntity<?> singUpCustomer(@RequestBody Customer signUpCustomer) {
        if (customerRepository.existsByUsername(signUpCustomer.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        Customer newCustomer = new Customer(signUpCustomer.getUsername(), encoder.encode(signUpCustomer.getPassword()),
                "USER");
        customerRepository.save(newCustomer);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    // Customer login
    @PostMapping("/customerlogin")
    public ResponseEntity<?> loginCustomer(@RequestBody AccountCredentials credentials) {
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(),
                credentials.password());
        Authentication auth = authenticationManager.authenticate(creds);
        // Generate token
        String jwts = jwtService.getToken(auth.getName());
        // Build response with the generated token
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer" + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .build();
    }

    // Delete customer
    @GetMapping("/customerdelete")
    public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String bearerToken) {
        String user = jwtService.getUser(bearerToken);
        Customer customer = customerRepository.findByUsername(user).get();

        try {
            customerRepository.deleteById(customer.getCustomerId());
        } catch (Exception error) {
        }
        return ResponseEntity.ok("Login credentials deleted successfully.");
    }

}
