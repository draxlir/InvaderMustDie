package com.example.invadermustdie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invadermustdie.domain.Score;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // hide top bar
        ListView listView = (ListView)findViewById(R.id.listView);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        //Meilleurs scores
        Score s1 = new Score(formatter.format(date), 34, 3);
        Score s2 = new Score(formatter.format(date), 39, 3);
        Score s3 = new Score(formatter.format(date), 48, 3);
        Score s4 = new Score(formatter.format(date), 39, 3);
        Score s5 = new Score(formatter.format(date), 48, 3);

        ArrayList<Score> history = new ArrayList<>();
        history.add(s1);
        history.add(s2);
        history.add(s3);
        history.add(s4);
        history.add(s5);
        Collections.sort(history, new Comparator<Score>() {
            public int compare(Score s1, Score s2) {
                return s2.getScore() - s1.getScore();
            }
        });
        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.row_history_layout, history);

        listView.setAdapter(adapter);
    }

    public void clickStart(View v){
        Toast.makeText(this,"ClickedButton", Toast.LENGTH_LONG).show(); // TODO Start Game
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}