package com.example.rbac.exception.custom;

public class RoleAlreadyExistsException extends RuntimeException{

    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
