package com.groupfive.assignment.error;

public class UserNotVerifyException  extends RuntimeException{
    public UserNotVerifyException(String message) {
        super(message);
    }
}
