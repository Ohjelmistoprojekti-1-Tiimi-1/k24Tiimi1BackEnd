package com.tiimi1.petshop.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;



@RestController
public class ManufacturerRestController {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerRestController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("all/manufacturerswithproducts")
    public Iterable<Manufacturer> getManufacturersWithProducts() {
        return manufacturerRepository.findAll();
    }
    

}