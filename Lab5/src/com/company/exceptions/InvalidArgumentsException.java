package com.company.exceptions;

public class InvalidArgumentsException extends Exception {

    String reason;

    public InvalidArgumentsException(String reason){
        super("argumente invalide");
        this.reason = reason;
    }

    public void printReason(){
        System.err.println(reason);
        System.err.flush();
    }
}
