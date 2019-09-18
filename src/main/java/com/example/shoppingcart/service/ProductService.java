package com.example.shoppingcart.service;

import com.example.shoppingcart.core.BusinessException;
import com.example.shoppingcart.model.dto.ProductDto;
import com.example.shoppingcart.model.entity.Product;
import com.example.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long productId) {
        return productRepository
                .findById(productId)
                .map(this::convertToProductDto)
                .orElseThrow(BusinessException::notFound);
    }

    private ProductDto convertToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
