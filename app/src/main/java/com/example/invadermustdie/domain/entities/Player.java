package com.example.invadermustdie.domain.entities;

import android.graphics.Color;

public class Player extends Entity{

    public Player(float x, float y, int radius) {
        super(x, y, radius);
        this.color.setColor(Color.rgb(0, 255, 0));
        this.speed = 5;
    }


}
