package com.example.invadermustdie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        SharedPreferences sharedPrefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        int score = sharedPrefs.getInt("score", 0);
        System.out.println(score);

        setContentView(R.layout.activity_game_over);
        TextView tw_score;
        tw_score = (TextView) findViewById(R.id.tw_score);
        tw_score.setText(String.valueOf(score));
    }

    public void clickPlayAgain(View v){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void clickMenu(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
