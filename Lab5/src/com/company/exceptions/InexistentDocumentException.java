package com.company.exceptions;

public class InexistentDocumentException extends Exception {
    String reason;

    InexistentDocumentException(){
        reason = "The Document does not exist in the current Catalog!";
    }

    public InexistentDocumentException(String docID){
        reason = "The Document with ID = " + docID + " does not exist in the current Catalog!";
    }

    @Override
    public String toString() {
        return reason;
    }
}
