package com.cs2.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.cs2.game.game.GameRender;
import com.cs2.game.game.GameWorld;
import com.cs2.game.ui.InputHandler;

public class GameScreen implements Screen {


    private GameWorld world;
    private GameRender renderer;

    private float runTime=0;

    public GameScreen() {

        float screenWidth= Gdx.graphics.getWidth();
        float screenHeight= Gdx.graphics.getHeight();
        float gameWidth=136;
        float gameHeight=screenHeight/(screenWidth/gameWidth);
        int midPointY=(int) (gameHeight/2);
        int midPointX=(int) (gameWidth/2);

        world=new GameWorld(midPointY, midPointX);
        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth/gameWidth, screenHeight/gameHeight));
        renderer=new GameRender(world, (int) gameHeight, midPointY, midPointX);
        world.setRenderer(renderer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        runTime+=delta;
        world.update(delta);
        renderer.render(delta,runTime);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
