package com.example.daily_new.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEvent(Event... events);
    @Update
    public void updateEvent(Event... events);
    @Delete
    public void deleteEvent(Event... events);
    @Query("SELECT * FROM EVENT_TABLE")
    public List<Event> getAllEvent();
    @Query("SELECT * FROM EVENT_TABLE WHERE id = :id")
    public Event getEventById(int id);
    @Delete()
    public void deleteEvent(Event event);
}
