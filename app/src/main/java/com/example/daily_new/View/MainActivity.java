package com.example.daily_new.View;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daily_new.Adapter.EventAdapter;
import com.example.daily_new.Controllers.EventController;
import com.example.daily_new.DAO.Event;
import com.example.daily_new.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EventController eventController;
    private EventAdapter eventAdapter;
    private String TAG = "ERROR";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        uiDesign();
        // Init
        eventController = new EventController(MainActivity.this);
        // Display data
        eventController.DisPlay();
        //  click
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventController.DisPlay();
    }

    public void DisplayEvents(List<Event> events) {
        if (events.size() == 0) {
            return;
        }
        eventAdapter = new EventAdapter(events,MainActivity.this,eventController);
        ListView listView = findViewById(R.id.main_layout);
        listView.setAdapter(eventAdapter);
    }
    public void  uiDesign() {
        TextView header = findViewById(R.id.header);
        header.setTextSize(45);
    }
}
