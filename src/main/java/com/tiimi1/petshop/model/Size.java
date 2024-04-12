package com.tiimi1.petshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sizeId;
    private String sizeValue;
    @JsonIgnore
    @OneToMany(mappedBy = "size")
    private List<Product> products;

    public Size() {
    }

    public Size(String value) {
        this.sizeValue = value;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(String sizeValue) {
        this.sizeValue = sizeValue;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
