package com.example2.demo2;

public class DuplicateCompanyNameException extends RuntimeException{
    public DuplicateCompanyNameException(String message){
        super(message);
    }
}
