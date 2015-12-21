package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
                //
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
        if (gameStage.isGameOver())
            status = GAME_OVER;
    }

    public void updatePause(){

    }

    public void updateOver(){

    }

    @Override
    public void resize(int width, int height) {

        Settings.setScreen(width, height);
    }

    @Override
    public void dispose(){

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
