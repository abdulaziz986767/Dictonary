package com.example.dictonary.db;

import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "questions")
public class Entity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String eng;
    private final String uzb;

    public Entity(String eng, String uzb) {
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
