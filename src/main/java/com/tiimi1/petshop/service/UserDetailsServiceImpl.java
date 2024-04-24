package com.tiimi1.petshop.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> user = customerRepository.findByUsername(username);
        UserBuilder builder = null;
        if (user.isPresent()) {
            Customer customer = user.get();
            builder = User.withUsername(username); 
            builder.password(customer.getPassword());
            builder.roles(customer.getRole());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

}

// Mukaillen teoksesta
// Full Stack Development with Spring Boot 3 and React
// Juha Hinkula
// Tämä saattaa olla tekijänoikeussuojattua aineistoa.
