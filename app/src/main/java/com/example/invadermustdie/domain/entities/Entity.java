package com.example.invadermustdie.domain.entities;

import android.graphics.Paint;

import com.example.invadermustdie.domain.Circle;

public abstract class Entity {

    protected Circle circle;
    protected float speed;
    protected Paint color = new Paint();

    public Entity(float x, float y, int radius) {
        this.circle = new Circle(radius);
        this.circle.setCenter(x, y);
    }

    public Circle getCircle() {
        return circle;
    }

    public float getSpeed() {
        return speed;
    }

    public Paint getColor() {
        return color;
    }

    public void setSpeed(float speed) { this.speed = speed; }
}
