package com.example.invadermustdie;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.invadermustdie.GameView;

public class GameUpdateThread extends Thread{

    private boolean running;

    private SurfaceHolder surfaceHolder;
    private GameView gameView;

    private Handler mHandler = new Handler();

    public GameUpdateThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        if (running) {
            try {
                synchronized(surfaceHolder) {
                    this.gameView.update();
                }
            } catch (Exception e) {}
        }
        mHandler.postDelayed(this, 50);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
