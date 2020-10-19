package com.biskot.domain.service;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Item;
import com.biskot.domain.model.Product;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.domain.spi.ProductPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartPersistencePort cartPersistencePort;
    private ProductPort productPort;
    private CartRuleChecker carteRuleChecker;

    long cartIdIncrement = 0;

    @Autowired
    public CartServiceImpl(CartPersistencePort cartPersistencePort, ProductPort productPort,
                           CartRuleChecker carteRuleChecker) {
        this.cartPersistencePort = cartPersistencePort;
        this.productPort = productPort;
        this.carteRuleChecker = carteRuleChecker;
    }

    @Override
    public void createCart() {
        long cartId = generateNewCartId();
        Cart cart = new Cart(cartId);
        cartPersistencePort.saveCart(cart);

    }

    @Override
    public Cart getCart(long cartId) {
        return cartPersistencePort.getCart(cartId).orElse(null);
    }

    @Override
    public void addItemToCart(long cartId, long productId, int quantityToAdd) {
        Cart cart = cartPersistencePort.getCart(cartId)
                .orElseThrow(() -> new IllegalStateException("cart not exist, please create it first"));

        Product product = retrieveProduct(productId);

       if (checkRequestValidity(quantityToAdd, cart, product)) return;

        addItemToCart(cart, product, quantityToAdd);
        cartPersistencePort.saveCart(cart);
    }

    private void addItemToCart(Cart cart, Product product, int quantityToAdd) {
        Optional<Item> itemOptional = cart.getItems().stream()
                .filter(i -> product.getId() == i.getProductId()).findFirst();
        Item item;
        if (itemOptional.isEmpty()) {
            item = buildItem(product);
            cart.getItems().add(item);
        } else {
            item = itemOptional.get();
        }
        item.setQuantity(item.getQuantity() + quantityToAdd);
    }

    private Product retrieveProduct(long productId) {
        Product product = productPort.getProduct(productId);

        if (product == null) {
            throw new IllegalStateException("product not exist");
        }
        return product;
    }


    private long generateNewCartId() {
        cartIdIncrement++;
        return cartIdIncrement;
    }


    private boolean checkRequestValidity(int quantityToAdd, Cart cart, Product product) {
        return carteRuleChecker.cartContainsMoreThan3DifferentProduct(cart, product.getId())
                || carteRuleChecker.addedQuantityExceedProductStock(quantityToAdd, product)
                || carteRuleChecker.totalCartPriceExceedMaximum(cart, quantityToAdd, product.getUnitPrice());
    }



    private Item buildItem(Product product) {
        Item item = new Item();
        item.setProductId(product.getId());
        item.setProductLabel(product.getLabel());
        item.setUnitPrice(product.getUnitPrice());
        return item;
    }


}
