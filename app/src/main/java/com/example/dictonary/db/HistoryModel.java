package com.example.dictonary.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class HistoryModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String eng;
    private final String uzb;

    public HistoryModel(String eng, String uzb) {
        this.eng = eng;
        this.uzb = uzb;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEng() {
        return eng;
    }

    public String getUzb() {
        return uzb;
    }
}
