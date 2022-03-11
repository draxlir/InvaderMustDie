package com.example.invadermustdie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameDrawThread threadDraw = new GameDrawThread(getHolder(), this);
    private GameUpdateThread threadUpdate = new GameUpdateThread(getHolder(), this);

    private int startX = 0;
    private int startY = 0;
    private int speedX = 0;
    private int speedY = 0;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    public void update() {
        speedX = (speedX + 20) % 500;
        speedY = (speedY + 20) % 500;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas != null) {
            drawPlayer(canvas);
        }
    }

    public void drawPlayer(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(250, 0, 0));
        canvas.drawCircle(startX + speedX, startY + speedY, 50, paint);
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
