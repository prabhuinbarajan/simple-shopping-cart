package com.example.shoppingcart.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void givenGetAllProducts_whenProductListRetrieved_thenSizeMatchAndListContainsProductNames() {
        get("/products?page={0}&size={1}", 0, 3)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$.[0].name").isEqualTo("Otterbox Etui")
                .jsonPath("$.[1].name").isEqualTo("Tablette Android RCT6K03W13");
    }

    @Test
    public void givenGetProductById_whenProductRetrieved_thenContainsProductNameAndPrice() {
        get("/products/{productId}", 7)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("APPIE Smart Watch Fitnes")
                .jsonPath("$.price").isEqualTo(35.69);
    }
}
