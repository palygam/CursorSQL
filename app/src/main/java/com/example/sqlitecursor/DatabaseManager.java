package com.example.sqlitecursor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.example.sqlitecursor.DatabaseHelper.ID;

public class DatabaseManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String lastName, String firstName, String middleName, int age) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.LAST_NAME, lastName);
        contentValue.put(DatabaseHelper.FIRST_NAME, firstName);
        contentValue.put(DatabaseHelper.MIDDLE_NAME, middleName);
        contentValue.put(String.valueOf(DatabaseHelper.AGE), age);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { ID, DatabaseHelper.LAST_NAME, DatabaseHelper.FIRST_NAME , DatabaseHelper.MIDDLE_NAME, String.valueOf(DatabaseHelper.AGE)};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, String lastName, String firstName, String middleName, int age) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LAST_NAME, lastName);
        contentValues.put(DatabaseHelper.FIRST_NAME, firstName);
        contentValues.put(DatabaseHelper.MIDDLE_NAME, middleName);
        contentValues.put(String.valueOf(DatabaseHelper.AGE), age);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, ID + " = " + id, null);
        return i;
    }

    public void delete(long id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=" + id, null);
    }
}
