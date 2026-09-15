package com.example2.demo2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "EntrySheets")
@Data
public class EntrySheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String selfPr;
    private String studentExp;
    private String your_strong;
    private String your_weakness;

    // JPAがDBから取得したデータをUserオブジェクトとして復元する際などに使用する引数なしコンストラクタ
    public EntrySheet(){
    }
    // 自分用EntrySheetコンストラクタ
    public EntrySheet(String selfPr,String studentExp,String your_strong,String your_weakness){
        this.selfPr = selfPr;
        this.studentExp = studentExp;
        this.your_strong = your_strong;
        this.your_weakness = your_weakness;
    }
}
