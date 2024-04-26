package com.tiimi1.petshop.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "manufacturerid")
    private Long manufacturerId;
    private String name;
    private String country;

    @Column (name = "businessidentitycode")
    private String businessIdentityCode;
    
    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products;

    public Manufacturer() {
    }

    public Manufacturer(String name, String country, String businessIdentityCode ) {
        super();
        this.name = name;
        this.country = country;
        this.businessIdentityCode = businessIdentityCode;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessIdentityCode() {
        return businessIdentityCode;
    }

    public void setBusinessIdentityCode(String businessIdentityCode) {
        this.businessIdentityCode = businessIdentityCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}