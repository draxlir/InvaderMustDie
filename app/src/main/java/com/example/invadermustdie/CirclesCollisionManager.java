package com.example.invadermustdie;

import com.example.invadermustdie.domain.Circle;

public class CirclesCollisionManager {

    private static boolean aabbCollision(Circle c1, Circle c2){
        return (c1.getCenter().x + c1.getRadius() + c2.getRadius() > c2.getCenter().x
                && c1.getCenter().x < c2.getCenter().x + c1.getRadius() + c2.getRadius()
                && c1.getCenter().y + c1.getRadius() + c2.getRadius() > c2.getCenter().y
                && c1.getCenter().y < c2.getCenter().y + c1.getRadius() + c2.getRadius());
    }

    public static boolean isColliding(Circle c1, Circle c2) {
        if (aabbCollision(c1, c2)) {
            double distance = Math.sqrt(
                    Math.pow(c1.getCenter().x - c2.getCenter().x, 2) +
                    Math.pow(c1.getCenter().y - c2.getCenter().y, 2));
            return (distance < c1.getRadius() + c2.getRadius());

        }
        return false;
    }
}
