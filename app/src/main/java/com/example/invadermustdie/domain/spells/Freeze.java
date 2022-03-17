package com.example.invadermustdie.domain.spells;

public class Freeze extends Spell{

    private int freezeStrength = 2;

    public Freeze(int cooldown, int length) {
        super(cooldown, length);
    }

    public int getFreezeStrength() {
        return freezeStrength;
    }
}
