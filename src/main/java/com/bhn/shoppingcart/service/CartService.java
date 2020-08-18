package com.bhn.shoppingcart.service;

import com.bhn.shoppingcart.core.BusinessException;
import com.bhn.shoppingcart.model.dto.CartDto;
import com.bhn.shoppingcart.model.dto.ItemDto;
import com.bhn.shoppingcart.model.dto.OrderDto;
import com.bhn.shoppingcart.model.dto.ProductDto;
import com.bhn.shoppingcart.model.entity.Cart;
import com.bhn.shoppingcart.model.entity.Item;
import com.bhn.shoppingcart.model.entity.Order;
import com.bhn.shoppingcart.model.entity.Product;
import com.bhn.shoppingcart.model.types.Channel;
import com.bhn.shoppingcart.model.types.OrderType;
import com.bhn.shoppingcart.repository.CartRepository;
import com.bhn.shoppingcart.repository.ItemRepository;
import com.bhn.shoppingcart.repository.OrderRepository;
import com.bhn.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    public CartService(
            CartRepository cartRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional(readOnly = true)
    public CartDto findById(Long cartId) {
        return cartRepository
                .findById(cartId)
                .map(this::convertToCartDto)
                .orElseThrow(BusinessException::notFound);
    }


    @Transactional(readOnly = true)
    public CartDto findByUserId(String userId) {
        return cartRepository
                .findByUserId(userId)
                .map(this::convertToCartDto)
                .orElseThrow(BusinessException::notFound);
    }

    @Transactional(readOnly = true)
    public CartDto findBySessionIdAndUserId(String sessionId, String userId) {
        return cartRepository
                .findBySessionIdAndUserId(sessionId, userId)
                .map(this::convertToCartDto)
                .orElseThrow(BusinessException::notFound);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CartDto createCart(String sessionId, String userId) {
        cartRepository.findBySessionIdAndUserId(sessionId,userId).ifPresent(cart -> {throw BusinessException.exists();});
        Cart input = new Cart(sessionId,userId);
        input.setCreatedDate(LocalDateTime.now());
        final Cart cart = cartRepository.saveAndFlush(input);
        Order orderNew=new Order();
        orderNew.setCart(cart);
        orderNew.setOrderType(OrderType.B2B);
        orderNew.setChannel(Channel.Api);
        orderService.createOrder(cart,orderNew);
        return convertToCartDto(cart);
    }

    public CartDto clearCart(Long cartId) {
        final Cart cartExisting = cartRepository.findById(cartId)
                .orElseThrow(BusinessException::notFound);
        OrderDto orderDto=orderService.removeAllItems(cartExisting.getOrder().getId());
        CartDto cartDto =  convertToCartDto(cartExisting);
        cartDto.setOrder(orderDto);
        return cartDto;

    }


    private CartDto convertToCartDto(Cart cart) {
        final OrderDto orderDto = (cart.getOrder() != null) ? convertToOrderDto(cart.getOrder()) : null;
        CartDto cartDto  =  new CartDto(cart.getId(),cart.getUserId(),cart.getSessionId(),cart.getCreatedDate(), orderDto);
        return cartDto;
    }

    private OrderDto convertToOrderDto(Order order) {
        final Set<ItemDto> itemDtos = order.getItems()
                .stream()
                .map(this::convertToItemDto)
                .collect(Collectors.toSet());
        return new OrderDto(order.getId(), order.getCreatedDate(), itemDtos);
    }

    private ItemDto convertToItemDto(Item item) {
        return new ItemDto(convertToProductDto(item.getPk().getProduct()), item.getQuantity());
    }

    private ProductDto convertToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getCurrency(), product.getTax(), product.getTermsAndConditions(),product.getActivationAmount(),
                product.getImageUrl(), product.getCategory(), product.getProductConfigurationId());

    }
}
