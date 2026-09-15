package com.example2.demo2;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindingResult;
import java.util.List;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

// validでNotBlankなどのエラーハンドリングをするクラス
@RestControllerAdvice
public class GlobalExceptionHandler {
    // ここでエラーをeで受け取る
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException e){

        // 例外eからバリデーション結果を取得して、resultオブジェクトとして保存
        // getBindingResult()はバリデーション結果の取得が出来る
        BindingResult result = e.getBindingResult();

        // リストバリデーション結果をすべて受け取る
        List<FieldError> fieldErrors = result.getFieldErrors();

        // new HashMapはからのMapを作成している。
        // Map<String,String>はMap<キーの型,値の型＞　"name","名前を入力してください"などの型
        // JavaScriptに返しやすいJSON型に変換するためにMapを使用
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fieldErrors){
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.put(field,message);
        }
        return ResponseEntity.badRequest().body(errors);    
    }

    // emailが重複した際のエラーハンドリング　操作は上と同様
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String,String>> handDuplicateEmail(DuplicateEmailException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("email",e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
}
