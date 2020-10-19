package com.biskot.infra.repository.mongo;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


@Component
@EnableMongoRepositories(basePackageClasses = CartsMongoRepository.class)
public class MongoConfig {


    @PostConstruct
    public void startEmbeddedMongoDB() {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp("localhost");
    }


}