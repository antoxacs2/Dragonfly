package com.cs2.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cs2.game.FlyGame;
import com.cs2.game.loader.ResourseLoader;
import com.cs2.game.tools.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class SplashScreen implements Screen {

    private TweenManager manager;
    private SpriteBatch batch;
    private Sprite sprite;
    private FlyGame game;

    public SplashScreen(FlyGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        sprite=new Sprite(ResourseLoader.logo);
        sprite.setColor(1,1,1,0);

        float width= Gdx.graphics.getWidth();
        float height= Gdx.graphics.getHeight();
        float desireWidth=width*0.7f;
        float scale=desireWidth / sprite.getWidth();

        sprite.setSize(sprite.getWidth()*scale, sprite.getHeight()*scale);
        sprite.setPosition((width/2)-(sprite.getWidth()/2), (height/2)
        - (sprite.getHeight()/2));

        setupTween();
        batch=new SpriteBatch();

    }

    private void setupTween(){
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback callback=new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen());

            }
        };
        Tween.to(sprite, SpriteAccessor.ALPHA, 0.8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1,0.4f)
                .setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);

    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        batch.end();


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
