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

import com.example.invadermustdie.domain.Explosion;
import com.example.invadermustdie.domain.Invincible;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private GameView gameView;
    private SensorManager sm = null;
    private Invincible spellInvincible = new Invincible(20000, 5000);
    private Explosion spellExplosion = new Explosion(25000, 1000);

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
                Toast.makeText(GameActivity.this, getIsInvincible()+"", Toast.LENGTH_SHORT).show();
                handler.postDelayed(() -> spellInvincible.setInvincible(false), spellInvincible.getLength());
            }
            public void onSwipeRight() {
                Toast.makeText(GameActivity.this, "right", Toast.LENGTH_SHORT).show();
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

    public Invincible getSpellInvincible() {
        return this.spellInvincible;
    }

    public Explosion getSpellExplosion() {
        return this.spellExplosion;
    }

    public boolean getIsInvincible() {
        return this.spellInvincible.isInvincible();
    }
}