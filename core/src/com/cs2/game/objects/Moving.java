package com.cs2.game.objects;

import com.badlogic.gdx.math.Vector2;

public class Moving {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isMovingLeft;

    public Moving(float x, float y, int width, int height, float movSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(movSpeed, 0);
        this.width = width;
        this.height = height;
        isMovingLeft = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        if (position.x + width < 0) {
            isMovingLeft = true;
        }
    }

    public void reset(float newX) {
        position.x = newX;
        isMovingLeft = false;
    }

    public void stop() {
        velocity.x = 0;
    }

    public boolean isScrolledLeft() {
        return isMovingLeft;
    }

    public float getTailX() {
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}


