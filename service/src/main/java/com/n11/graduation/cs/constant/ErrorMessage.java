package com.n11.graduation.cs.constant;

public enum ErrorMessage {
    CUSTOMER_NOT_FOUND("Customer not found."),
    APPLICATION_NOT_FOUND("Application not found."),
    INVALID_PARAMETER("Invalid parameter found."),
    CREDIT_SCORE_NOT_FOUND_FOR_USER("Credit score not found for user."),
    SCORE_NOT_GENERATED("Unexpected error occured when generate credit score."),
    CONSTRAINT_VIOLATION("Constraint violation occured when connected database.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
