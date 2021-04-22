package com.cs2.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cs2.game.loader.ResourseLoader;
import com.cs2.game.objects.Fly;
import com.cs2.game.objects.Grass;
import com.cs2.game.objects.MovHandler;
import com.cs2.game.objects.Web;
import com.cs2.game.tools.Value;
import com.cs2.game.tools.ValueAccessor;
import com.cs2.game.ui.InputHandler;
import com.cs2.game.ui.PlayButton;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;


public class GameRender {


    private int midPointY;
    private int midPointX;

    private GameWorld myWorld;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batch;

    private Fly myFly;
    private MovHandler movHandler;
    private Grass frontGrass, backGrass;
    private Web web1,web2,web3;


    private Sprite background, grass, flyMid, spider, webUp, webDown, ready,
            flyLogo, gameOver, highScore, scoreboard, starOn, starOff, retry;
    private Animation<TextureRegion> flyAnimation;
    private Music music;

    private TweenManager manager;
    private Value alpha=new Value();
    private Color transitionColor;


    private ArrayList<PlayButton> menuButtons;


    public GameRender(GameWorld world, int gameHeight, int midPointY, int midPointX) {
        myWorld = world;

        this.midPointX = midPointX;
        this.midPointY = midPointY;
        this.menuButtons=((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, gameHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initAssets();
        transitionColor=new Color();
        prepareTransition(255,255,255,0.5f);
    }

    public void render(float delta, float runTime){



        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(186 / 255.0f, 224 / 255.0f, 213 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        shapeRenderer.setColor(167 / 255.0f, 211 / 255.0f, 152 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        shapeRenderer.setColor(75 / 255.0f, 136 / 255.0f, 178 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 53);

        shapeRenderer.end();

        batch.begin();
        batch.disableBlending();

        batch.draw(background, 0, midPointY + 23, 136, 43);

        batch.enableBlending();
        drawGrass();
        drawWebs();
        drawSpiders();
        if (myWorld.isRunning()){
            drawFly(runTime);
            drawScore();
        }else if (myWorld.isReady()){
            drawFly(runTime);
            drawReady();
        }else if (myWorld.isMenu()){
            drawFlyCentered(runTime);
            drawMenuUI();
        }else if (myWorld.isGameOver()){
            drawScoreBoard();
            drawFly(runTime);
            drawGameOver();
            drawRetry();
        }else if (myWorld.isHighScore()){
            drawScoreBoard();
            drawFly(runTime);
            drawHighScore();
            drawRetry();
        }


        batch.end();
        drawTransition(delta);
        if (myFly.isAlive()){
            music.play();
            music.isLooping();
        } else {
            music.stop();
        }
    }
    private void initAssets() {
        background = ResourseLoader.background;
        grass = ResourseLoader.grass;
        flyAnimation = ResourseLoader.flyAnimation;
        flyMid = ResourseLoader.fly2;
        spider = ResourseLoader.spider;
        webUp = ResourseLoader.webUp;
        webDown = ResourseLoader.webDown;
        ready = ResourseLoader.ready;
        flyLogo = ResourseLoader.flyAndSpiders;
        gameOver = ResourseLoader.gameOver;
        highScore = ResourseLoader.highScore;
        scoreboard = ResourseLoader.scoreboard;
        retry = ResourseLoader.retry;
        starOn = ResourseLoader.starOn;
        starOff = ResourseLoader.starOff;
        music = ResourseLoader.fly;
    }

    private void initGameObjects() {
        myFly = myWorld.getFly();
        movHandler=myWorld.getMovHandler();
        frontGrass=movHandler.getFrontGrass();
        backGrass=movHandler.getBackGrass();
        web1=movHandler.getWeb1();
        web2=movHandler.getWeb2();
        web3=movHandler.getWeb3();


    }
    private void drawFly(float runTime) {

        if (myFly.notFlap()) {
            batch.draw(flyMid, myFly.getX(), myFly.getY(),
                    myFly.getWidth() / 2.0f, myFly.getHeight() / 2.0f,
                    myFly.getWidth(), myFly.getHeight(), 1, 1, myFly.getRotation());

        } else {
            batch.draw(flyAnimation.getKeyFrame(runTime), myFly.getX(),
                    myFly.getY(), myFly.getWidth() / 2.0f,
                    myFly.getHeight() / 2.0f, myFly.getWidth(), myFly.getHeight(),
                    1, 1, myFly.getRotation());
        }

    }
    private void drawGrass() {
        batch.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batch.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawWebs() {
        batch.draw(webUp, web1.getX(), web1.getY(), web1.getWidth(),
                web1.getHeight());
        batch.draw(webDown, web1.getX(), web1.getY() + web1.getHeight() + 45,
                web1.getWidth(), midPointY + 81 - (web1.getHeight() + 45));

        batch.draw(webUp, web2.getX(), web2.getY(), web2.getWidth(),
                web2.getHeight());
        batch.draw(webDown, web2.getX(), web2.getY() + web2.getHeight() + 45,
                web2.getWidth(), midPointY + 81 - (web2.getHeight() + 45));

        batch.draw(webUp, web3.getX(), web3.getY(), web3.getWidth(),
                web3.getHeight());
        batch.draw(webDown, web3.getX(), web3.getY() + web3.getHeight() + 45,
                web3.getWidth(), midPointY + 81 - (web3.getHeight() + 45));
    }
    private void drawSpiders() {

        batch.draw(spider, web1.getX() - 1,
                web1.getY() + web1.getHeight() - 10, 34, 24);

        batch.draw(spider, web2.getX() - 1,
                web2.getY() + web2.getHeight() - 10, 34, 24);

        batch.draw(spider, web3.getX() - 1,
                web3.getY() + web3.getHeight() - 10, 34, 24);

    }
    public void prepareTransition(int r,int g, int b, float duration){
        transitionColor.set(r/255.0f, g/255.0f,b/255.0f,1);
        alpha.setVal(1);
        Tween.registerAccessor(Value.class,new ValueAccessor());
        manager=new TweenManager();
        Tween.to(alpha,-1,duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }
    private void drawTransition(float delta){
        if (alpha.getVal()>0){
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r,transitionColor.g,
                    transitionColor.b,alpha.getVal());
            shapeRenderer.rect(0,0,136,300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);


        }
    }
    private void drawMenuUI(){
        batch.draw(flyLogo, midPointX-48,midPointY-50,96,14);
        for (PlayButton button: menuButtons){
            button.draw(batch);
        }
    }
    private void drawScoreBoard(){
        batch.draw(scoreboard,22,midPointY-26,97,37);
        batch.draw(starOff,37,midPointY-15,10,10);
        batch.draw(starOff,49,midPointY-15,10,10);
        batch.draw(starOff,61,midPointY-15,10,10);
        batch.draw(starOff,73,midPointY-15,10,10);

        if (myWorld.getScore()>2){
            batch.draw(starOn,73,midPointY-15,10,10);
        }
        if (myWorld.getScore()>17){
            batch.draw(starOn,61,midPointY-15,10,10);
        }
        if (myWorld.getScore()>50){
            batch.draw(starOn,49,midPointY-15,10,10);
        }
        if (myWorld.getScore()>80){
            batch.draw(starOn,37,midPointY-15,10,10);
        }
        if (myWorld.getScore()>120){
            batch.draw(starOn,25,midPointY-15,10,10);
        }
        int length=(""+myWorld.getScore()).length();

        ResourseLoader.whiteFont.draw(batch, ""+myWorld.getScore(),
        104-(2*length),midPointY-20);

        int length2=(""+ResourseLoader.getHighScore()).length();
        ResourseLoader.whiteFont.draw(batch, ""+ResourseLoader.getHighScore(),
                104-(2.5f*length2), midPointY-3);
    }
    private void drawRetry(){
        batch.draw(retry,36,midPointY+20,66,14);
    }
    private void drawReady(){
        batch.draw(ready,36,midPointY-5,68,14);
    }
    private void drawGameOver(){
        batch.draw(gameOver,24,midPointY+52,92,14);
    }
    private void drawScore(){
        int length=(""+myWorld.getScore()).length();
        ResourseLoader.shadow.draw(batch,""+myWorld.getScore(),
                68-(3*length),midPointY-82);
        ResourseLoader.font.draw(batch,""+myWorld.getScore(),
                68-(3*length),midPointY-83);
    }
    private void drawHighScore(){
        batch.draw(highScore,22,midPointY+50,96,14);
    }
    private void drawFlyCentered(float runTime){
        batch.draw(flyAnimation.getKeyFrame(runTime),59,myFly.getY()-15,
                myFly.getWidth()/2.0f,myFly.getHeight()/2.0f,
                myFly.getWidth(),myFly.getHeight(),1,1,myFly.getRotation());
    }

}
