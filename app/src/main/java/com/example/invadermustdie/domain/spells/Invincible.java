package com.example.invadermustdie.domain.spells;

public class Invincible extends Spell {


    public Invincible (int cooldown, int length) {
        super(cooldown, length);
    }

    public void castSpell() {
        if (available) {
            super.castSpell();
        }
    }
}
