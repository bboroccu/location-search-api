package com.kakaobank.location.entity;

public enum UserRole {
    USER;
    public String authority() {
        return "ROLE_" + this.name();
    }
}
