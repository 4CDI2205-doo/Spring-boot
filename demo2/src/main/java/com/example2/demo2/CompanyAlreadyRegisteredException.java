package com.example2.demo2;

// 登録済み例外
public class CompanyAlreadyRegisteredException extends RuntimeException{
    public CompanyAlreadyRegisteredException(String message){
        super(message);
    }
}
