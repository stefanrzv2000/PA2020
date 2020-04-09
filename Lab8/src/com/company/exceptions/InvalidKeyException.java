package com.company.exceptions;

public class InvalidKeyException extends DataBaseException {
    public InvalidKeyException(String key) {
        super("Invalid Key Exveption: '" + key + "' is not a valid kay for your selected data.");
    }
}
