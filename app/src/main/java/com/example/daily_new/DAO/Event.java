package com.example.daily_new.DAO;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.Serializable;


@Entity(tableName = "event_table")
public class Event implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Long endtime;
    private boolean important;
    private String content;


    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    public static final Migration MIGRATION_2_3 = new Migration(2,3) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 删除旧表
            database.execSQL("DROP TABLE IF EXISTS event_table");
            // 创建新表
            // 这里有个坑，end是一个关键字，end命令直接crash，查了半天
            database.execSQL("CREATE TABLE event_table ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "title TEXT, "
                    + "endtime INTEGER, "
                    + "important INTEGER NOT NULL DEFAULT 0, "
                    + "content TEXT)");
        }
    };
    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public int getId() {
        return id;
    }

    public Event(String title, Long endtime, boolean important, String content) {
        this.title = title;
        this.endtime = endtime;
        this.important = important;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Long getEndtime() {
        return endtime;
    }
}
