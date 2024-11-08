package com.example.daily_new.DAO;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EventRepo {
    private DAO dao;
    public EventRepo(Application application) {
        DailyDatabase instance = DailyDatabase.getInstance(application);
        dao = instance.dao();
    }
    public void insert(Event event) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            dao.insertEvent(event);
        });
    }
    public void getAllEvents(EventCallback callback) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            List<Event> events = dao.getAllEvent();
            callback.onEventLoaded(events);
        });
    }
    public void delete(Event event) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            dao.deleteEvent(event);
        });
    }
}
