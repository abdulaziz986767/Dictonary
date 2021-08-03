package com.example.dictonary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dictonary.adapters.HistoryAdapter;
import com.example.dictonary.R;
import com.example.dictonary.db.Database;
import com.example.dictonary.db.HistoryModel;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<HistoryModel> models;
    private Database db;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle("Arxiv");
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        RecyclerView rc = findViewById(R.id.historyRecycler);
        rc.setLayoutManager(new LinearLayoutManager(this));

        db = Database.getInstance(this.getApplicationContext());

        models = db.dao().getAllHistory();

        adapter = new HistoryAdapter(models);

        rc.setAdapter(adapter);

        touchHelper.attachToRecyclerView(rc);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private final ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            db.dao().deleteHistory(models.get(viewHolder.getAdapterPosition()));
            models.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    });
}