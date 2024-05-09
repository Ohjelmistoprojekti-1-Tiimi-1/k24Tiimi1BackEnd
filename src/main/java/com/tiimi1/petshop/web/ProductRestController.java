package com.tiimi1.petshop.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ProductRestController {
    private final ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("all/productwithinfo")
    public Iterable<Product> getProductsWithAllInfo() {
        return productRepository.findAll();
    }

    @GetMapping("all/productsbymanufacturer")
    public List<Product> getProductsByManufacturer(@RequestParam String manufacturerName) {
        List<Product> products = productRepository.findByManufacturerName(manufacturerName);
        return products;
    }
    

}