package com.example.invadermustdie.domain;

import android.os.Handler;

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

    public boolean getActive() {
        return active;
    }

    public boolean getAvailable() {
        return available;
    }

    public void castSpell() {
        if (available) {
            available = false;
            active = true;
            Handler cd = new Handler();
            cd.postDelayed(() -> available = true, cooldown);
            Handler le = new Handler();
            le.postDelayed(() -> active = false, length);
        }
    }
}
