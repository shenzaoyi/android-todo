package com.example.daily_new.DAO;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {Event.class}, version = 3, exportSchema = false)
public abstract class DailyDatabase extends RoomDatabase {
    public abstract DAO dao();
    private static DailyDatabase instance;
    public static synchronized DailyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),DailyDatabase.class,"daily_db").addMigrations(Event.MIGRATION_2_3).build();
        }
        return instance;
    }
}