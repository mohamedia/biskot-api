package com.biskot.domain.service;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Item;
import com.biskot.domain.model.Product;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.domain.spi.ProductPort;
import com.biskot.infra.gateway.ProductGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartPersistencePort cartPersistencePort;
    private ProductPort productPort;

    long cartIdIncrement = 1;

    @Autowired
    public CartServiceImpl(CartPersistencePort cartPersistencePort, ProductPort productPort) {
        this.cartPersistencePort = cartPersistencePort;
        this.productPort = productPort;
    }

    @Override
    public void createCart() {
        Cart cart = new Cart(cartIdIncrement);
        cartPersistencePort.saveCart(cart);
        cartIdIncrement++;
    }

    @Override
    public Cart getCart(long cartId) {
        return cartPersistencePort.getCart(cartId).orElse(null);
    }

    @Override
    public void addItemToCart(long cartId, long productId, int quantityToAdd) {
        Cart cart = cartPersistencePort.getCart(cartId)
                .orElseThrow(() -> new IllegalStateException("cart not exist, please create it first"));

        Optional<Item> itemOptional = cart.getItems().stream()
                .filter(i -> productId == i.getProductId()).findFirst();

        Product product = retrieveProduct(productId);

        Item item;
        if (itemOptional.isEmpty()) {
            item = buildItem(product);
            cart.getItems().add(item);
        } else {
            item = itemOptional.get();
        }

        updateQuantity(item, quantityToAdd, product);

        cartPersistencePort.saveCart(cart);
    }

    private void updateQuantity(Item item, int quantityToAdd, Product product) {
        item.setQuantity(item.getQuantity() + quantityToAdd);
        if (item.getQuantity() > product.getQuantityInStock()) {
            item.setQuantity(product.getQuantityInStock());
        }
    }

    private Item buildItem(Product product) {
        Item item = new Item();
        item.setProductId(product.getId());
        item.setProductLabel(product.getLabel());
        item.setUnitPrice(product.getUnitPrice());
        return item;
    }


    private Product retrieveProduct(long productId) {
        Product product = productPort.getProduct(productId);

        if (product == null) {
            throw new IllegalStateException("product not exist");
        }
        return product;
    }


}
