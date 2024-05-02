package com.tiimi1.petshop.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationid")
    private Long reservationId;

    @Column(name = "created")
    private Date created;

    @Column(name = "delivered")
    private Date delivered;

    @Column(name = "canceled")
    private Date cancelled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid")
    Customer customer;

    @JsonIgnore
    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name="reservation_product",joinColumns = 
        {
        @JoinColumn(name="reservationid") },
        inverseJoinColumns = 
        {
        @JoinColumn(name="productid") })
    private Set<Product> products = new HashSet<>();


    public Reservation() {
    }

    public Reservation(Customer customer) {
        super();
        this.created = new Date(System.currentTimeMillis());
        this.customer = customer;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDelivered() {
        return delivered;
    }

    public void setDelivered(Date delivered) {
        this.delivered = delivered;
    }

    public Date getCancelled() {
        return cancelled;
    }

    public void setCancelled(Date cancelled) {
        this.cancelled = cancelled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.getReservations().add(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.getReservations().remove(this);
    }

    public Set<Product> getProducts() {
        return products;
    }



}
