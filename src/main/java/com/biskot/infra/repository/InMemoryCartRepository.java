package com.biskot.infra.repository;

import com.biskot.domain.model.Cart;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.infra.repository.mongo.CartsMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryCartRepository implements CartPersistencePort {


    private CartsMongoRepository cartsRepository;

    @Autowired
    public InMemoryCartRepository(CartsMongoRepository cartsRepository) {
        this.cartsRepository = cartsRepository;
    }

    @Override
    public Optional<Cart> getCart(long id) {
        Cart cart = cartsRepository.findById(id);
        return Optional.ofNullable(cart);
    }

    @Override
    public void saveCart(Cart cart) {
        cartsRepository.save(cart);
    }
}
