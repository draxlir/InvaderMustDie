package com.example.invadermustdie.domain.spells;

import com.example.invadermustdie.domain.Circle;
import com.example.invadermustdie.domain.Constants;

public class Explosion extends Spell {

    private Circle circle;

    public Explosion(int cooldown, int length) {
        super(cooldown, length);
    }

    public void castSpell(float posX, float posY) {
        if (available) {
            circle = new Circle(Constants.EXPLOSION_RADIUS);
            circle.setCenter(posX, posY);
            super.castSpell();
        }
    }

    public float getX() {
        return circle.getCenter().x;
    }

    public float getY() { return circle.getCenter().y; }

    public Circle getCircle() { return circle; }
}
