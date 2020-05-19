package com.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(int id){
        super("Game with id = " + id + " does not exist.");
    }
}
