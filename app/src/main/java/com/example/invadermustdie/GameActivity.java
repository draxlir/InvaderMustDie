package com.example.invadermustdie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.invadermustdie.domain.Constants;
import com.example.invadermustdie.domain.spells.Explosion;
import com.example.invadermustdie.domain.spells.Freeze;
import com.example.invadermustdie.domain.spells.Invincible;

import java.io.IOException;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private GameView gameView;
    private SensorManager sm = null;
    private Invincible spellInvincible = new Invincible(Constants.INVINCIBLE_CD, Constants.INVINCIBLE_DURATION);
    private Explosion spellExplosion = new Explosion(Constants.EXPLOSION_CD, Constants.EXPLOSION_DURATION);
    private Freeze spellFreeze = new Freeze(Constants.FREEZE_CD, Constants.FREEZE_DURATION);
    private AudioService audioService;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    private static String fileName = null;

    private Handler mHandler = new Handler();
    private Runnable rTask = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                if(audioService.isRecording()) {
                    int amplitude = audioService.getSoundLevel();
                    int amplitudeDb = (int) (20 * Math.log10(Math.abs(amplitude)));
                    gameView.setSoundLevel(amplitudeDb);
                }
            });
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";

        audioService = new AudioService(fileName);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getSupportActionBar().hide();

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        gameView = new GameView(this);
        setContentView(gameView);
        gameView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeUp() {
                Handler handler = new Handler();
                spellInvincible.castSpell();
            }
            public void onSwipeRight() {
                Handler handler = new Handler();
                spellFreeze.castSpell(gameView.getEnemies());
               }
            public void onSwipeLeft() {
                Toast.makeText(GameActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeDown() {
                Toast.makeText(GameActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                spellExplosion.castSpell(gameView.getPlayer().getX(), gameView.getPlayer().getY());

            }
        });

        audioService.startRecording();
        mHandler.postDelayed(rTask, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Sensor mLuminosity = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this, mLuminosity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.TYPE_LIGHT));
        audioService.stopRecording();
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER){
                gameView.setSpeedX(values[1]);
                gameView.setSpeedY(values[0] - 4.25f);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //rien
    }

    public Explosion getSpellExplosion() {
        return spellExplosion;
    }

    public Invincible getSpellInvincible() {
        return spellInvincible;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();
    }

}