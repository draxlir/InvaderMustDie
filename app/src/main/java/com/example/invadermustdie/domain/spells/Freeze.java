package com.example.invadermustdie.domain.spells;

import android.os.Handler;

import com.example.invadermustdie.domain.Constants;
import com.example.invadermustdie.domain.entities.Enemy;

import java.util.List;

public class Freeze extends Spell{

    public Freeze(int cooldown, int length) {
        super(cooldown, length);
    }

    public void castSpell(List<Enemy> enemies) {
        if(available){
            enemies.forEach((enemy) -> enemy.setSpeed(enemy.getSpeed() / Constants.FREEZE_STRENGTH));
            super.castSpell();
            Handler handle = new Handler();
            handle.postDelayed(() -> enemies.forEach((enemy) -> enemy.setSpeed(enemy.getSpeed() * Constants.FREEZE_STRENGTH)), length);
        }
    }
}
