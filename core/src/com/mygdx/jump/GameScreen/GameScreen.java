package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    /**constructor*/
    public GameScreen(TsinghuaJump inGame){
        this.game = inGame;
        gameStage = new GameStage();
        status = GAME_READY;
    }

    private void update(float delta){
        gameStage.update(delta);
        switch(status){
            case GAME_READY:
                // code goes here
                break;
            case GAME_RUNNING:
                // code goes here
                break;
            case GAME_PAUSE:
                // code goes here
                break;
            case GAME_OVER:
                // code goes here
                break;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        gameStage.update(delta);
        gameStage.draw();
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
