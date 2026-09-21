package com.example2.demo2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.Map;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UserController{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 新規登録
    @PostMapping("/newlogin")
    public ResponseEntity<?> newLogin(@Valid @RequestBody User user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateEmailException("このユーザーはすでに登録されています。");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("ユーザー登録が完了しました。",HttpStatus.OK);
    }

    // ログイン
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser,HttpSession session){
        Optional<User> user = userRepository.findByEmail(loginUser.getEmail());
        // 一致するemailがない
        if (user.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("login","メールアドレスまたはパスワードが正しくありません"));
        }
        // パスワード照合
        User foundUser = user.get();
        if (!passwordEncoder.matches(loginUser.getPassword(),foundUser.getPassword())){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("login","メールアドレスまたはパスワードが正しくありません"));
        }
        session.setAttribute("userId",foundUser.getId());
        System.out.println("ログイン時 sessionId: " + session.getId());
        System.out.println("保存したuserId: " + session.getAttribute("userId"));
        return ResponseEntity.ok().build();
    }

    // セッション確認
    @GetMapping("/session-check")
    public ResponseEntity<?> sessionCheck(HttpSession session){
        System.out.println("確認時 sessionId: " + session.getId());
        Object userId = session.getAttribute("userId");
        System.out.println("確認時 userId: " + userId);
        if (userId == null){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message","ログインしていません"
            ));
        }
        return ResponseEntity.ok(Map.of("userId",userId));
    }

    // ログアウト
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // session解消
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
