package com.example2.demo2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class AllController{

    @GetMapping("/hello")
    public String Hello() {
        return "Hello Spring Boot!";
    }

}
