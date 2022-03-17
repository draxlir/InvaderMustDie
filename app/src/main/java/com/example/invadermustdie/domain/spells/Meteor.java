package com.example.invadermustdie.domain.spells;

import com.example.invadermustdie.domain.Circle;
import com.example.invadermustdie.domain.Constants;

import java.util.ArrayList;
import java.util.List;

public class Meteor extends Spell {

    public Meteor(int cooldown, int length) { super(cooldown, length); }

    private List<Circle> meteors = new ArrayList<>();

    public void castSpell(int height, int width) {
        if (available) {
            meteors.clear();
            super.castSpell();
            for (int i=0; i<5; i++){
                meteors.add(new Circle(Constants.METEOR_RADIUS));
            }
            meteors.get(0).setCenter(width/2, -100);
            meteors.get(1).setCenter((width/2)*1.3f, -500);
            meteors.get(2).setCenter((width/2)*1.7f, -300);
            meteors.get(3).setCenter(width+100, -200);
            meteors.get(4).setCenter(width+200, 0.4f * height);
        }
    }

    public List<Circle> getMeteors() { return meteors; }
}
