package com.example.invadermustdie.domain.spells;

public class Invincible extends Spell {

    private boolean isInvincible;

    public Invincible (int cooldown, int length) {
        super(cooldown, length);
        isInvincible = false;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }
}
