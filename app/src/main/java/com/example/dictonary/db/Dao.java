package com.example.dictonary.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert(entity = Entity.class)
    void insertQuestion(Entity e);

    @Insert(entity = HistoryModel.class)
    void insertHistory(HistoryModel model);

    @Delete(entity = Entity.class)
    void deleteQuestion(Entity e);

    @Delete(entity = HistoryModel.class)
    void deleteHistory(HistoryModel model);

    @Query("SELECT * FROM questions")
    List<Entity> getAllQuestions();

    @Query("SELECT * FROM history ORDER BY id DESC")
    List<HistoryModel> getAllHistory();

    @Query("SELECT * FROM history WHERE id = :position")
    HistoryModel getById(int position);

    @Query("SELECT COUNT(id) FROM history")
    int getCount();

    @Query("SELECT COUNT(id) FROM questions")
    int getCountQuestions();
}
