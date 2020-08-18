package com.bhn.shoppingcart.service;

import com.bhn.shoppingcart.repository.ProductRepository;
import com.bhn.shoppingcart.core.BusinessException;
import com.bhn.shoppingcart.model.dto.ProductDto;
import com.bhn.shoppingcart.model.entity.Product;
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

    ProductDto convertToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getCurrency(), product.getTax(), product.getTermsAndConditions(),product.getActivationAmount(),
                product.getImageUrl(), product.getCategory(), product.getProductConfigurationId());
    }
}
