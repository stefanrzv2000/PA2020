package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class PlayerNameTakenException extends RuntimeException {
    public PlayerNameTakenException(String name){
        super("The name " + name + " is already assigned to another player.");
    }
}
