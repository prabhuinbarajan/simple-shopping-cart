package com.bhn.shoppingcart.controller;

import com.bhn.shoppingcart.core.BusinessException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonNode handleException(BusinessException exception) {
        logger.info(String.format("Business rule violation: %s", exception.getMessage()));
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("message", exception.getMessage());
        return objectNode;
    }
}
