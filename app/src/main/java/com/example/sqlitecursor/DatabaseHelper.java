package com.example.sqlitecursor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper INSTANCE;
    private static final String DATABASE_NAME = "DATABASE_CONTACTS";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "TABLE_CONTACTS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_MIDDLE_NAME = "MIDDLE_NAME";
    public static final String COLUMN_AGE = "AGE";

    public static final String[] COLUMNS = {COLUMN_ID, COLUMN_LAST_NAME, COLUMN_FIRST_NAME, COLUMN_MIDDLE_NAME, COLUMN_AGE};

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LAST_NAME + " TEXT NOT NULL, " + COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
            COLUMN_MIDDLE_NAME + " TEXT NOT NULL, " + COLUMN_AGE + " INTEGER NOT NULL);";


    static DatabaseHelper getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                INSTANCE = new DatabaseHelper(context.getApplicationContext());
            }
        }
        return INSTANCE;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public long addContact(String lastName, String firstName, String middleName, int age) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_MIDDLE_NAME, middleName);
        values.put(COLUMN_AGE, age);

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(TABLE_NAME, null, values);
        db.close();
        return returnId;
    }

    public List<Contact> getContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null);
        List<Contact> contacts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                contacts.add(new Contact(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MIDDLE_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AGE))));
                cursor.moveToNext();
            }
        }
        return contacts;
    }
}
