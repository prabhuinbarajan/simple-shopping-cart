package com.example.shoppingcart.service;

import com.example.shoppingcart.model.dto.ProductDto;
import com.example.shoppingcart.model.entity.Product;
import com.example.shoppingcart.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenProductById_whenProductRetrieved_thenContainsProductId() {
        final Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        final ProductDto productDto = productService.findById(1L);

        assertEquals(1L, productDto.getId().longValue());
    }

    @Test
    public void givenProductList_whenProductListRetrieved_thenContainsProducts() {
        final Product productA = new Product();
        productA.setId(1L);
        final Product productB = new Product();
        productB.setId(2L);

        final ArrayList<Product> products = new ArrayList<>();
        products.add(productA);
        products.add(productB);

        final PageRequest pageRequest = PageRequest.of(0, 2);
        final PageImpl<Product> productPage = new PageImpl<Product>(products, pageRequest, 2);

        when(productRepository.findAll(pageRequest)).thenReturn(productPage);

        final List<ProductDto> productDtoList = productService.findAll(pageRequest);

        assertEquals(2, productDtoList.size());
    }
}