package com.example2.demo2;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 企業検索リクエスト
@Data
public class CompanySearchRequest {
    @NotBlank(message="企業名を入力してください")
    private String companyName;
}