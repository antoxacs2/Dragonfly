package com.cs2.game.objects;

import com.cs2.game.game.GameWorld;
import com.cs2.game.loader.ResourseLoader;

public class MovHandler {
    private Grass frontGrass, backGrass;
    private Web web1, web2, web3;
    public static final int MOV_SPEED=-59;
    public static final int WEB_GAP=60;

    private GameWorld gameWorld;

    public MovHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontGrass=new Grass(0, yPos, 143,11,MOV_SPEED);
        backGrass=new Grass(frontGrass.getTailX(), yPos, 143,11,MOV_SPEED);

        web1=new Web(210,0,22,60,MOV_SPEED,yPos);
        web2=new Web(web1.getTailX()+WEB_GAP,0,22,70, MOV_SPEED, yPos);
        web3=new Web(web2.getTailX()+WEB_GAP,0,22,60,MOV_SPEED,yPos);

    }
    public void update(float delta) {

        frontGrass.update(delta);
        backGrass.update(delta);
        web1.update(delta);
        web2.update(delta);
        web3.update(delta);


        if (web1.isScrolledLeft()) {
            web1.reset(web3.getTailX() + WEB_GAP);
        } else if (web2.isScrolledLeft()) {
            web2.reset(web1.getTailX() + WEB_GAP);

        } else if (web3.isScrolledLeft()) {
            web3.reset(web2.getTailX() + WEB_GAP);
        }

        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());

        }
    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Web getWeb1() {
        return web1;
    }

    public Web getWeb2() {
        return web2;
    }

    public Web getWeb3() {
        return web3;
    }
    public void stop(){
        frontGrass.stop();
        backGrass.stop();
        web1.stop();
        web2.stop();
        web3.stop();
    }
    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }
    public boolean collides(Fly fly) {
        if (!web1.isScored()
                && web1.getX() + (web1.getWidth() / 2) < fly.getX()
                + fly.getWidth()) {
            addScore(1);
            web1.setScored(true);
            ResourseLoader.coin.play();
        } else if (!web2.isScored()
                && web2.getX() + (web2.getWidth() / 2) < fly.getX()
                + fly.getWidth()) {
            addScore(1);
            web2.setScored(true);
            ResourseLoader.coin.play();

        } else if (!web3.isScored()
                && web3.getX() + (web3.getWidth() / 2) < fly.getX()
                + fly.getWidth()) {
            addScore(1);
            web3.setScored(true);
            ResourseLoader.coin.play();
        }
        return (web1.collides(fly)||web2.collides(fly)||web3.collides(fly));
    }

    public void onRestart() {
        frontGrass.onRestart(0, MOV_SPEED);
        backGrass.onRestart(frontGrass.getTailX(),MOV_SPEED);
        web1.onRestart(210,MOV_SPEED);
        web2.onRestart(web1.getTailX()+WEB_GAP,MOV_SPEED);
        web3.onRestart(web2.getTailX()+WEB_GAP,MOV_SPEED);
    }

    public void updateReady(float delta) {
        frontGrass.update(delta);
        backGrass.update(delta);

        if (frontGrass.isScrolledLeft()){
            frontGrass.reset(backGrass.getTailX());
        }else if (backGrass.isScrolledLeft()){
            backGrass.reset(frontGrass.getTailX());
        }
    }
}
