package com.techelevator.tenmo.security;

public class InvalidTransferException extends Exception{

    public InvalidTransferException(String message) {
        super(message);
    }
}
