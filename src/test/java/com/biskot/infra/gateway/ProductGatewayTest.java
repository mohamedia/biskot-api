package com.biskot.infra.gateway;


import com.biskot.domain.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ProductGatewayTest {

    private String productGatewayUrl = "http://localhost:9001/products/";

    @InjectMocks
    private ProductGateway productGateway;

    @Mock
    private RestTemplate restTemplate;


    @Test
    public void should_retrieve_product() {

        //Given
        int productId = 1;
        Product product = new Product();
        product.setId(productId);
        product.setLabel("label");
        Mockito.when(restTemplate.getForObject(productGatewayUrl + productId, Product.class)).thenReturn(product);


        //When
        Product productResult = productGateway.getProduct(productId);

        //Then
        Assertions.assertThat(productResult).isEqualTo(product);
        Mockito.verify(restTemplate).getForObject(productGatewayUrl + productId, Product.class);

    }
}