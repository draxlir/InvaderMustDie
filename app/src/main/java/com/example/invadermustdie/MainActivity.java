package com.example.invadermustdie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invadermustdie.domain.Score;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); // hide top bar
        setContentView(R.layout.activity_main);
        context = this;
        ListView listView = (ListView)findViewById(R.id.listView);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://invadermustdie-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("scores");
        ArrayList<Score> history = new ArrayList<>();

        DatabaseReference scoreQuery = myRef;
        scoreQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("datachange");
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    history.add(singleSnapshot.getValue(Score.class));
                }
                Collections.sort(history, new Comparator<Score>() {
                    public int compare(Score s1, Score s2) {
                        return s2.getScore() - s1.getScore();
                    }
                });
                // Top 100 scores
                if(history.size() > 100){
                    history.subList(0,99);
                }
                PersonListAdapter adapter = new PersonListAdapter(context, R.layout.row_history_layout, history);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                 Log.e("error database", "onCancelled", databaseError.toException());
            }
        });


    }

    public void clickStart(View v){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}