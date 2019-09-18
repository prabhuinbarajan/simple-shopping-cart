package com.example.shoppingcart.service;

import com.example.shoppingcart.core.BusinessException;
import com.example.shoppingcart.model.dto.ItemDto;
import com.example.shoppingcart.model.dto.OrderDto;
import com.example.shoppingcart.model.dto.ProductDto;
import com.example.shoppingcart.model.entity.Item;
import com.example.shoppingcart.model.entity.Order;
import com.example.shoppingcart.model.entity.Product;
import com.example.shoppingcart.repository.ItemRepository;
import com.example.shoppingcart.repository.OrderRepository;
import com.example.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Long orderId) {
        return orderRepository
                .findById(orderId)
                .map(this::convertToOrderDto)
                .orElseThrow(BusinessException::notFound);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderDto createOrder(Long productId, Integer quantity) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(BusinessException::notFound);

        final Order order = orderRepository.saveAndFlush(new Order(LocalDateTime.now()));
        final Item item = createItem(order, product, quantity);
        order.getItems().add(item);

        return convertToOrderDto(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderDto addOrderItem(Long orderId, Long productId, Integer quantity) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(BusinessException::notFound);

        return orderRepository
                .findById(orderId)
                .map(order -> updateOrder(order, product, quantity))
                .orElseThrow(BusinessException::notFound);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderDto removeOrderItem(Long orderId, Long productId) {
        final Order order = orderRepository
                .findById(orderId)
                .orElseThrow(BusinessException::notFound);

        order.getItems()
                .removeIf(item -> {
                    if (item.getPk().getProduct().getId().equals(productId)) {
                        itemRepository.delete(item);
                        return true;
                    }
                    return false;
                });

        return convertToOrderDto(order);
    }

    private OrderDto updateOrder(Order order, Product product, Integer quantity) {
        order.getItems()
                .stream()
                .filter(item -> item.getPk().getProduct().equals(product))
                .findFirst()
                .map(item -> updateItem(item, quantity))
                .orElseGet(() -> {
                    final Item item = createItem(order, product, quantity);
                    order.getItems().add(item);
                    return item;
                });

        return convertToOrderDto(order);
    }

    private Item updateItem(Item item, Integer quantity) {
        item.setQuantity(item.getQuantity() + quantity);
        return itemRepository.saveAndFlush(item);
    }

    private Item createItem(Order order, Product product, Integer quantity) {
        return itemRepository.saveAndFlush(new Item(order, product, quantity));
    }

    private OrderDto convertToOrderDto(Order order) {
        final Set<ItemDto> itemDtos = order.getItems()
                .stream()
                .map(this::convertToItemDto)
                .collect(Collectors.toSet());

        return new OrderDto(order.getId(), order.getCreationTime(), itemDtos);
    }

    private ItemDto convertToItemDto(Item item) {
        return new ItemDto(convertToProductDto(item.getPk().getProduct()), item.getQuantity());
    }

    private ProductDto convertToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
