package com.example.shoppingcart.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void step01_givenPostOrder_whenOrderCreated_thenContainsStatusCodeCreated() {
        post("/orders?productId={0}&quantity={1}", 3, 4)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void step02_givenGetOrderById_whenOrderRetrieved_thenContainsProductNameAndPriceAndQuantityAndTotalPrice() {
        get("/orders/{orderId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items[0].product.name").isEqualTo("Grimpeur et toboggan Hide & Seek de Little Tikes")
                .jsonPath("$.items[0].product.price").isEqualTo(159.2)
                .jsonPath("$.items[0].quantity").isEqualTo(4)
                .jsonPath("$.items[0].totalPrice").isEqualTo(636.8);
    }

    @Test
    public void step03_givenPostOrderItem_whenOrderUpdated_thenContainsTotalOrderPriceAndTotalOfItems() {
        post("/orders/{orderId}/items?productId={1}&quantity={2}", 1, 7, 2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(2)
                .jsonPath("$.totalOrderPrice").isEqualTo(708.18)
                .jsonPath("$.numberOfProducts").isEqualTo(6);
    }

    @Test
    public void step04_givenDeleteOrderItem_whenItemDeleted_thenContainsStatusCodeSuccess() {
        delete("/orders/{orderId}/items?productId={1}", 1, 3)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.totalOrderPrice").isEqualTo(71.38)
                .jsonPath("$.numberOfProducts").isEqualTo(2);
    }

    @Test
    public void step05_givenPostOrderItem_whenItemUpdated_thenContainsTotalOrderPriceAndTotalOfItems() {
        post("/orders/{orderId}/items?productId={1}&quantity={2}", 1, 7, 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.totalOrderPrice").isEqualTo(107.07)
                .jsonPath("$.numberOfProducts").isEqualTo(3);
    }
}
