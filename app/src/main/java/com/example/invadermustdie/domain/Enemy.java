package com.example.invadermustdie.domain;

import android.graphics.Color;
import android.graphics.Paint;

public class Enemy {

    private float posX;
    private float posY;
    private float speed;
    private double lifetime;
    private Paint color = new Paint();

    public Enemy(float x, float y) {
        this.posX = x;
        this.posY = y;
        this.speed = 3;
        this.lifetime = 0;
        this.color.setColor(Color.rgb(255,0,0));
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public Paint getColor() {
        return color;
    }

    public void updatePos(double playerX, double playerY) {
        if (posX < playerX) {
            posX = posX+speed;
        }
        if (posX > playerX) {
            posX = posX-speed;
        }
        if (posY < playerY) {
            posY = posY+speed;
        }
        if (posY > playerY) {
            posY = posY-speed;
        }
        lifetime++;
        // if lifetime > valeur, then speed increase
    }
}
