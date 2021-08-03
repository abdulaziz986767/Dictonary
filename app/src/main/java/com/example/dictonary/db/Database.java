package com.example.dictonary.db;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Entity.class, HistoryModel.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static volatile Database instance;

    public abstract Dao dao();

    public static synchronized Database getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "dictionary")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
