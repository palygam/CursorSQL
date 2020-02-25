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

import java.util.ArrayList;
import java.util.List;

public class ShowDatabaseActivity extends AppCompatActivity {
    private RecyclerViewCursorAdapter adapter;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);
        setupToolbar();
        intiRecyclerView();
        loadContacts();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    private void loadContacts() {
        final Handler handler = new Handler();
        Thread backgroundThread = new Thread(() -> {
            contacts = DatabaseHelper.getInstance(ShowDatabaseActivity.this).getContacts();
            handler.post(() -> adapter.setContacts(contacts));
        });
        backgroundThread.start();
    }

    private void intiRecyclerView() {
        adapter = new RecyclerViewCursorAdapter(ShowDatabaseActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
