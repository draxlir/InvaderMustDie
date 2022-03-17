package com.example.invadermustdie.domain.entities;

import android.graphics.Color;

import com.example.invadermustdie.domain.Constants;

public class Enemy extends Entity{

    private double lifetime;
    private int opacity = 255;
    // imageView.setAlpha(yourValue) quand on utilisera des icones

    public Enemy(float x, float y, int radius) {
        super(x, y, radius);
        this.speed = Constants.REGULAR_ENEMY_SPEED;
        this.lifetime = 0;
        this.color.setColor(Color.argb(opacity, 255, 0, 0));
    }

    public void updatePos(double playerX, double playerY) {
        float newX = 0;
        float newY = 0;
        if (this.circle.getCenter().x < playerX) {
            newX = this.circle.getCenter().x + speed;
        }
        if (this.circle.getCenter().x > playerX) {
            newX = this.circle.getCenter().x - speed;
        }
        if (this.circle.getCenter().y < playerY) {
            newY = this.circle.getCenter().y + speed;
        }
        if (this.circle.getCenter().y > playerY) {
            newY = this.circle.getCenter().y - speed;
        }
        circle.setCenter(newX, newY);
        lifetime++;
        // if lifetime > valeur, then speed increase
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public void updateColor() {
        this.color.setColor(Color.argb(opacity, 255, 0, 0));
    }
}
