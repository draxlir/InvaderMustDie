package com.example.invadermustdie;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private float speedX;
    private float speedY;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getSupportActionBar().hide();

        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER){
                gameView.setSpeedX(values[0]);
                gameView.setSpeedY(values[1]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //rien
    }
}