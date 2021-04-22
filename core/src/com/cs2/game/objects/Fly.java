package com.cs2.game.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.cs2.game.loader.ResourseLoader;

public class Fly {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private Circle circle;

    public boolean isAlive;

    private float rotation;
    private int width;
    private float height;
    private float originalY;

    public Fly(float x, float y, int width, int height){
        this.width=width;
        this.height=height;
        this.originalY=y;



        circle=new Circle();
        isAlive=true;
        position=new Vector2(x,y);
        velocity=new Vector2(0,0);
        acceleration=new Vector2(0,460);

    }



    public boolean isFalling(){
        return velocity.y>110;
    }
    public boolean notFlap(){
        return velocity.y>70 || isAlive;
    }

    public void onClick(){

        if (isAlive) {
            velocity.y=-140;
            ResourseLoader.flap.play();
        }
    }
    public void update(float delta){
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y>200){
            velocity.y=200;
        }
        if (position.y<-13){
            position.y=-13;
            velocity.y=0;
        }

        position.add(velocity.cpy().scl(delta));
        circle.set(position.x+9, position.y+6,6.5f);

        if (velocity.y<0){
            rotation -=600*delta;

            if (rotation<-20){
                rotation=-20;
            }
        }
        if (isFalling()){
            rotation+=480*delta;
            if (rotation>90){
                rotation=90;
            }
        }
    }



    public void die(){
        isAlive=false;
        velocity.y=0;
    }
    public float getX(){
        return position.x;
    }
    public float getY(){
        return position.y;
    }
    public float getRotation() {
        return rotation;
    }

    public int getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Circle getCircle() {
        return circle;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void cling(){
        acceleration.y=0;
    }

    public void onRestart(int y) {
        rotation=0;
        position.y=y;
        velocity.x=0;
        velocity.y=0;
        acceleration.x=0;
        acceleration.y=460;
        isAlive=true;


    }

    public void updateReady(float runTime) {
        position.y=2*(float) Math.sin(7*runTime)+originalY;
    }
}
