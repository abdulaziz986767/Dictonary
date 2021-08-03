package com.example.dictonary.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dictonary.R;
import com.example.dictonary.db.Database;
import com.example.dictonary.db.Entity;

public class AddActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText englishEt;
    private EditText uzbEt;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle("Add");
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        englishEt = findViewById(R.id.englishEt);
        uzbEt = findViewById(R.id.uzbEt);

        Button addBtn = findViewById(R.id.btnAdd);

        db = Database.getInstance(this.getApplicationContext());

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String english = englishEt.getText().toString();
        String uzb = uzbEt.getText().toString();

        if (TextUtils.isEmpty(english)) {
            englishEt.setError("Inglizcha so'z topilmadi");
            englishEt.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(uzb)) {
            englishEt.setError("So'z topilmadi");
            englishEt.requestFocus();
            return;
        }

        saveToDb(english, uzb);
    }

    private void saveToDb(String english, String uzb) {
        Entity e = new Entity(english, uzb);
        db.dao().insertQuestion(e);

        englishEt.setText("");
        uzbEt.setText("");

        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}