package com.cs2.game.objects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Web extends Moving {


    private Random r;
    private Rectangle spider,webUp, webDown;

    public static final int GAP=45;
    private boolean isScored=false;
private float groundY;

    public Web(float x, float y, int width, int height, float movSpeed, float groundY) {
        super(x, y, width, height, movSpeed);
        r=new Random();
        spider=new Rectangle();
        webUp=new Rectangle();
        webDown=new Rectangle();
        this.groundY=groundY;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        webUp.set(position.x,position.y,width,height);
        webDown.set(position.x,position.y+height+GAP,width,groundY-(position.y+height+GAP));
        spider.set(position.x-(34-width)/2,position.y+height-14,34,24);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height=r.nextInt(90)+15;
        isScored=false;
    }
    public boolean collides(Fly fly){
        if (position.x<fly.getX()+fly.getWidth()){
            return (Intersector.overlaps(fly.getCircle(), webUp)
                    || Intersector.overlaps(fly.getCircle(),webDown)
                    || Intersector.overlaps(fly.getCircle(), spider));
        }
        return false;
    }

    public boolean isScored() {
        return isScored;
    }
    public void setScored(boolean b) {
        isScored = b;
    }

    public void onRestart(float x, float movSpeed) {
        velocity.x=movSpeed;
        reset(x);
    }
}
