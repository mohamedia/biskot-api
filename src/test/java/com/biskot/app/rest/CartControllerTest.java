package com.biskot.app.rest;

import com.biskot.app.rest.request.AddItemRequest;
import com.biskot.domain.model.Cart;
import com.biskot.domain.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Test
    public void should_create_cart() {
        //When
        ResponseEntity responseEntity = cartController.createCart();


        //Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(cartService).createCart();
    }


    @Test
    public void should_retrieve_cart() {
        //Given
        long cartId = 3;
        Cart cart = new Cart();
        Mockito.when(cartService.getCart(cartId)).thenReturn(cart);
        //When
        ResponseEntity responseEntity = cartController.retrieveCart(cartId);


        //Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(cart);

        Mockito.verify(cartService).getCart(3);

    }

    @Test
    public void should_add_item_to_cart() {
        //Given
        long cartId = 3;
        long productId = 1;
        int quantityToAdd = 5;
        AddItemRequest addItemRequest = new AddItemRequest(productId, quantityToAdd);
        //When
        ResponseEntity responseEntity = cartController.addOrUpdateItem(cartId, addItemRequest);

        //Then
        Mockito.verify(cartService).addItemToCart(cartId, productId, quantityToAdd);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}