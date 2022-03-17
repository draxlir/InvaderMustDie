package com.example.invadermustdie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.invadermustdie.domain.Enemy;
import com.example.invadermustdie.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameDrawThread threadDraw = new GameDrawThread(getHolder(), this);
    private GameUpdateThread threadUpdate = new GameUpdateThread(getHolder(), this);

    private final int SCREEN_WIDTH = this.getResources().getDisplayMetrics().widthPixels;
    private final int SCREEN_HEIGHT = this.getResources().getDisplayMetrics().heightPixels;

    private final int PLAYER_RADIUS = 30;
    private final int ENEMY_RADIUS = 20;

    private double posX = SCREEN_WIDTH / 2;
    private double posY = SCREEN_HEIGHT / 2;
    private float speedX = 0;
    private float speedY = 0;
    private int delaySpawn = 5050;

    private List<Enemy> enemies = new ArrayList<>();
    private Handler mHandlerEnemySpawn = new Handler();
    private Player player = new Player((float) posX, (float) posY, PLAYER_RADIUS);

    private int score = 0;
    private int multiplier = 1;

    private Runnable mEnemySpawn= new Runnable() {
        public void run() {
            Random rnd = new Random();
            double enemyX = rnd.nextInt(SCREEN_WIDTH);
            double enemyY = rnd.nextInt(SCREEN_HEIGHT);
            while (posInRadius(enemyX, enemyY, posX, posY, SCREEN_HEIGHT/4)) {
                enemyX = rnd.nextInt(SCREEN_HEIGHT);
                enemyY = rnd.nextInt(SCREEN_WIDTH);
            }
            enemies.add(new Enemy((float) enemyX, (float) enemyY, ENEMY_RADIUS));
            delaySpawn = delaySpawn - 50;
            mHandlerEnemySpawn.postDelayed(mEnemySpawn, Math.max(delaySpawn, 1000));
        }
    };

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        mHandlerEnemySpawn.postDelayed(mEnemySpawn, 2000);
    }

    public void update() {
        double newX = posX + player.getSpeed() * speedX;
        double newY = posY + player.getSpeed() * speedY;

        posX = (newX >= PLAYER_RADIUS / 2 && newX <= SCREEN_WIDTH - PLAYER_RADIUS / 2)
                ? newX
                : nearest(PLAYER_RADIUS / 2, SCREEN_WIDTH - PLAYER_RADIUS / 2, newX) ;
        posY = (newY >= PLAYER_RADIUS / 2 && newY <= SCREEN_HEIGHT - PLAYER_RADIUS / 2)
                ? newY
                : nearest(PLAYER_RADIUS / 2, SCREEN_HEIGHT - PLAYER_RADIUS / 2, newY) ;

        player.getCircle().setCenter((float) posX, (float) posY);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        if(canvas != null) {
            drawPlayer(canvas);
            drawEnemies(canvas);
            drawScoreAndMultiplier(canvas);
        }
        for (Enemy enemy : enemies) {
            if (CirclesCollisionManager.isColliding(player.getCircle(), enemy.getCircle())) {
                GameActivity gameActivity = (GameActivity) getContext();
                System.out.println(gameActivity.getIsInvincible());
                if (gameActivity.getIsInvincible()){
                    //add score
                    enemies.remove(enemy);
                    System.out.println("Mange ta mere");

                } else {
                    //afficher gameover
                    System.out.println("Gameover");
                }
            }
        }
    }

    public void drawPlayer(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0, 255, 0));
        canvas.drawCircle((float) posX, (float) posY, PLAYER_RADIUS, paint);

    }

    public void drawEnemies(Canvas canvas) {
        for(Enemy enemy : enemies) {
            enemy.updatePos(posX, posY);
            canvas.drawCircle(enemy.getCircle().getCenter().x, enemy.getCircle().getCenter().y, ENEMY_RADIUS, enemy.getColor());
        }
    }

    public void drawScoreAndMultiplier(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0,0,0));
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.RIGHT);

        canvas.drawText(score+" pts", SCREEN_WIDTH-150, 60, paint);
        canvas.drawText("x"+multiplier, SCREEN_WIDTH-20, 60, paint);
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

    private static int nearest(int minus, int plus, double pick) {
        return Math.abs(minus - pick) < Math.abs(plus - pick) ? minus : plus;
    }

    private static boolean posInRadius(double posXtoCheck, double posYtoCheck, double posXRadius, double posYRadius, double radius) {
        double distance = Math.sqrt(Math.pow(posXtoCheck-posXRadius,2) + Math.pow(posYtoCheck-posYRadius,2));
        return distance < radius;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
