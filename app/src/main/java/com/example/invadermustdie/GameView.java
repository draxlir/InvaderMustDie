package com.example.invadermustdie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.invadermustdie.domain.Circle;
import com.example.invadermustdie.domain.Constants;
import com.example.invadermustdie.domain.Score;
import com.example.invadermustdie.domain.spells.Explosion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.invadermustdie.domain.entities.Enemy;
import com.example.invadermustdie.domain.entities.Player;
import com.example.invadermustdie.threads.GameDrawThread;
import com.example.invadermustdie.threads.GameUpdateThread;
import com.example.invadermustdie.utils.CirclesCollisionManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final GameDrawThread threadDraw = new GameDrawThread(getHolder(), this);
    private final GameUpdateThread threadUpdate = new GameUpdateThread(getHolder(), this);

    private final int SCREEN_WIDTH = this.getResources().getDisplayMetrics().widthPixels;
    private final int SCREEN_HEIGHT = this.getResources().getDisplayMetrics().heightPixels;

    private final GameActivity gameActivity;

    private float speedX = 0;
    private float speedY = 0;

    private int delaySpawn = 5050;

    private final List<Enemy> enemies = new ArrayList<>();
    private final Handler mHandlerEnemySpawn = new Handler();
    private final Player player = new Player((SCREEN_WIDTH / 2.0f), (SCREEN_HEIGHT / 2.0f), Constants.PLAYER_RADIUS);

    private final Score score = new Score(null, 0, 1);
    private final Context mContext;

    private boolean gameOver = false;

    private final Runnable mEnemySpawn= new Runnable() {

        @Override
        public void run() {
            Random rnd = new Random();
            double enemyX = rnd.nextInt(SCREEN_WIDTH);
            double enemyY = rnd.nextInt(SCREEN_HEIGHT);
            while (posInRadius(enemyX, enemyY, player.getX(), player.getY(), SCREEN_HEIGHT / 4.0)) {
                enemyX = rnd.nextInt(SCREEN_HEIGHT);
                enemyY = rnd.nextInt(SCREEN_WIDTH);
            }
            Enemy enemy = new Enemy((float) enemyX, (float) enemyY, Constants.ENEMY_RADIUS);
            if(gameActivity.getSpellFreeze().isActive()){
                enemy.setSpeed(enemy.getSpeed() / Constants.FREEZE_STRENGTH);
            }
            enemies.add(enemy);
            delaySpawn = delaySpawn - Constants.SPAWN_ACCELERATION;
            mHandlerEnemySpawn.postDelayed(mEnemySpawn, Math.max(delaySpawn, Constants.MIN_SPAWN_DELAY));
        }
    };

    public GameView(Context context) {
        super(context);
        this.mContext = context;
        this.gameActivity = (GameActivity) context;
        getHolder().addCallback(this);
        setFocusable(true);
        mHandlerEnemySpawn.postDelayed(mEnemySpawn, 2000);
    }

    public void update() {
        double newX = player.getX() + player.getSpeed() * speedX;
        double newY = player.getY() + player.getSpeed() * speedY;

        newX = (newX >= Constants.PLAYER_RADIUS / 2.0 && newX <= SCREEN_WIDTH - Constants.PLAYER_RADIUS / 2.0)
                ? newX
                : nearest(Constants.PLAYER_RADIUS / 2, SCREEN_WIDTH - Constants.PLAYER_RADIUS / 2, newX) ;
        newY = (newY >= Constants.PLAYER_RADIUS / 2.0 && newY <= SCREEN_HEIGHT - Constants.PLAYER_RADIUS / 2.0)
                ? newY
                : nearest(Constants.PLAYER_RADIUS / 2, SCREEN_HEIGHT - Constants.PLAYER_RADIUS / 2, newY) ;

        player.getCircle().setCenter((float) newX, (float) newY);

        score.updateScore();
        double multiplier = score.getMultiplier();
        if(multiplier > 1) {
            BigDecimal bd = new BigDecimal(multiplier);
            BigDecimal bd2 = new BigDecimal("0.01");
            score.setMultiplier((double) Math.round(bd.subtract(bd2).doubleValue()*100)/100);
        }

        for (Enemy enemy : enemies) {
            if (CirclesCollisionManager.isColliding(player.getCircle(), enemy.getCircle())) {
                if (gameActivity.getSpellInvincible().isActive()){
                    killEnemy(enemy);
                } else {
                    gameOver();
                }
            }
            if (gameActivity.getSpellExplosion().isActive()){
                if (CirclesCollisionManager.isColliding(gameActivity.getSpellExplosion().getCircle(), enemy.getCircle()) && gameActivity.getSpellExplosion().isActive()) {
                    killEnemy(enemy);
                }
            }

            if (gameActivity.getSpellMeteor().isActive()) {
                for (Circle meteor : gameActivity.getSpellMeteor().getMeteors()) {
                    if (CirclesCollisionManager.isColliding(meteor, enemy.getCircle())) {
                        killEnemy(enemy);
                    }
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        if (gameActivity.getSpellFreeze().isActive()) {
            canvas.drawColor(Color.CYAN);
        }
        if(canvas != null) {
            drawSpells(canvas);
            drawPlayer(canvas);
            drawEnemies(canvas);
            drawScoreAndMultiplier(canvas);
        }
    }

    public void gameOver(){
        if(!gameOver){
            gameOver = true;
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://invadermustdie-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("scores");
            myRef.push().setValue(score);
            SharedPreferences sharedPref = this.mContext.getSharedPreferences("settings",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("score", score.getScore());
            editor.apply();
            Intent intent = new Intent(getContext(), GameOverActivity.class);
            mContext.startActivity(intent);
        }
    }

    public void drawPlayer(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0, 255, 0));
        if (gameActivity.getSpellInvincible().isActive()) {
            paint.setColor(Color.rgb(255, 150, 150));
        }
        canvas.drawCircle(player.getX(), player.getY(), Constants.PLAYER_RADIUS, paint);

    }

    public void drawEnemies(Canvas canvas) {
        for(Enemy enemy : enemies) {
            enemy.updatePos(player.getX(), player.getY());
            canvas.drawCircle(enemy.getCircle().getCenter().x, enemy.getCircle().getCenter().y, Constants.ENEMY_RADIUS, enemy.getColor());
        }
    }

    public void drawScoreAndMultiplier(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255,0,0));
        paint.setTextSize(Constants.SCORE_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.RIGHT);

        canvas.drawText(score.getScore()+" pts", SCREEN_WIDTH-20, 60, paint);
        canvas.drawText("x"+ score.getMultiplier(), SCREEN_WIDTH-20, 120, paint);
    }

    public void drawSpells(Canvas canvas) {
        if (gameActivity.getSpellExplosion().isActive()) {
            Paint paint = new Paint();
            paint.setColor(Color.rgb(255, 255, 0));
            canvas.drawCircle(gameActivity.getSpellExplosion().getX(), gameActivity.getSpellExplosion().getY(), Constants.EXPLOSION_RADIUS, paint);
        }
        if (gameActivity.getSpellMeteor().isActive()) {
            Paint paint = new Paint();
            paint.setColor(Color.rgb(120,120,40));
            for (Circle meteor : gameActivity.getSpellMeteor().getMeteors()) {
                canvas.drawCircle(meteor.getCenter().x, meteor.getCenter().y, Constants.METEOR_RADIUS, paint);
                meteor.setCenter(meteor.getCenter().x - Constants.METEOR_SPEED, meteor.getCenter().y + Constants.METEOR_SPEED);
            }
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        threadDraw.setRunning(true);
        threadDraw.start();
        threadUpdate.setRunning(true);
        threadUpdate.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                threadDraw.setRunning(false);
                threadDraw.join();
                threadUpdate.setRunning(false);
                threadUpdate.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    private int nearest(int minus, int plus, double pick) {
        return Math.abs(minus - pick) < Math.abs(plus - pick) ? minus : plus;
    }

    private boolean posInRadius(double posXtoCheck, double posYtoCheck, double posXRadius, double posYRadius, double radius) {
        double distance = Math.sqrt(Math.pow(posXtoCheck-posXRadius, 2) + Math.pow(posYtoCheck-posYRadius, 2));
        return distance < radius;
    }

    private void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
        score.addScore(50);
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void setSoundLevel(int amplitudeDb) {
        score.computeMultiplierFromSoundLevel(amplitudeDb);
    }


    public Player getPlayer() {
        return this.player;
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }


    public int getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    public int getSCREEN_HEIGHT() {
        return SCREEN_HEIGHT;
    }
}
