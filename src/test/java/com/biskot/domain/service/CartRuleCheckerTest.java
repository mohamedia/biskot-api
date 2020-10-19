package com.biskot.domain.service;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Item;
import com.biskot.domain.model.Product;
import de.flapdoodle.embed.process.collections.Collections;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartRuleCheckerTest {

    private CartRuleChecker carteRuleChecker = new CartRuleChecker();



    @Test
    public void should_be_true_when_product_to_add_not_exceed_3DifferentProduct() {
        //Given
        Cart cart = new Cart();
        List<Item> items = Collections.newArrayList(
                new Item(1),
                new Item(2),
                new Item(3));
        int newProductIdToAdd = 4;
        cart.setItems(items);

        //When
        boolean result = carteRuleChecker.cartContainsMoreThan3DifferentProduct(cart, newProductIdToAdd);

        //Then
        assertThat(result).isTrue();
    }


    @Test
    public void should_be_false_when_product_to_add_not_exceed_3DifferentProduct() {
        //Given
        Cart cart = new Cart();
        List<Item> items = Collections.newArrayList(
                new Item(1),
                new Item(2));
        int newProductIdToAdd = 3;
        cart.setItems(items);

        //When
        boolean result = carteRuleChecker.cartContainsMoreThan3DifferentProduct(cart, newProductIdToAdd);

        //Then
        assertThat(result).isFalse();
    }


    @Test
    public void should_be_false_when_product_to_add_already_exists() {
        //Given
        Cart cart = new Cart();
        List<Item> items = Collections.newArrayList(
                new Item(1),
                new Item(2),
                new Item(3));
        int newProductIdToAdd = 3;
        cart.setItems(items);

        //When
        boolean result = carteRuleChecker.cartContainsMoreThan3DifferentProduct(cart, newProductIdToAdd);

        //Then
        assertThat(result).isFalse();
    }


    @Test
    public void should_true_when_addedQuantity_exceed_product_in_stock() {
        //Given
        int addedQuantity = 200;
        Product product = new Product();
        product.setQuantityInStock(150);

        //When
        boolean result = carteRuleChecker.addedQuantityExceedProductStock(addedQuantity, product);

        //Then
        assertThat(result).isTrue();
    }

    @Test
    public void should_false_when_addedQuantity_not_exceed_product_in_stock() {
        //Given
        int addedQuantity = 100;
        Product product = new Product();
        product.setQuantityInStock(150);

        //When
        boolean result = carteRuleChecker.addedQuantityExceedProductStock(addedQuantity, product);

        //Then
        assertThat(result).isFalse();
    }


    @Test
    public void should_true_when_total_cart_price_excedd_maximum() {
        //Given
        Cart cart = new Cart();
        List<Item> items = Collections.newArrayList(
                new Item(1, 2, 10.00),
                new Item(2, 1, 50.00));
        cart.setItems(items);

        int quantityToAdd = 3;
        Double unitPrice = 30.00;

        //When
        boolean result = carteRuleChecker.totalCartPriceExceedMaximum(cart, quantityToAdd, unitPrice);

        //Then
        assertThat(result).isTrue();
    }

    @Test
    public void should_false_when_total_cart_price_not_excedd_maximum() {
        //Given
        Cart cart = new Cart();
        List<Item> items = Collections.newArrayList(
                new Item(1, 2, 10.00),
                new Item(2, 1, 50.00));
        cart.setItems(items);

        int quantityToAdd = 2;
        Double unitPrice = 10.00;

        //When
        boolean result = carteRuleChecker.totalCartPriceExceedMaximum(cart, quantityToAdd, unitPrice);

        //Then
        assertThat(result).isFalse();
    }


}