package com.tiimi1.petshop.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(exported = false)
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional <Customer> findByUsername(String username);
    Boolean existsByUsername(String username);
}
