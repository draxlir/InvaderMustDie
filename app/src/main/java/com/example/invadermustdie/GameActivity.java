package com.example.invadermustdie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.invadermustdie.domain.Constants;
import com.example.invadermustdie.domain.spells.Explosion;
import com.example.invadermustdie.domain.spells.Freeze;
import com.example.invadermustdie.domain.spells.Invincible;
import com.example.invadermustdie.domain.spells.Meteor;
import com.example.invadermustdie.services.AudioService;
import com.example.invadermustdie.utils.OnSwipeTouchListener;

import java.util.Objects;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private GameView gameView;
    private SensorManager sm = null;
    private final Invincible spellInvincible = new Invincible(Constants.INVINCIBLE_CD, Constants.INVINCIBLE_DURATION);
    private final Explosion spellExplosion = new Explosion(Constants.EXPLOSION_CD, Constants.EXPLOSION_DURATION);
    private final Freeze spellFreeze = new Freeze(Constants.FREEZE_CD, Constants.FREEZE_DURATION);
    private final Meteor spellMeteor = new Meteor(Constants.METEOR_CD, Constants.METEOR_DURATION);
    private AudioService audioService;

    private boolean permissionToRecordAccepted = false;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};

    private final Handler mHandler = new Handler();
    private final Runnable rTask = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                if(audioService.isRecording()) {
                    int amplitude = audioService.getSoundLevel();
                    int amplitudeDb = (int) (20 * Math.log10(Math.abs(amplitude)));
                    gameView.setSoundLevel(amplitudeDb);
                }
            });
            mHandler.postDelayed(this, Constants.AMPLITUDE_CHECK_PERIOD);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fileName = getExternalCacheDir().getAbsolutePath();
        fileName += Constants.AUDIO_FILE;

        audioService = new AudioService(fileName);

        ActivityCompat.requestPermissions(this, permissions, Constants.REQUEST_RECORD_AUDIO_PERMISSION);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Objects.requireNonNull(getSupportActionBar()).hide();

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        gameView = new GameView(this);
        setContentView(gameView);
        gameView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeUp() {
                spellInvincible.castSpell();
            }
            public void onSwipeRight() {
                spellFreeze.castSpell(gameView.getEnemies());
               }
            public void onSwipeLeft() {
                spellMeteor.castSpell();
            }
            public void onSwipeDown() {
                spellExplosion.castSpell(gameView.getPlayer().getX(), gameView.getPlayer().getY());
            }
        });

        audioService.startRecording();
        mHandler.postDelayed(rTask, Constants.AMPLITUDE_CHECK_PERIOD);
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
                gameView.setSpeedY(values[0] - Constants.ACCELEROMETER_OFFSET);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //nothing
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
        if (requestCode == Constants.REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted) finish();
    }

}