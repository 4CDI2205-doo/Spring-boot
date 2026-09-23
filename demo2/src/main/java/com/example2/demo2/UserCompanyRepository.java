package com.example2.demo2;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCompanyRepository 
        extends JpaRepository<UserCompany, Long> {
    boolean existsByUserAndCompany(User user,Company company);
}
