package com.biskot.domain.service;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Product;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.domain.spi.ProductPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartPersistencePort cartPersistencePort;
    @Mock
    private ProductPort productPort;
    @Mock
    private CartRuleChecker carteRuleChecker;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void should_create_cart() {
        //When
        cartService.createCart();

        //Then
        Mockito.verify(cartPersistencePort).saveCart(ArgumentMatchers.any(Cart.class));
    }

    @Test
    public void should_get_cart() {
        //Given
        Optional<Cart> cart = Optional.of(new Cart());
        Mockito.when(cartPersistencePort.getCart(1)).thenReturn(cart);
        //When
        Cart cartResult = cartService.getCart(1);

        //Then
        assertThat(cartResult).isEqualTo(cart.get());
        Mockito.verify(cartPersistencePort).getCart(1);

    }

    @Test
    public void should_not_get_cart() {
        //When
        Cart cartResult = cartService.getCart(1);

        //Then
        assertThat(cartResult).isNull();
        Mockito.verify(cartPersistencePort).getCart(1);

    }



    @Test
    public void should_add_item_to_cart() {
        //When
        long cartId = 1;
        int productId = 1;
        int quantityToAdd = 5;

        Product product = buildProduct(productId, 5, 10.00, "Label");

        Optional<Cart> cartOptional = Optional.of(new Cart());
        Mockito.when(cartPersistencePort.getCart(cartId)).thenReturn(cartOptional);
        Mockito.when(productPort.getProduct(productId)).thenReturn(product);
        cartService.addItemToCart(cartId, productId, quantityToAdd);

        //Then
        Cart cart = cartOptional.get();
        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getProductId()).isEqualTo(1);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(5);
        assertThat(cart.getItems().get(0).getLinePrice()).isEqualTo(50.00);
        assertThat(cart.getItems().get(0).getProductLabel()).isEqualTo("Label");
        assertThat(cart.getTotalPrice()).isEqualTo(50.00);
        Mockito.verify(cartPersistencePort).saveCart(cartOptional.get());
    }

    @Test
    public void should_not_add_item_to_cart_when_exceed_3_items() {
        //When
        long cartId = 1;
        int productId = 1;
        int quantityToAdd = 5;

        Product product = buildProduct(productId, 5, 10.00, "Label");

        Optional<Cart> cartOptional = Optional.of(new Cart());
        Mockito.when(cartPersistencePort.getCart(cartId)).thenReturn(cartOptional);
        Mockito.when(productPort.getProduct(productId)).thenReturn(product);
        Mockito.when(carteRuleChecker.cartContainsMoreThan3DifferentProduct(cartOptional.get(), productId))
                .thenReturn(true);
        //When
        cartService.addItemToCart(cartId, productId, quantityToAdd);

        //Then
        Cart cart = cartOptional.get();
        assertThat(cart.getItems()).isEmpty();
        Mockito.verify(cartPersistencePort, Mockito.never()).saveCart(cartOptional.get());
    }

    @Test
    public void should_not_add_item_to_cart_when_exceed_product_stock() {
        //When
        long cartId = 1;
        int productId = 1;
        int quantityToAdd = 5;

        Product product = buildProduct(productId, 5, 10.00, "Label");

        Optional<Cart> cartOptional = Optional.of(new Cart());
        Mockito.when(cartPersistencePort.getCart(cartId)).thenReturn(cartOptional);
        Mockito.when(productPort.getProduct(productId)).thenReturn(product);
        Mockito.when(carteRuleChecker.addedQuantityExceedProductStock(quantityToAdd, product))
                .thenReturn(true);
        //When
        cartService.addItemToCart(cartId, productId, quantityToAdd);

        //Then
        Cart cart = cartOptional.get();
        assertThat(cart.getItems()).isEmpty();
        Mockito.verify(cartPersistencePort, Mockito.never()).saveCart(cartOptional.get());
    }


    @Test
    public void should_not_add_item_to_cart_when_exceed_total_cart_amount() {
        //When
        long cartId = 1;
        int productId = 1;
        int quantityToAdd = 5;

        Product product = buildProduct(productId, 5, 10.00, "Label");

        Optional<Cart> cartOptional = Optional.of(new Cart());
        Mockito.when(cartPersistencePort.getCart(cartId)).thenReturn(cartOptional);
        Mockito.when(productPort.getProduct(productId)).thenReturn(product);
        Mockito.when(carteRuleChecker.totalCartPriceExceedMaximum(cartOptional.get(), quantityToAdd,
                product.getUnitPrice()))
                .thenReturn(true);
        //When
        cartService.addItemToCart(cartId, productId, quantityToAdd);

        //Then
        Cart cart = cartOptional.get();
        assertThat(cart.getItems()).isEmpty();
        Mockito.verify(cartPersistencePort, Mockito.never()).saveCart(cartOptional.get());
    }

    private Product buildProduct(int productId, int quantityStock, Double unitPrice, String label) {
        Product product = new Product();
        product.setId(productId);
        product.setQuantityInStock(quantityStock);
        product.setLabel(label);
        product.setUnitPrice(unitPrice);
        return product;
    }


}