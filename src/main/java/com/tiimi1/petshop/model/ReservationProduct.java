package com.tiimi1.petshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservationProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservationId")
    private Long reservationProductId;

    @Column(name = "count")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation")
    Reservation reservation;

    public ReservationProduct() {
    }

    public ReservationProduct(int count, Product product, Reservation reservation) {
        super();
        this.count = count;
        this.product = product;
        this.reservation = reservation;
    }

    public Long getReservationProductId() {
        return reservationProductId;
    }

    public void setReservationProductId(Long reservationProductId) {
        this.reservationProductId = reservationProductId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


}
