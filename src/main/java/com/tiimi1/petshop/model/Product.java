package com.tiimi1.petshop.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private Long productId;

    @NotBlank(message = "A name is needed")
    @Column(name = "name")
    private String name;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "size")
    private String size;
    
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "10000.00")
    @NotNull(message = "message")
    @Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "instock")
    private int inStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producttypeid")
    private ProductType productType;
    
    @NotNull(message = "A manufacturer is needed")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturerid")
    private Manufacturer manufacturer;

    @ManyToMany(mappedBy = "products")
    private Set<Reservation> reservations = new HashSet<>();

    public Product() {

    }

    public Product(String name, String color, String size, BigDecimal price, int inStock, 
            ProductType productType, Manufacturer manufacturer) {
        super();
        this.name = name;
        this.color = color;
        this.size = size;
        this.price = price;
        this.inStock = inStock;
        this.productType = productType;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    

}