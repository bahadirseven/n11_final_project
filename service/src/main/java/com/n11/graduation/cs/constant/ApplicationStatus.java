package com.n11.graduation.cs.constant;

public enum ApplicationStatus {
    ACCEPTED("ACCEPTED", "Onay"),
    REJECTED("REJECTED", "Red");

    private final String status;
    private final String message;

    ApplicationStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
