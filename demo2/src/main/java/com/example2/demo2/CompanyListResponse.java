package com.example2.demo2;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CompanyListResponse {
    // 企業情報取得時のDTO
    private String companyName;
    private Integer employeeCount;
    private Integer startingSalary;
    private Integer annualHolidays;

    private String interestLevel;
    private String selectionStatus;
    private LocalDate nextDate;
}


