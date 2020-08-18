package com.bhn.shoppingcart.core;

public final class BusinessException extends RuntimeException {

    private final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public static BusinessException notFound() {
        return new BusinessException(Messages.ERROR_ID_DOESNT_EXIST);
    }

    public static BusinessException exists() {
        return new BusinessException(Messages.ERROR_ID_EXIST);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
