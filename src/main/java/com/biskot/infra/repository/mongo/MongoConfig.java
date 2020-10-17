package com.biskot.infra.repository.mongo;


import com.mongodb.client.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@EnableMongoRepositories(basePackages = "com.biskot.infra.repository")
public class MongoConfig {


    @PostConstruct
    public void mongoTemplate() throws Exception {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp("localhost");
        /*MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "abcd");
        return mongoTemplate; */
    }

    /*
    @Bean(destroyMethod = "close")
    public Mongo mongoEmbbeded() throws IOException {
        return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp("127.0.0.1")
                .port(12345)
                .build();
    } */

    /*
    MongodExecutable mongodExecutable = null;

    @PostConstruct
    public void startMongo() throws Exception{
        MongodStarter starter = MongodStarter.getDefaultInstance();
        String bindIp = "localhost";
        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();

        mongodExecutable = starter.prepare(mongodConfig);
        MongodProcess mongod = mongodExecutable.start();

        MongoClient mongo = new MongoClient(bindIp, port);
        DB db = mongo.getDB("test");
        DBCollection col = db.createCollection("carts", new BasicDBObject());
        //col.save(new BasicDBObject("testDoc", new Date()));
    }

    @PreDestroy
    public void destroy() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }

    } */


}