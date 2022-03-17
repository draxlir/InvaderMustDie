package com.example.invadermustdie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.invadermustdie.domain.Score;
import com.example.invadermustdie.utils.PersonListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide top bar
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://invadermustdie-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("message");



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
        history.sort((Score a, Score b) -> b.getScore() - a.getScore());

        myRef.setValue(history);

        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.row_history_layout, history);

        listView.setAdapter(adapter);
    }

    public void clickStart(View v){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}