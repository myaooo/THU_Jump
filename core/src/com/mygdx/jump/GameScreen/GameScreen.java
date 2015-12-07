package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.jump.Settings;

/**
 * Created by Yao on 15/12/2.
 */

/**
 * This is the main game screen, which contains a gamestage to operate game logic
 */
public class GameScreen implements Screen {

    // fields
    private GameStage gameStage;
    private Stage testStage;

    // methods
    public GameScreen(){

    }
    @Override
    public void render(float delta) {
        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
    }

    @Override
    public void resize(int width, int height) {

        Settings.setScreen(width, height);
    }

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
