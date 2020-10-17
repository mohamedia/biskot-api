package com.biskot.infra.gateway;

import com.biskot.domain.model.Product;
import com.biskot.domain.spi.ProductPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductGateway implements ProductPort {

    private RestTemplate restTemplate;

    private String productGatewayUrl = "http://localhost:9001/products/";

    @Autowired
    public ProductGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProduct(long productId) {
        Product product = restTemplate.getForObject(productGatewayUrl + productId, Product.class);

        return product;
    }

}
