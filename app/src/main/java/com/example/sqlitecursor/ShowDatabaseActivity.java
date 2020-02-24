package com.example.sqlitecursor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowDatabaseActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[] {
            DatabaseHelper.LAST_NAME, DatabaseHelper.FIRST_NAME, DatabaseHelper.MIDDLE_NAME, String.valueOf(DatabaseHelper.AGE)};
    final int[] to = new int[] { R.id.last_name_text_view, R.id.first_name_text_view, R.id.middle_name_text_view, R.id.age_text_view };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);
        dbManager = new DatabaseManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        setupToolbar();
        getData();}

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    private void getData() {
        final Handler handler = new Handler();
        Thread backgroundThread = new Thread(() -> {
           /* contacts = DatabaseHelper.getINSTANCE(ShowDatabaseActivity.this).contactDao().getAll();
            handler.post(() -> adapter.setContacts(contacts));*/
        });
        backgroundThread.start();
    }
}
