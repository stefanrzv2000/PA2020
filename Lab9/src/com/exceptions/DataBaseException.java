package com.exceptions;

public class DataBaseException extends Exception {
    private String reason;

    public DataBaseException(String reason){
        this.reason = reason;
    }

    public void printReason(){
        System.err.println("DataBase Exception: " + reason);
    }

}
