package com.example.dictonary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.dictonary.MyReceiver;
import com.example.dictonary.R;
import com.example.dictonary.db.Database;

public class MainActivity extends AppCompatActivity {

    private CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        card = findViewById(R.id.cardBegan);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int countA = Database.getInstance(this.getApplicationContext())
                .dao().getCount();

        card.setClickable(countA > 10);

        MyReceiver receiver = new MyReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);

        registerReceiver(receiver, filter);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickCard(View view) {
        switch (view.getId()) {
            case R.id.addCard: {
                startActivity(new Intent(this, AddActivity.class));
            } break;
            case R.id.cardBegin: {
                startActivity(new Intent(this, QuestionActivity.class)
                        .putExtra("id", 1));
            } break;
            case R.id.cardBegan: {
                startActivity(new Intent(this, QuestionActivity.class)
                        .putExtra("id", 2));
            } break;
            case R.id.cardHistory: {
                startActivity(new Intent(this, HistoryActivity.class));
            } break;
        }
    }
}