package com.example.invadermustdie.domain.spells;

import android.os.Handler;

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
            available = false;
            active = true;
            Handler cd = new Handler();
            cd.postDelayed(() -> available = true, cooldown);
            Handler le = new Handler();
            le.postDelayed(() -> active = false, length);
        }
    }
}
