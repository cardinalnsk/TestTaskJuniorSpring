package ru.cardinalnsk.springjavajuniortest.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
