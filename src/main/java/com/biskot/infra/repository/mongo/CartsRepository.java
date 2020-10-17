package com.biskot.infra.repository.mongo;

import com.biskot.domain.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartsRepository extends MongoRepository<Cart, String> {


    public Cart findById(long id);

}
