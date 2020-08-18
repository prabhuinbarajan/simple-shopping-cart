package com.bhn.shoppingcart.controller;

import com.bhn.shoppingcart.repository.ItemRepository;
import com.bhn.shoppingcart.repository.OrderRepository;
import com.bhn.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public abstract class AbstractIntegrationTest {

    private static final String CONTEXT = "/cart/v1";

    @Autowired
    protected WebTestClient client;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected ItemRepository itemRepository;

    protected WebTestClient.RequestHeadersSpec<?> get(String uri, Object... uriVariables) {
        return client.get().uri(CONTEXT + uri, uriVariables);
    }

    protected WebTestClient.RequestBodySpec post(String uri, Object... uriVariables) {
        return client.post().uri(CONTEXT + uri, uriVariables);
    }

    protected WebTestClient.RequestBodySpec put(String uri, Object... uriVariables) {
        return client.put().uri(CONTEXT + uri, uriVariables);
    }

    protected WebTestClient.RequestHeadersSpec<?> delete(String uri, Object... uriVariables) {
        return client.delete().uri(CONTEXT + uri, uriVariables);
    }
}
