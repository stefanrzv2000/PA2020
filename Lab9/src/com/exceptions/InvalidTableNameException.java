package com.exceptions;

public class InvalidTableNameException extends DataBaseException {
    public InvalidTableNameException(String tableName) {
        super("Table " + tableName + " does not exist in the database.");
    }
}
