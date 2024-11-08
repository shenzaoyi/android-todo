package com.example.daily_new.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.daily_new.DAO.Event;
import com.example.daily_new.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ItemInfo extends AppCompatActivity {
    private Event event;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init
        setContentView(R.layout.iteminfo);
        event = (Event) getIntent().getSerializableExtra("item_data");
        // Display
        disPlay(event);
    }
    public void disPlay(Event event) {
        TextView infotitle = findViewById(R.id.infotitle);
        TextView infonotes = findViewById(R.id.infonotes);
        TextView infodue = findViewById(R.id.due_date);
        infotitle.setText(event.getTitle());
        if (event.isImportant()) {
            int highlightcolor = ContextCompat.getColor(this,R.color.red);
            infotitle.setTextColor(highlightcolor);
        }
        infonotes.setText(event.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());;
        infodue.setText(simpleDateFormat.format(event.getEndtime().longValue()));
    }
}
