package com.bhn.shoppingcart.service;

import com.bhn.shoppingcart.model.dto.OrderDto;
import com.bhn.shoppingcart.model.entity.Cart;
import com.bhn.shoppingcart.model.entity.Item;
import com.bhn.shoppingcart.model.entity.Order;
import com.bhn.shoppingcart.model.entity.Product;
import com.bhn.shoppingcart.repository.CartRepository;
import com.bhn.shoppingcart.repository.ItemRepository;
import com.bhn.shoppingcart.repository.OrderRepository;
import com.bhn.shoppingcart.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)

public class OrderTypeServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;



    /*
    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public ProductService productService() {
            return new ProductService(productRepository);
        }
    }

        */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        orderService.productService = productService;
    }

    @Test
    public void givenOrder_whenOrderRetrieved_thenContainsOrderId() {
        final Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        final OrderDto orderDto = orderService.findById(1L);

        assertEquals(1, orderDto.getId().longValue());
    }

    @Test
    public void givenOrder_whenOrderCreated_thenContainsItemsAndTotalPrice() {
        final Order order = new Order();
        order.setId(1L);

        final Product product = new Product();
        product.setId(2L);
        product.setPrice(BigDecimal.TEN);

        final Cart cart = new Cart();
        cart.setId(1L);

        final Item item = new Item(order, product, 3);

        order.getItems().add(item);

        when(orderRepository.saveAndFlush(any())).thenReturn(order);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));

        when(itemRepository.saveAndFlush(any())).thenReturn(item);

        when(cartRepository.saveAndFlush(any())).thenReturn(cart);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));


        final OrderDto orderDto = orderService.createOrder( product.getId(), 3);

        assertEquals(1, orderDto.getId().longValue());
        assertEquals(1, orderDto.getItems().size());
        assertEquals(30, orderDto.getTotalOrderPrice().intValue());
        final OrderDto orderDto2 = orderService.createOrder(product.getId(), 3);

    }

    @Test
    public void givenOrderItem_whenOrderItemCreated_thenContainsTotalOrderPriceAndTotalOfItems() {
        final Order order = new Order();
        order.setId(1L);

        final Product productA = new Product();
        productA.setId(2L);
        productA.setPrice(BigDecimal.TEN);

        final Product productB = new Product();
        productB.setId(7L);
        productB.setPrice(new BigDecimal(15));

        final Item itemA = new Item(order, productA, 3);
        final Item itemB = new Item(order, productB, 5);

        order.getItems().add(itemA);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepository.findById(2L)).thenReturn(Optional.of(productA));
        when(productRepository.findById(7L)).thenReturn(Optional.of(productB));
        when(itemRepository.saveAndFlush(itemA)).thenReturn(itemA);
        when(itemRepository.saveAndFlush(itemB)).thenReturn(itemB);

        final OrderDto orderDto = orderService.addOrderItem(1L, 7L, 5);

        assertEquals(8, orderDto.getNumberOfProducts().intValue());
        assertEquals(105, orderDto.getTotalOrderPrice().intValue());
    }

    @Test
    public void givenOrderItem_whenOrderItemUpdated_thenContainsTotalOrderPriceAndTotalOfItems() {
        final Order order = new Order();
        order.setId(1L);

        final Product productA = new Product();
        productA.setId(2L);
        productA.setPrice(BigDecimal.TEN);

        final Product productB = new Product();
        productB.setId(7L);
        productB.setPrice(new BigDecimal(35));

        final Item itemA = new Item(order, productA, 3);
        final Item itemB = new Item(order, productB, 4);

        order.getItems().add(itemA);
        order.getItems().add(itemB);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepository.findById(2L)).thenReturn(Optional.of(productA));
        when(productRepository.findById(7L)).thenReturn(Optional.of(productB));
        when(itemRepository.saveAndFlush(itemA)).thenReturn(itemA);
        when(itemRepository.saveAndFlush(itemB)).thenReturn(itemB);

        final OrderDto orderDto = orderService.addOrderItem(1L, 2L, 5);

        assertEquals(12, orderDto.getNumberOfProducts().intValue());
        assertEquals(220, orderDto.getTotalOrderPrice().intValue());
    }

    @Test
    public void givenOrderItem_whenOrderItemDeleted_thenContainsTotalOrderPriceAndTotalOfItems() {
        final Order order = new Order();
        order.setId(1L);

        final Product productA = new Product();
        productA.setId(2L);
        productA.setPrice(BigDecimal.TEN);

        final Product productB = new Product();
        productB.setId(4L);
        productB.setPrice(new BigDecimal(23));

        final Item itemA = new Item(order, productA, 3);
        final Item itemB = new Item(order, productB, 4);

        order.getItems().add(itemA);
        order.getItems().add(itemB);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepository.findById(2L)).thenReturn(Optional.of(productA));
        when(productRepository.findById(4L)).thenReturn(Optional.of(productB));
        when(itemRepository.saveAndFlush(itemA)).thenReturn(itemA);
        when(itemRepository.saveAndFlush(itemB)).thenReturn(itemB);

        final OrderDto orderDto = orderService.removeOrderItem(1L, 2L);

        assertEquals(4, orderDto.getNumberOfProducts().intValue());
        assertEquals(92, orderDto.getTotalOrderPrice().intValue());
    }
}