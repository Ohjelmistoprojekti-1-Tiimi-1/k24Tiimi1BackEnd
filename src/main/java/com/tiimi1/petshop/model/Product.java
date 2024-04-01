package com.tiimi1.petshop.model;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String productName;
    private String productType;
    private String productColor;
    private String productSize;
    private Double productPrice;

    @ManyToMany
    private Manufacturer manufacturer;


    public Product(String productName, String productType, String productColor, String productSize, Double productPrice, Manufacturer manufacturer) {
        super();
        this.productName = productName;
        this.productType = productType;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.manufacturer = manufacturer;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getproductColor() {
        return this.productColor;
    }

    public void setproductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getSize() {
        return this.productSize;
    }

    public void setSize(String productSize) {
        this.productSize = productSize;
    }

    public Double getproductPrice() {
        return this.productPrice;
    }

    public void setproductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productType='" + getProductType() + "'" +
            ", productColor='" + getproductColor() + "'" +
            ", productSize='" + getSize() + "'" +
            ", productPrice='" + getproductPrice() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            "}";
    }



}
