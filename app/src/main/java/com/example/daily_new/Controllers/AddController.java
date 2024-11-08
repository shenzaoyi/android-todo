package com.example.daily_new.Controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.daily_new.DAO.Event;
import com.example.daily_new.DAO.EventRepo;
import com.example.daily_new.View.AddActivity;
import com.example.daily_new.View.AlertReciever;

public class AddController {
    AddActivity view;
    private  String TAG = "AddItem";
    EventRepo eventRepo;

    public AddController(AddActivity view) {
        this.eventRepo = new EventRepo(view.getApplication());
        this.view = view;
    }
    // Store Date
    public void Store(Event event) {
        eventRepo.insert(event);
    }
    // alarm
    public void setAlarm(long triggerAtMillis) {
        AlarmManager alarmManager = (AlarmManager) view.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(view, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(view,0,intent,PendingIntent.FLAG_IMMUTABLE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis,pendingIntent);
            Log.d(TAG, "setAlarm: successful");
        }else {
            Log.e(TAG, "Failed to get AlarmManager");
        }

    }
}
