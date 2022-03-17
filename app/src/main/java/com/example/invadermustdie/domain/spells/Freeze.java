package com.example.invadermustdie.domain.spells;

import com.example.invadermustdie.domain.entities.Enemy;

import java.util.List;

public class Freeze extends Spell{

    private int freezeStrength = 2;

    public Freeze(int cooldown, int length) {
        super(cooldown, length);
    }

    public void castSpell(List<Enemy> enemies) {
        enemies.forEach((enemy) -> enemy.setSpeed(enemy.getSpeed() / freezeStrength));
        super.castSpell();
    }
}
