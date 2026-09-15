package com.example2.demo2;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // 送信内容の確認と定数指定
    @NotBlank(message="名前を入力してください")
    private String name;

    @NotBlank(message="Emailを入力してください")
    @Email(message="正しいメールアドレス形式で入力してください")
    private String email;

    @NotBlank(message="パスワードを入力してください")
    private String password;

    @NotBlank(message="学校名を入力してください")
    private String school;

    @NotBlank(message="学部名を入力してください")
    private String faculty;

    @NotBlank(message="学科名を入力してください")
    private String department;

    @NotNull(message="年齢を入力してください")
    @Min(value = 0,message="年齢は0以上で入力してください")
    private Integer age;

    @NotNull(message="生年月日を入力してください")
    private LocalDate date;

    // JPAがDBから取得したデータをUserオブジェクトとして復元する際などに使用する引数なしコンストラクタ
    public User(){
    }
    // 自分用Userコンストラクタ
    public User(String name, String email,String password,String school,String faculty, String department, Integer age, LocalDate date){
        this.name = name;
        this.email = email;
        this.password = password;
        this.school = school;
        this.faculty = faculty;
        this.department = department;
        this.age = age;
        this.date = date;
    }
}
