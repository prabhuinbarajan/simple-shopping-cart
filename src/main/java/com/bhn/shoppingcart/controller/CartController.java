package com.bhn.shoppingcart.controller;

import com.bhn.shoppingcart.model.dto.CartDto;
import com.bhn.shoppingcart.model.dto.OrderDto;
import com.bhn.shoppingcart.service.CartService;
import com.bhn.shoppingcart.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get cart  by id")
    public CartDto findById(@PathVariable Long cartId) {
        return cartService.findById(cartId);
    }


    @GetMapping(path = "/{cartId}/clearCart")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "clear the shopping cart")
    public CartDto clearCart(@PathVariable Long cartId) {
        return cartService.clearCart(cartId);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create cart")
    public CartDto createCart(
            @NonNull @RequestParam String sessionId,
            @NonNull @RequestParam String userId) {
        return cartService.createCart(sessionId, userId);
    }
}
