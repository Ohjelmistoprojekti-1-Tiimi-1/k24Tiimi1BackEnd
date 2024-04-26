package com.tiimi1.petshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "productType")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productTypeId")
    private Long productTypeId;

    @Column(name = "productTypeValue")
    private String productTypeValue;

    @JsonIgnore
    @OneToMany(mappedBy = "productType")
    private List<Product> products;

    public ProductType() {
    }

    public ProductType(String productTypeValue) {
        super();
        this.productTypeValue = productTypeValue;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeValue() {
        return productTypeValue;
    }

    public void setProductTypeValue(String productTypeValue) {
        this.productTypeValue = productTypeValue;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
