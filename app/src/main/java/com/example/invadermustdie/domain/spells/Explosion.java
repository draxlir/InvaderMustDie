package com.example.invadermustdie.domain.spells;

public class Explosion extends Spell {

    private float posX;
    private float posY;

    public Explosion(int cooldown, int length) {
        super(cooldown, length);
    }

    public void castSpell(float posX, float posY) {
        if (available) {
            this.posX = posX;
            this.posY = posY;
            super.castSpell();
        }
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }
}
