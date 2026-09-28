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
import java.util.Optional;

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
        // ログインしているUserを取得
        Optional<User> existingUser = companyService.findUserById(userId);
        if (existingUser.isEmpty()){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message","ユーザーが存在しません"));
        }
        User user = existingUser.get();

        // Companyを取得
        Company company = companyService.findOrCreateCompany(request);
        
        // 同じユーザーの重複登録防止
        boolean alreadyRegistered = companyService.isAlreadyRegistered(user,company);
        if (alreadyRegistered){
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message","この企業はすでに登録されています"));
        }

        // UserCompany関連
        UserCompany userCompany = companyService.createUserCompany(user,company,request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userCompany);
    }
}
