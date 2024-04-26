package com.tiimi1.petshop.model;

import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservationId")
    private Long reservationId;

    // kaikki päivämäärät lisätään myöhemmin
    @Column(name = "fakeDate")
    private String fakeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    Customer customer;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation")
    private List<ReservationProduct> reservationProducts;

    public Reservation() {
    }

    public Reservation(String fakeDate, Customer customer) {
        this.fakeDate = fakeDate;
        this.customer = customer;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getFakeDate() {
        return fakeDate;
    }

    public void setFakeDate(String fakeDate) {
        this.fakeDate = fakeDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
