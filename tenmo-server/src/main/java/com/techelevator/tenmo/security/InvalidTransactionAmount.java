package com.techelevator.tenmo.security;

public class InvalidTransactionAmount extends Exception {

    public InvalidTransactionAmount(String message) {
        super(message);
    }

}
