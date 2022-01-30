package com.n11.graduation.cs.constant;

public enum ResponseMessage {
    SUCCESS_DELETE("Entity deleted succesfully.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
