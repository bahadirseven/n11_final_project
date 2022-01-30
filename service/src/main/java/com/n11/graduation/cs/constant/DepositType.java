package com.n11.graduation.cs.constant;

public enum DepositType {
    HOUSE("House"),
    VEHICLE("Vehicle");

    private final String type;

    DepositType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}