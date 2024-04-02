package com.tiimi1.petshop.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByProductName(String productName);
}
