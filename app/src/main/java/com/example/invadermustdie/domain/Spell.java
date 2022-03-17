package com.example.invadermustdie.domain;

public abstract class Spell {

    protected int cooldown;
    protected int length;

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
