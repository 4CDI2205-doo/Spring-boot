package com.example2.demo2;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserCompanyRepository 
        extends JpaRepository<UserCompany, Long> {
    boolean existsByUserAndCompany(User user,Company company);
    
    // ユーザーIDから企業を探す
    List<UserCompany> findByUser(User user);
}
