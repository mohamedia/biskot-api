package com.biskot.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private int id;
    private String label;
    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("quantity_in_stock")
    private int quantityInStock;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
