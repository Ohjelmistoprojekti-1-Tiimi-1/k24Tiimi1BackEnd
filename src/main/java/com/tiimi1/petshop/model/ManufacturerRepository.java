package com.tiimi1.petshop.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Long> {
    List<Manufacturer> findByManufacturerName(String manufacturerName);
}
