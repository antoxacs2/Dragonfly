package com.cs2.game.objects;

public class Grass extends Moving {
    public Grass(float x, float y, int width, int height, float movSpeed) {
        super(x, y, width, height, movSpeed);
    }

    public void onRestart(float x, float movSpeed) {
        position.x=x;
        velocity.x=movSpeed;
    }
}
