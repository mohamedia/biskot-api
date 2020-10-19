package com.biskot.domain.service;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Item;
import com.biskot.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartRuleChecker {

    private static int MAXIMUM_TOTAL_CART_AMOUNT = 100;
    private static int MAXIMUM_PRODUCT_ALLOWED = 3;

    public boolean cartContainsMoreThan3DifferentProduct(Cart cart, int productId) {
        List<Integer> listProductsId = cart.getItems()
                .stream()
                .map(Item::getProductId)
                .collect(Collectors.toList());

        return !(listProductsId.size() < MAXIMUM_PRODUCT_ALLOWED ? true : listProductsId.contains(productId));
    }

    public boolean totalCartPriceExceedMaximum(Cart cart, int quantityToAdd, Double unitPrice) {
        return (quantityToAdd * unitPrice) + cart.getTotalPrice() > MAXIMUM_TOTAL_CART_AMOUNT;
    }

    public boolean addedQuantityExceedProductStock(int addedQuantity, Product product) {
        return addedQuantity >  product.getQuantityInStock();
    }

}
