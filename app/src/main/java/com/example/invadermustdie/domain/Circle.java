package com.example.invadermustdie.domain;

import android.graphics.PointF;

public class Circle {

    private final PointF center = new PointF();
    private final int radius;

    public Circle (int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public PointF getCenter() {
        return center;
    }

    public void setCenter(float x, float y) {
        this.center.set(x, y);
    }
}
