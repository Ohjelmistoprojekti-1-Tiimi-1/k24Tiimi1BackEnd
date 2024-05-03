package com.tiimi1.petshop.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByOrderByReservationIdAsc();
}
