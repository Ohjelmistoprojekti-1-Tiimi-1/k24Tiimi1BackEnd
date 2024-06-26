package com.tiimi1.petshop.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByManufacturerName(String name);
}
