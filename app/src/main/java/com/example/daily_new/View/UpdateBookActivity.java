    package com.example.daily_new.View;

    import android.content.Intent;
    import android.database.Cursor;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import androidx.appcompat.app.AppCompatActivity;

    import com.example.daily_new.R;

    public class UpdateBookActivity extends AppCompatActivity {
        private EditText titleInput, authorInput, yearInput;
        private DatabaseHelper db;
        private int bookId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_book);

            titleInput = findViewById(R.id.titleInput);
            authorInput = findViewById(R.id.authorInput);
            yearInput = findViewById(R.id.yearInput);
            db = new DatabaseHelper(this);
            bookId = getIntent().getIntExtra("bookId", -1);

            loadBookDetails();

            Button updateButton = findViewById(R.id.updateButton);
            updateButton.setOnClickListener(v -> {
                String title = titleInput.getText().toString();
                String author = authorInput.getText().toString();
                int year = Integer.parseInt(yearInput.getText().toString());
                db.updateBook(bookId, title, author, year);
                finish(); // 关闭当前活动并返回
            });
        }

        private void loadBookDetails() {
            Cursor cursor = db.getAllBooks();
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getInt(0) == bookId) {
                        titleInput.setText(cursor.getString(1));
                        authorInput.setText(cursor.getString(2));
                        yearInput.setText(String.valueOf(cursor.getInt(3)));
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }