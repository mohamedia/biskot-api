package com.biskot.infra.repository.mongo;

import com.biskot.domain.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartsMongoRepository extends MongoRepository<Cart, String> {
    Cart findById(long id);
}
