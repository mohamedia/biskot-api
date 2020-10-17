package com.biskot.app.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AddItemRequest implements Serializable {

    @JsonProperty("product_id")
    private long productId;

    private int quantity;

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
