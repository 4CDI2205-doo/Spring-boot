package com.example2.demo2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AllController{
    private final UserRepository userRepository;
    
    public AllController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/newlogin")
    public ResponseEntity<String> newLogin(@Validated @RequestBody User user){
        if (userRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("このユーザーはすでに登録されています。",HttpStatus.CONFLICT);
        }
        userRepository.save(user);
        return new ResponseEntity<String>("ユーザー登録が完了しました。",HttpStatus.OK);
    }
}
