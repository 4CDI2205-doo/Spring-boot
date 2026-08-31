package com.example2.demo2;

import lombok.Data;

@Data
public class User{
    private int id;
    private String name;
    private String email;
    private String password;
    private String school;
    private String faculty;
    private String department;
    private int age;
    private String date;

    private static int idcounter = 0;

    public User(String name, String email,String password,String school,String faculty, String department, int age, String date){
        this.id = idcounter++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.school = school;
        this.faculty = faculty;
        this.department = department;
        this.age = age;
        this.date = date;
    }
}
