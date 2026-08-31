package com.example2.demo2;

import lombok.Data;

@Data
public class EntrySheet {
    private int id;
    private String PR;
    private String CH;
    private String your_strong;
    private String your_weakness;

    private int idcounter = 0;

    public EntrySheet(String PR,String CH,String your_strong,String your_weakness){
        this.id = idcounter++;
        this.PR = PR;
        this.CH = CH;
        this.your_strong = your_strong;
        this.your_weakness = your_weakness;
    }
}
