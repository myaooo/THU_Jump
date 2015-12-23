package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.jump.MenuScreen.MainMenuScreen;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;

/**
 * Created by Yao on 15/12/2.
 */

/**
 * This is the main game screen, which contains a gamestage to operate game logic
 */
public class GameScreen extends ScreenAdapter {

    // fields
    // static fields
    final static int GAME_READY = 0;
    final static int GAME_RUNNING = 1;
    final static int GAME_OVER = 2;
    final static int GAME_PAUSE = 3;


    // private class fields
    private GameStage gameStage;
    private Stage overStage;
    private TsinghuaJump game;
    private int status;
    private Mediator mediator;


    /**constructor*/
    public GameScreen(TsinghuaJump inGame){
        this.game = inGame;
        mediator = new Mediator();
        gameStage = new GameStage(mediator);
        status = GAME_RUNNING;

    }

    /**Initializing*/
    private void initialize(){
        overStage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Label.LabelStyle ls = new Label.LabelStyle(Assets.defaultFont.getFont(), Color.WHITE);
        Label scoreLabel = new Label("SCORE:", ls);
    }


    /**Calls before rendering*/
    private void update(float delta){
        switch(status){
            case GAME_RUNNING:
                updateRunning(delta);
                break;
            case GAME_PAUSE:
                updatePause();
                break;
            case GAME_OVER:
                updateOver();
                break;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        switch(status){
            case GAME_RUNNING:
                gameStage.draw();
                break;
            case GAME_PAUSE:
                //
                break;
            case GAME_OVER:
                gameStage.draw();
                break;
        }
    }

    public void updateRunning(float delta){
        if (Gdx.input.isKeyPressed(Settings.KEY_LEFT))
            mediator.setLeft();
        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT))
            mediator.setRight();
        if (Gdx.input.isKeyPressed(Settings.KEY_SHOOT))
            mediator.doctorShoot();

        gameStage.update(delta);
        mediator.reset();
        if (gameStage.isGameOver()) {
            status = GAME_OVER;
            gameStage.GameOver();
        }
    }

    public void updatePause(){

    }

    public void updateOver(){
        if (Gdx.input.isTouched())
            game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void resize(int width, int height) {

        Settings.setScreen(width, height);
    }

    @Override
    public void dispose(){
        gameStage.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide(){

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }



}
