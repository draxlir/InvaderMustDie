package com.example.invadermustdie.domain;

public class Score {
    private String date;
    private int score;
    private double multiplier;

    private static final int addingValue = 2;

    @Override
    public String toString() {
        return  date  + "  "+
                "score=" + score + "   "+
                "multiplier=" + multiplier
                ;
    }

    public Score(String date, int score, float multiplier) {
        this.date= date;
        this.score = score;
        this.multiplier = multiplier;
    }

    public void updateScore() {
        score += addingValue*multiplier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public void computeMultiplierFromSoundLevel(int amplitudeDb) {
        System.out.println("amplitude " + amplitudeDb);
        if (multiplier == 1) {
            if(amplitudeDb >= 70 && amplitudeDb <= 100) {
                multiplier = 0.1 * amplitudeDb -5;
            } else if (amplitudeDb >= 100) {
                multiplier = 5;
            }
            multiplier = (double) Math.round(multiplier * 100) / 100;
            System.out.println(multiplier);
        }
    }

}



