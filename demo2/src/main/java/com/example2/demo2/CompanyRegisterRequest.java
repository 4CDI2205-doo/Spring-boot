package com.example2.demo2;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CompanyRegisterRequest {
    // Companyに保存する情報
    private String companyName;
    private Integer employeeCount;
    private Integer startingSalary;
    private Integer annualHolidays;

    // UserCompanyに保存する情報
    private String interestLevel;
    private String selectionStatus;
    private LocalDate nextDate;


    public CompanyRegisterRequest(){
    }
};
