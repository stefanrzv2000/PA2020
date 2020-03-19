package com.company.exceptions;

public class InvalidDocumentException extends Exception {
    public InvalidDocumentException(Exception e){
        super("Invalid document path.", e);
    }
}
