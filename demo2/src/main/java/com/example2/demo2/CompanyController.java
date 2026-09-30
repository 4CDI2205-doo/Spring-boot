package com.example2.demo2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.Map;

@RestController 
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCompany(@Valid @RequestBody CompanyRegisterRequest request,HttpSession session){
        // userIdを取得
        Long userId = (Long)session.getAttribute("userId");
        if (userId == null){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message","ログインが必要です"));
        }

        // UserCompany登録
        UserCompany userCompany = companyService.registerCompany(userId,request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userCompany);
    }
}
