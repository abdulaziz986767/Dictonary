package com.example.dictonary.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

import com.example.dictonary.R;
import com.example.dictonary.adapters.VpAdapter;
import com.example.dictonary.db.Database;
import com.example.dictonary.db.Entity;
import com.example.dictonary.db.HistoryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements VpAdapter.OnClickNextListener {

    private ViewPager2 vp;
    private Database db;
    private List<Entity> list  = new ArrayList<>();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle("Questions");
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        vp = findViewById(R.id.vp);
        vp.setUserInputEnabled(false);

        db = Database.getInstance(this.getApplicationContext());

        id = getIntent().getIntExtra("id", 0);

        if (id == 1) {
            list = db.dao().getAllQuestions();
        }
        else if (id ==2) {
            List<HistoryModel> models = db.dao().getAllHistory();
            Collections.shuffle(models);

            for (int i = 0; i < 10; i++) {
                Entity e = new Entity(models.get(i).getEng(), models.get(i).getUzb());
                list.add(e);
            }
        }

        VpAdapter adapter = new VpAdapter(this, list);
        vp.setAdapter(adapter);
    }

    @Override
    public void setOnCLickNext(int position, boolean bool) {
        Toast.makeText(this, String.valueOf(bool), Toast.LENGTH_SHORT).show();

        if (id == 1) {
            if (bool) {
                Entity e = list.get(position);
                HistoryModel model = new HistoryModel(e.getEng(), e.getUzb());
                db.dao().insertHistory(model);
                db.dao().deleteQuestion(list.get(position));
            }
        }

        if (position != (list.size() - 1)) {
            vp.setCurrentItem(vp.getCurrentItem() + 1);
        }
        else {
            finish();
        }
    }

    @Override
    public void setOnClickPervious() {
        if (vp.getCurrentItem() > 0) {
            vp.setCurrentItem(vp.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}