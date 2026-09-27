package com.example2.demo2;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository
        extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyName(String companyName);
}