package com.tiimi1.petshop.web;

public class ReservationJson {
    private final Long productId;
    private final int count;

    public ReservationJson(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    


}
