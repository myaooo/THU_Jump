package com.mygdx.jump.MenuScreen;

/**
 * @author Wang Yuehan
 */

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

import com.mygdx.jump.GameScreen.GameScreen;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;

// MainMenuScreen
public class MainMenuScreen implements Screen{

    private TsinghuaJump game;
    public MainMenuScreen(TsinghuaJump agame){
        this.game = agame;
    }

    @Override
    // A methods that render the screen, calls whenever the screen should be rendered
    public void render(float delta){
        game.setScreen(new GameScreen(game));
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
