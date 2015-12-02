package com.mygdx.jump.MenuScreen;

/**
 * Created by Yao on 15/12/2.
 */

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

import com.mygdx.jump.Settings;

// MainMenuScreen
public class MainMenuScreen implements Screen{

    private Game game;
    public MainMenuScreen(final Game agame){
        this.game = agame;
    }

    @Override
    // A methods that render the screen, calls whenever the screen should be rendered
    public void render(float delta){

    }

    @Override
    public void resize(int width, int height) {
        Settings.setScreen(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
