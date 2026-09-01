package com.example2.demo2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AllController{
    //ユーザー情報を保存するリスト
    private List<User> users = new ArrayList<>();

    @PostMapping("/newlogin")
    public ResponseEntity<String> newLogin(@RequestBody User user){
        if (user.getName() == null || user.getName().isBlank() || user.getEmail() == null || user.getEmail().isBlank() || user.getPassword() == null || user.getPassword().isBlank() || user.getSchool() == null || user.getSchool().isBlank() || user.getFaculty() == null || user.getFaculty().isBlank() || user.getDepartment() == null || user.getDepartment().isBlank() || user.getAge() <= 0 || user.getDate() == null || user.getDate().isBlank()){
            return new ResponseEntity<String>("入力が不十分です。",HttpStatus.BAD_REQUEST);
        }
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getEmail() == user.getEmail() || users.get(i).getId() == user.getId() || users.get(i).getPassword() == user.getPassword()){
                return new ResponseEntity<>("このユーザーはすでに登録されています。",HttpStatus.CONFLICT);
            }
        }
        users.add(user);
        return new ResponseEntity<String>("ユーザー登録が完了しました。",HttpStatus.OK);
    }

}
