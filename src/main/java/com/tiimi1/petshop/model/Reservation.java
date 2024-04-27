package com.tiimi1.petshop.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationId")
    private Long reservationId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created")
    private Date created;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "delivered")
    private Date delivered;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "cancelled")
    private Date cancelled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    Customer customer;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation")
    private List<ReservationProduct> reservationProducts;

    
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

    public List<ReservationProduct> getReservationProducts() {
        return reservationProducts;
    }

    public void setReservationProducts(List<ReservationProduct> reservationProducts) {
        this.reservationProducts = reservationProducts;
    }



}
