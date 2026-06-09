package com.example.daily_new.View;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE Books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "author TEXT," +
                "publishedYear INTEGER)";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Books");
        onCreate(db);
    }

    // 增
    public void addBook(String title, String author, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("author", author);
        values.put("publishedYear", year);
        db.insert("Books", null, values);
        db.close();
    }

    // 删
    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Books", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 改
    public void updateBook(int id, String title, String author, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("author", author);
        values.put("publishedYear", year);
        db.update("Books", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 查
    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Books", null);
    }
    public Cursor searchBooks(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ?";
        return db.rawQuery(sql, new String[]{"%" + query + "%", "%" + query + "%"});
    }
}