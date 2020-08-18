package com.bhn.shoppingcart.controller;

import com.bhn.shoppingcart.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ProductRepository productRepository;


    @Test
    public void givenGetAllProducts_whenProductListRetrieved_thenSizeMatchAndListContainsProductNames() {
        get("/products?page={0}&size={1}", 0, 3)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$.[0].name").isEqualTo("HAPPY HOLIDAYS SWAP $50.00")
                .jsonPath("$.[1].name").isEqualTo("Visa Meta eGift - $50.00");
    }

    @Test
    public void givenGetProductById_whenProductRetrieved_thenContainsProductNameAndPrice() {
        get("/products/{productId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("HAPPY HOLIDAYS SWAP $50.00")
                .jsonPath("$.price").isEqualTo(55.00);
    }
}
