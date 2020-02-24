package com.example.sqlitecursor;

import android.provider.BaseColumns;

public class DatabaseContract {
    public DatabaseContract() {
    }
    public static class Contact implements BaseColumns {
        public static final String TABLE_NAME = "TABLE_CONTACTS";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_LAST_NAME = "LAST_NAME";
        public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
        public static final String COLUMN_MIDDLE_NAME = "MIDDLE_NAME";
        public static final int COLUMN_AGE = 0;

        public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LAST_NAME + " TEXT NOT NULL, " + COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                COLUMN_MIDDLE_NAME + " TEXT NOT NULL, " + COLUMN_AGE + " INTEGER NOT NULL);";
    }}
