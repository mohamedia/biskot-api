package com.biskot.domain.model;

import java.io.Serializable;

public class Item implements Serializable {
    // TODO: to be implemented

    private int productId;

    private String productLabel;

    private int quantity = 0;

    private Double unitPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getLinePrice() {
        return unitPrice * quantity;
    }




}
