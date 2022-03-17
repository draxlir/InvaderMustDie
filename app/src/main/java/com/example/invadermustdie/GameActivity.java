package com.example.invadermustdie;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.invadermustdie.domain.Constants;
import com.example.invadermustdie.domain.spells.Explosion;
import com.example.invadermustdie.domain.spells.Freeze;
import com.example.invadermustdie.domain.spells.Invincible;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private GameView gameView;
    private SensorManager sm = null;
    private Invincible spellInvincible = new Invincible(Constants.INVINCIBLE_CD, Constants.INVINCIBLE_DURATION);
    private Explosion spellExplosion = new Explosion(Constants.EXPLOSION_CD, Constants.EXPLOSION_DURATION);
    private Freeze spellFreeze = new Freeze(Constants.FREEZE_CD, Constants.FREEZE_DURATION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                spellInvincible.setInvincible(true);
                handler.postDelayed(() -> spellInvincible.setInvincible(false), spellInvincible.getLength());
            }
            public void onSwipeRight() {
                Handler handler = new Handler();
                handler.postDelayed(() -> gameView.getEnemies().forEach((enemy) -> enemy.setSpeed(enemy.getSpeed() / spellFreeze.getFreezeStrength())), spellFreeze.getLength());
            }
            public void onSwipeLeft() {
                Toast.makeText(GameActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeDown() {
                Toast.makeText(GameActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                spellExplosion.castSpell(gameView.getPlayer().getX(), gameView.getPlayer().getY());

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
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

    public boolean getIsInvincible() {
        return spellInvincible.isInvincible();
    }
}