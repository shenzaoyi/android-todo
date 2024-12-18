package com.example.daily_new.Controllers;

import android.app.Application;
import android.util.Log;

import com.example.daily_new.DAO.Event;
import com.example.daily_new.DAO.EventCallback;
import com.example.daily_new.DAO.EventRepo;
import com.example.daily_new.View.MainActivity;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class EventController {
    private MainActivity view;
    private EventRepo repo;
    private String TAG = "Event Controller";

    public EventController(MainActivity view) {
        Application application = view.getApplication();
        repo = new EventRepo(application);
        this.view = view;
    }
    public class callback implements EventCallback{
        private List<Event> events;
        private CountDownLatch countDownLatch;
        @Override
        public void onEventLoaded(List<Event> events) {
            this.events = events;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
        public void setLatch(CountDownLatch latch) {
            this.countDownLatch = latch;
        }
    }
    public void DisPlay() {
        callback cb = new callback();
        CountDownLatch latch = new CountDownLatch(1);
        cb.setLatch(latch);
        // 这个位置是异步的，需要等待线程执行的结果
        repo.getAllEvents(cb);
        try {
            latch.await(); // 等待异步操作完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (cb.events == null) {
            Log.d("ERROR", "DisPlay: THERE NOTHING");
            return;
        }
        view.DisplayEvents(cb.events);
    }
    public void DeleteEvent(Event event) {
        repo.delete(event);
    }
}
