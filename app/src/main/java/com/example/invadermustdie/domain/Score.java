package com.example.invadermustdie.domain;

public class Score {
    private String date;
    private int score;
    private int multiplier;

    private static final int addingValue = 10;

    @Override
    public String toString() {
        return  date  + "  "+
                "score=" + score + "   "+
                "multiplier=" + multiplier
                ;
    }

    public Score(String date, int score, int multiplier) {
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

    public void setScore(int score) {
        this.score = score;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}



