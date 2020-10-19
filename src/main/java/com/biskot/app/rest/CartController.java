package com.biskot.app.rest;

import com.biskot.app.rest.request.AddItemRequest;
import com.biskot.domain.model.Cart;
import com.biskot.domain.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "carts")
    public ResponseEntity createCart() {
        cartService.createCart();
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(value = "carts" + "/{cartId}" ,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> retrieveCart(@PathVariable long cartId) {
        Cart cart = cartService.getCart(cartId);
        return new ResponseEntity(cart, HttpStatus.OK);
    }


    @PutMapping(value = "carts" + "/{cartId}/items" ,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity addOrUpdateItem(@PathVariable long cartId, @RequestBody AddItemRequest addItemRequest) {
        cartService.addItemToCart(cartId, addItemRequest.getProductId(), addItemRequest.getQuantity());
        return new ResponseEntity(HttpStatus.OK);
    }


}
