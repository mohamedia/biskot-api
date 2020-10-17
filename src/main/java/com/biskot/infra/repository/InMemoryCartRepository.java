package com.biskot.infra.repository;

import com.biskot.domain.model.Cart;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.infra.repository.mongo.CartsRepository;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryCartRepository implements CartPersistencePort {

    @Autowired
    private CartsRepository cartsRepository;

    @Override
    public Optional<Cart> getCart(long id) {
        return Optional.of(cartsRepository.findById(id));
    }

    @Override
    public void saveCart(Cart cart) {
        cartsRepository.save(cart);
    }
}
