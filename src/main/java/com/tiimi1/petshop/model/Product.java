package com.tiimi1.petshop.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank(message = "A name is needed")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productTypeId")
    private ProductType productType;
    private String color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizeId")
    private Size size;
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "10000.00")
    @NotNull(message = "message")
    private BigDecimal price;
    @NotNull(message = "A manufacturer is needed")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturerId")
    private Manufacturer manufacturer;

    public Product() {

    }

    public Product(String name, ProductType productType, String color, Size size, BigDecimal price,
            Manufacturer manufacturer) {
        this.name = name;
        this.productType = productType;
        this.color = color;
        this.size = size;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", productType=" + productType + ", color="
                + color + ", size=" + size + ", price=" + price + ", manufacturer=" + manufacturer + "]";
    }

}