package com.example2.demo2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.persistence.Table;

@Entity
@Table(name="companies")
@Data
public class Company {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="企業名を入力してください")
    private String companyName;
    
    private Integer employeeCount;
    private Integer startingSalary;
    private Integer annualHolidays;
}
