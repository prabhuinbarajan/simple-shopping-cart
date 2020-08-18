package com.bhn.shoppingcart.controller;

import com.bhn.shoppingcart.model.dto.OrderDto;
import com.bhn.shoppingcart.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get order  by id")
    public OrderDto findById(@PathVariable Long orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create order")
    public OrderDto createOrder(
            @NonNull @RequestParam Long productId,
            @NonNull @RequestParam Integer quantity) {
        return orderService.createOrder(productId, quantity);
    }

    @PostMapping(path = "/{orderId}/items")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "add line item to order")
    public OrderDto addOrderItem(
            @NonNull @PathVariable Long orderId,
            @NonNull @RequestParam Long productId,
            @NonNull @RequestParam Integer quantity) {
        return orderService.addOrderItem(orderId, productId, quantity);
    }

    @DeleteMapping(path = "/{orderId}/items")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "remove order line item")
    public OrderDto removeOrderItem(
            @NonNull @PathVariable Long orderId,
            @NonNull @RequestParam Long productId) {
        return orderService.removeOrderItem(orderId, productId);
    }
}
