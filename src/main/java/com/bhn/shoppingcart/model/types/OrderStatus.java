package com.bhn.shoppingcart.model.types;

public  enum OrderStatus {
    DRAFT,
    CHECKOUT,
    IN_REVIEW,
    IN_FULFILLMENT,
    IN_SHIPMENT,
    COMPLETED,
    CANCELLED,
    REFUNDED,
    RETURNED
}