package com.example2.demo2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AllController {

    @GetMapping("/hello")
    public String Hello() {
        return "Hello Spring Boot!";
    }

}
