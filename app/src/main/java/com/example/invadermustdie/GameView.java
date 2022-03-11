package com.example.invadermustdie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameDrawThread threadDraw = new GameDrawThread(getHolder(), this);
    private GameUpdateThread threadUpdate = new GameUpdateThread(getHolder(), this);

    private int startX = 0;
    private int startY = 0;
    private int speedX = 0;
    private int speedY = 0;

    private List<Pair<Integer, Integer>> enemies = new ArrayList<>();

    private Handler mHandlerEnemySpawn = new Handler();

    private Runnable mEnemySpawn= new Runnable() {
        public void run() {
            Random rnd = new Random();
            enemies.add(new Pair<>(rnd.nextInt(1000), rnd.nextInt(1000)));
            mHandlerEnemySpawn.postDelayed(mEnemySpawn, 1000);
        }
    };

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        mHandlerEnemySpawn.postDelayed(mEnemySpawn, 1000);
    }

    public void update() {
        speedX = (speedX + 20) % 500;
        speedY = (speedY + 20) % 500;

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        if(canvas != null) {
            drawPlayer(canvas);
            drawEnemies(canvas);
        }
    }

    public void drawPlayer(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(250, 0, 0));
        canvas.drawCircle(startX + speedX, startY + speedY, 50, paint);
    }

    public void drawEnemies(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(250, 0, 0));
        for(Pair<Integer, Integer> enemy : enemies) {
            canvas.drawRect(enemy.first, enemy.second, enemy.first+20, enemy.second+20, paint);
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
}
