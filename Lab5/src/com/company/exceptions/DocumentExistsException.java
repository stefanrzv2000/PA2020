package com.company.exceptions;

public class DocumentExistsException extends Exception {
    private String reason;

    public DocumentExistsException(String ID){
        reason = "A document with the ID " + ID + " already exists in this Catalog!";
    }

    @Override
    public String toString() {
        return reason;
    }
}
