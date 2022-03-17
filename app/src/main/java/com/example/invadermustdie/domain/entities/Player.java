package com.example.invadermustdie.domain.entities;

import android.graphics.Color;

import com.example.invadermustdie.domain.Constants;

public class Player extends Entity{

    public Player(float x, float y, int radius) {
        super(x, y, radius);
        this.color.setColor(Color.rgb(0, 255, 0));
        this.speed = Constants.REGULAR_PLAYER_SPEED;
    }

    public float getX() {
        return circle.getCenter().x;
    }

    public float getY() {
        return circle.getCenter().y;
    }


}
