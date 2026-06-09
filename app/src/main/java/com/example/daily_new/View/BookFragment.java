package com.example.daily_new.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daily_new.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private DatabaseHelper db;

    // 定义输入框
    private TextInputEditText searchInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        // 初始化数据库和书籍列表
        db = new DatabaseHelper(getContext());
        bookList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookAdapter = new BookAdapter(bookList, getContext());
        recyclerView.setAdapter(bookAdapter);

        loadBooks(); // 加载所有书籍

        Button addBookButton = view.findViewById(R.id.btnAddBook);
        addBookButton.setOnClickListener(v -> {
            // 跳转到 AddBookActivity
            Intent intent = new Intent(getActivity(), AddBookActivity.class);
            startActivity(intent);
        });

        // 初始化搜索输入框
        searchInput = view.findViewById(R.id.searchInput);

        // 设置搜索按钮的点击事件
        Button searchButton = view.findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString();
            searchBooks(query); // 调用搜索功能
        });

        return view;
    }

    private void loadBooks() {
        Cursor cursor = db.getAllBooks();
        bookList.clear();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                int publishedYear = cursor.getInt(3);
                bookList.add(new Book(id, title, author, publishedYear));
            } while (cursor.moveToNext());
        }
        cursor.close();
        bookAdapter.notifyDataSetChanged();
    }

    private void searchBooks(String query) {
        Cursor cursor = db.searchBooks(query);
        bookList.clear();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                int publishedYear = cursor.getInt(3);
                bookList.add(new Book(id, title, author, publishedYear));
            } while (cursor.moveToNext());
        }
        cursor.close();
        bookAdapter.notifyDataSetChanged(); // 更新 RecyclerView
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBooks(); // 返回时加载书籍
    }
}