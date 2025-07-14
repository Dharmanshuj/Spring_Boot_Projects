package com.example.test.customException;

public class userAlreadyExistsException extends RuntimeException{

    public userAlreadyExistsException(String message) {
        super(message);
    }
}
