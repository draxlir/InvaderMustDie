package com.example.invadermustdie.domain;

public final class Constants {


    private Constants(){

    }

    public static final int PLAYER_RADIUS = 30;
    public static final int ENEMY_RADIUS = 20;

    public static final int FREEZE_CD = 15000;
    public static final int FREEZE_DURATION = 5000;
    public static final int FREEZE_STRENGTH = 2;

    public static final int EXPLOSION_CD = 25000;
    public static final int EXPLOSION_DURATION = 1000;
    public static final int EXPLOSION_RADIUS = 300;

    public static final int INVINCIBLE_CD = 30000;
    public static final int INVINCIBLE_DURATION = 5000;

    public static final int METEOR_CD = 30000;
    public static final int METEOR_DURATION = 7000;
    public static final int METEOR_RADIUS = 100;
    public static final int METEOR_SPEED = 12;

    public static final int SPAWN_ACCELERATION = 50;
    public static final int MIN_SPAWN_DELAY = 200;

    public static final int SCORE_TEXT_SIZE = 50;

    public static final String AUDIO_FILE = "/audiorecordtest.3gp";

    public static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    public static final int AMPLITUDE_CHECK_PERIOD = 1000;

    public static final float ACCELEROMETER_OFFSET = 4.25f;

    public static final int REGULAR_ENEMY_SPEED = 3;
    public static final int REGULAR_PLAYER_SPEED = 5;

}
