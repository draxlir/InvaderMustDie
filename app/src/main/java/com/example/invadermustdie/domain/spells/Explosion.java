package com.example.invadermustdie.domain.spells;

public class Explosion extends Spell {

    private int posX;
    private int posY;

    public Explosion(int cooldown, int length, int posX, int posY) {
        super(cooldown, length);
        this.posX = posX;
        this.posY = posY;
    }

    public void castSpell() {
        if (cooldown == 0) {
            posX = 0; // player.getPosX()
            posY = 0; // player.getPosY()
            length = 1000/60; //milliseconds/draw per second
            cooldown = 20000/20; //milliseconds/update per second
        }
    }


}
