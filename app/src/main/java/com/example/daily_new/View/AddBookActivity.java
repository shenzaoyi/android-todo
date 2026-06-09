package com.example.daily_new.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daily_new.R;

public class AddBookActivity extends AppCompatActivity {
    private EditText titleInput, authorInput, yearInput;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleInput = findViewById(R.id.titleInput);
        authorInput = findViewById(R.id.authorInput);
        yearInput = findViewById(R.id.yearInput);
        db = new DatabaseHelper(this);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String author = authorInput.getText().toString();
            int year = Integer.parseInt(yearInput.getText().toString());
            db.addBook(title, author, year);
            finish(); // 关闭当前活动并返回  
        });
    }
}