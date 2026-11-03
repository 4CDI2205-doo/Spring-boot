package com.example2.demo2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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

    // 企業情報の取得
    @GetMapping("/list")
    public ResponseEntity<?> getCompanyList(HttpSession session){
        // userIdを取得
        Long userId = (Long)session.getAttribute("userId");
        if (userId == null){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message","ログインが必要です"));
        }

        // 企業情報の取得
        List<CompanyListResponse> userCompanies = companyService.getUserCompanies(userId);

        // 企業情報の返却
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userCompanies);
    }

    // 検索企業
    @PostMapping("/search")
    public ResponseEntity<?> getSearchCompany(@Valid @RequestBody CompanySearchRequest request,HttpSession session){
        // userIdを取得
        Long userId = (Long)session.getAttribute("userId");
        if (userId == null){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message","ログインが必要です"));
        }
        
        // 企業名検索
        List<CompanySearchResponse> companySearchNumber = companyService.searchCompany(request.getCompanyName());

        // 企業詳細リスト
        List<CompanySearchResponse> companySearchResponse = new ArrayList<>();

        // 検索企業の企業ナンバー取得
        for (CompanySearchResponse company : companySearchNumber){
            String corporateNumber = company.getCorporate_number();

            List<CompanySearchResponse> detail = companyService.getCompanyDetail(corporateNumber);
             companySearchResponse.addAll(detail);
        }
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(companySearchResponse);
    }
}
