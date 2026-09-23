package com.example2.demo2;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity 
@Data
public class UserCompany {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 複数のUserCompanyから一つのUserを参照
    @ManyToOne 
    private User user;
    // 複数のUserCompanyから一つのCompanyを参照
    @ManyToOne
    private Company company;
    
    private String interestLevel;
    private String selectionStatus;
    private LocalDate nextDate;
}
