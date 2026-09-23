package com.example2.demo2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Optional;

@RestController 
@RequestMapping("/company")
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final UserRepository userRepository;

    public CompanyController(CompanyRepository companyRepository,UserCompanyRepository userCompanyRepository,UserRepository userRepository){
        this.companyRepository = companyRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterCompany(@Valid @RequestBody CompanyRegisterRequest request,HttpSession session){
        // userIdを取得
        Long userId = (Long)session.getAttribute("userId");
        if (userId == null){
            return new ResponseEntity<>("ログインが必要です",HttpStatus.UNAUTHORIZED);
        }
        // ログインしているUserを取得
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()){
            return new ResponseEntity<>("ユーザーが存在しません",HttpStatus.UNAUTHORIZED);
        }
        User user = existingUser.get();

        // Companyを取得
        Optional<Company> existingCompany = companyRepository.findByCompanyName(request.getCompanyName());
        Company company;
        if (existingCompany.isPresent()){
            // すでに登録されている企業
            company = existingCompany.get();
        }else{
            // まだ登録されていない企業
            company = new Company();
            
            company.setCompanyName(request.getCompanyName());
            company.setEmployeeCount(request.getEmployeeCount());
            company.setStartingSalary(request.getStartingSalary());
            company.setAnnualHolidays(request.getAnnualHolidays());

            company = companyRepository.save(company);
        }
        // 同じユーザーの重複登録防止
        boolean alreadyRegistered = userCompanyRepository.existsByUserAndCompany(user, company);
        if (alreadyRegistered){
            return new ResponseEntity<>("この企業はすでに登録されています",HttpStatus.CONFLICT);
        }

        // UserCompany関連
        UserCompany userCompany = new UserCompany();
        // 上で設定したcompanyを登録
        userCompany.setUser(user);
        userCompany.setCompany(company);
        userCompany.setInterestLevel(request.getInterestLevel());
        userCompany.setSelectionStatus(request.getSelectionStatus());
        userCompany.setNextDate(request.getNextDate());

        userCompanyRepository.save(userCompany);

        return new ResponseEntity<>(userCompany,HttpStatus.CREATED);
    }
}
