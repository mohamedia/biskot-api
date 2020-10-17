package com.biskot.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private long id;
    private List<Item> items = new ArrayList<>();
    private Double totalPrice;


    public Double getTotalPrice() {
        return items.stream().mapToDouble(Item::getLinePrice).sum();
    }

    public Cart(long id) {
        this.id = id;
    }

    public Cart() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
