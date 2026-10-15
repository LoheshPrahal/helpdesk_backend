package com.springboot.helpdesk.Exception;

public class InvalidCallException extends RuntimeException{
    public InvalidCallException(String message){
        super(message);
    }
}
