package com.bhn.shoppingcart.controller;

import com.bhn.shoppingcart.model.dto.CartDto;
import com.bhn.shoppingcart.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final CartService cartService;

    @Autowired
    public UserController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/{userId}/carts")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get cart  by user id")
    public CartDto findCartsByUserId(@PathVariable String userId) {
        return cartService.findByUserId(userId);
    }


    @GetMapping(path = "/{userId}/session/{sessionId}/carts")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get cart by user id and  session id")
    public CartDto findCartsByUserId(@PathVariable String userId,@PathVariable String sessionId) {
        return cartService.findBySessionIdAndUserId(sessionId, userId);
    }

}
