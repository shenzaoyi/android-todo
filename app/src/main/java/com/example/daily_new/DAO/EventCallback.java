package com.example.daily_new.DAO;

import com.example.daily_new.DAO.Event;

import java.util.List;

public interface EventCallback {
    void onEventLoaded(List<Event> events);
}