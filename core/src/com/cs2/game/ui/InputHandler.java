package com.cs2.game.ui;

import com.badlogic.gdx.InputProcessor;
import com.cs2.game.game.GameWorld;
import com.cs2.game.loader.ResourseLoader;
import com.cs2.game.objects.Fly;

import java.util.ArrayList;

public class InputHandler implements InputProcessor {

    private Fly myFly;
    private ArrayList<PlayButton> menuButtons;
    private PlayButton playButton;
    private GameWorld myWorld;
    private float scaleFactorX;
    private float getScaleFactorY;

    public InputHandler(GameWorld myWorld, float scaleFactorX, float getScaleFactorY) {
        this.myWorld = myWorld;
        this.scaleFactorX = scaleFactorX;
        this.getScaleFactorY = getScaleFactorY;
        myFly=myWorld.getFly();

        int midPointX=myWorld.getMidPointX();
        int midPointY=myWorld.getMidPointY();

        menuButtons=new ArrayList<PlayButton>();
        playButton=new PlayButton(midPointX-14.5f,
                midPointY+10,29,29, ResourseLoader.playButtonUp,
                ResourseLoader.playButtonDown);
        menuButtons.add(playButton);
    }



    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX=scaleX(screenX);
        screenY=scaleY(screenY);

        if (myWorld.isMenu()){
            playButton.isTouchDown(screenX,screenY);
        }else if (myWorld.isReady()){
            myWorld.start();
            myFly.onClick();
        }else if (myWorld.isRunning()){
            myFly.onClick();
        }
        if (myWorld.isGameOver()||myWorld.isHighScore()){
            myWorld.restart();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX=scaleX(screenX);
        screenY=scaleY(screenY);

        if (myWorld.isMenu()){
            if (playButton.isTouchUp(screenX,screenY)){
                myWorld.ready();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    private int scaleX(int screenX){
        return (int)(screenX/scaleFactorX);
    }
    private int scaleY(int screenY){
        return (int)(screenY/getScaleFactorY);
    }

    public ArrayList<PlayButton> getMenuButtons() {
        return menuButtons;
    }
}
