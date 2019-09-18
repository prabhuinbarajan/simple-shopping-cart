package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.dto.ProductDto;
import com.example.shoppingcart.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Afficher tous les produits")
    public List<ProductDto> findAll(@RequestParam(value = "page", defaultValue = "0") int pageIndex,
                                    @RequestParam(value = "size", defaultValue = "5") int pageSize) {
        return productService.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @GetMapping(path = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Afficher un produit spécifique")
    public ProductDto findById(@NonNull @PathVariable Long productId) {
        return productService.findById(productId);
    }
}
