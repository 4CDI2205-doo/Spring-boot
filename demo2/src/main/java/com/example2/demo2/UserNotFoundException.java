package com.example2.demo2;

// ユーザーが見つからない場合
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}