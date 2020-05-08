package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(Integer id){
        super(String.format("Player with id = %d not found",id));
    }
    public PlayerNotFoundException(String name){
        super(String.format("Player named %s does not exist",name));
    }
}
