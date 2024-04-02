package com.tiimi1.petshop.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long manufacturerId;
    private String manufacturerName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturer")
    private List<Product> products;

    public Manufacturer() {
    }

    public Manufacturer(String manufacturerName) {
        super();
        this.manufacturerName = manufacturerName;
    }

    public Long getManufacturerId() {
        return this.manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return this.manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "{" +
                " manufacturerId='" + getManufacturerId() + "'" +
                ", manufacturerName='" + getManufacturerName() + "'" +
                ", products='" + getProducts() + "'" +
                "}";
    }

}