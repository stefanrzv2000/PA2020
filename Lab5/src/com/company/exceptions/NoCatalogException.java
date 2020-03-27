package com.company.exceptions;

public class NoCatalogException extends Exception{
    String reason;

    public NoCatalogException(){
        reason = "There is no catalog opened!";
    }

    public NoCatalogException(String reason){
        this.reason = reason;
    }

    public void printReason(){
        System.err.println(reason);
        System.err.flush();
    }
}
