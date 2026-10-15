package com.springboot.helpdesk.Exception;

public class OwnershipException extends RuntimeException{
    public OwnershipException(String message){
        super(message);
    }
}
