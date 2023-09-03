package com.github.pepek42.rain.enumeration;

public enum Difficulty {
    EASY(150f),
    HARD(500f),
    ;

    private final float dropSpeed;

    Difficulty(float dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public float getDropSpeed() {
        return dropSpeed;
    }
}
