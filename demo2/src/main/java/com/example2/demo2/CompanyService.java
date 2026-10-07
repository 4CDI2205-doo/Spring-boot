package com.example2.demo2;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final UserRepository userRepository;

    public CompanyService(
        CompanyRepository companyRepository,
        UserCompanyRepository userCompanyRepository,
        UserRepository userRepository){
            this.companyRepository = companyRepository;
            this.userCompanyRepository = userCompanyRepository;
            this.userRepository = userRepository;
    }

    // ユーザーID検索
    public Optional<User> findUserById(Long userId){
        return userRepository.findById(userId);
    }

    // 会社名検索
    public Optional<Company> findCompanyByName(String companyName){
        return companyRepository.findByCompanyName(companyName);
    }

    // Company登録
    public Company saveCompany(Company company){
        return companyRepository.save(company);
    }

    // 会社が登録されていればオブジェクトを返し、なければ新規登録
    public Company findOrCreateCompany(CompanyRegisterRequest request){
        Optional<Company> existingCompany = findCompanyByName(request.getCompanyName());
        // 企業が存在した場合
        if (existingCompany.isPresent()){
            return existingCompany.get();
        }

        // 存在しなかった場合
        Company company = new Company();

        company.setCompanyName(request.getCompanyName());
        company.setEmployeeCount(request.getEmployeeCount());
        company.setStartingSalary(request.getStartingSalary());
        company.setAnnualHolidays(request.getAnnualHolidays());

        return saveCompany(company);
    }

    // 1人のユーザーが同じ会社を登録していないか確認する
    public boolean isAlreadyRegistered(User user,Company company){
        return userCompanyRepository.existsByUserAndCompany(user,company);
    }

    // UserCompany登録
    public UserCompany createUserCompany(User user,Company company,CompanyRegisterRequest request){
        UserCompany userCompany = new UserCompany();

        userCompany.setUser(user);
        userCompany.setCompany(company);

        userCompany.setInterestLevel(request.getInterestLevel());
        userCompany.setSelectionStatus(request.getSelectionStatus());
        userCompany.setNextDate(request.getNextDate());

        return userCompanyRepository.save(userCompany);
    }

    // 一連の作業を行うメソッド
    public UserCompany registerCompany(Long userId,CompanyRegisterRequest request){
        User user = findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException("ユーザーが存在しません"));

        Company company = findOrCreateCompany(request);

        if (isAlreadyRegistered(user,company)){
            throw new CompanyAlreadyRegisteredException("この企業はすでに登録されています");
        }
        
        return createUserCompany(user,company,request);
    }

    // 企業情報の取得
    public List<CompanyListResponse> getUserCompanies(Long userId){
        User user = findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException("ユーザーが存在しません"));

        List<UserCompany> userCompanies = userCompanyRepository.findByUser(user);  
        List<CompanyListResponse> responses = new ArrayList<>();
        
        for (UserCompany userCompany : userCompanies){
            CompanyListResponse response = new CompanyListResponse();
            response.setCompanyName(userCompany.getCompany().getCompanyName());
            response.setEmployeeCount(userCompany.getCompany().getEmployeeCount());
            response.setStartingSalary(userCompany.getCompany().getStartingSalary());                response.setAnnualHolidays(userCompany.getCompany().getAnnualHolidays());
            response.setInterestLevel(userCompany.getInterestLevel());
            response.setSelectionStatus(userCompany.getSelectionStatus());
            response.setNextDate(userCompany.getNextDate());
            responses.add(response);
        }
        return responses;
    }
}
