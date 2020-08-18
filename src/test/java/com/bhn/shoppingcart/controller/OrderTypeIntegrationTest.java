package com.bhn.shoppingcart.controller;

import com.bhn.shoppingcart.model.dto.CartDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
public class OrderTypeIntegrationTest extends AbstractIntegrationTest {
    static ObjectMapper objectMapper = new ObjectMapper();

    static boolean initialized = false;
    @PostConstruct
    public void init() throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        if (initialized) {
            return;
        }
        initialized = true;
        MockitoAnnotations.initMocks(this);
        WebTestClient.ResponseSpec responseSpec = post("/carts?sessionId={0}&userId={1}", 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        responseSpec.expectStatus().isCreated();
        String cartStr = responseSpec.expectBody(String.class).returnResult().getResponseBody();

               // .expectStatus().isCreated();
        CartDto cart =
                objectMapper.readValue(cartStr.getBytes(), CartDto.class);

        System.out.println(cart.getId());
    }

   /* @Test
    public void step01_givenPostOrder_whenOrderCreated_thenContainsStatusCodeCreated() {
        post("/orders?productId={0}&quantity={1}&cartId={2}", 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
    }

    */

    @Test
    public void step01_givenPostOrderItem_whenOrderUpdated_thenContainsTotalOrderPriceAndTotalOfItems() {
        post("/orders/{orderId}/items?productId={1}&quantity={2}", 1, 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.total_order_price").isEqualTo(55.00)
                .jsonPath("$.number_of_products").isEqualTo(1);
    }


    @Test
    public void step02_givenGetOrderById_whenOrderRetrieved_thenContainsProductNameAndPriceAndQuantityAndTotalPrice() {
        get("/orders/{orderId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items[0].product.name").isEqualTo("HAPPY HOLIDAYS SWAP $50.00")
                .jsonPath("$.items[0].product.price").isEqualTo(55.00)
                .jsonPath("$.items[0].quantity").isEqualTo(1)
                .jsonPath("$.items[0].total_price").isEqualTo(55.00);
    }

    @Test
    public void step03_givenPostOrderItem_whenOrderUpdated_thenContainsTotalOrderPriceAndTotalOfItems() {
        post("/orders/{orderId}/items?productId={1}&quantity={2}", 1, 2, 2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(2)
                .jsonPath("$.total_order_price").isEqualTo(166.9)
                .jsonPath("$.number_of_products").isEqualTo(3);
    }

    @Test
    public void step04_givenDeleteOrderItem_whenItemDeleted_thenContainsStatusCodeSuccess() {
        delete("/orders/{orderId}/items?productId={1}", 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.total_order_price").isEqualTo(111.90)
                .jsonPath("$.number_of_products").isEqualTo(2);
    }

    @Test
    public void step05_givenPostOrderItem_whenItemUpdated_thenContainsTotalOrderPriceAndTotalOfItems() {
        post("/orders/{orderId}/items?productId={1}&quantity={2}", 1, 1, 2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(2)
                .jsonPath("$.total_order_price").isEqualTo(221.9)
                .jsonPath("$.number_of_products").isEqualTo(4);
    }
}
