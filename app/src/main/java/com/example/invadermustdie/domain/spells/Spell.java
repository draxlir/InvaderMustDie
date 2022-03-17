package com.example.invadermustdie.domain.spells;

public abstract class Spell {

    protected int cooldown;
    protected int length;
    protected boolean available = true;
    protected boolean active = false;

    public Spell(int cooldown, int length) {
        this.cooldown = cooldown;
        this.length = length;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getLength() {
        return length;
    }
}
