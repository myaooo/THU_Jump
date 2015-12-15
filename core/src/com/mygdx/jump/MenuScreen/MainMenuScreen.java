package com.mygdx.jump.MenuScreen;

/**
 * @author Wang Yuehan
 */

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.jump.GameScreen.GameScreen;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;
import com.mygdx.jump.MenuHandler;
import com.mygdx.jump.Options;
import com.mygdx.jump.Res;
import com.mygdx.jump.screens.StartScreen.StartType;

// MainMenuScreen
public class MainMenuScreen implements Screen{

    private TsinghuaJump game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private MenuHandler menu;

    private int dividedWidth;
    private int dividedHeight;

    public MainMenuScreen(TsinghuaJump agame, OrthographicCamera camera, SpriteBatch batch){
        this.game = agame;
        this.camera = camera;
        this.batch = batch;

        dividedWidth = (int) (camera.viewportWidth / Res._pattern.getWidth()) + 1;
        dividedHeight = (int) (camera.viewportHeight / Res._pattern.getHeight()) + 1;

        menu = new MenuHandler(MenuHandler.Layout.Vertical, MenuHandler.Handle.KeyboardAndMouse, (int) (camera.viewportWidth / 2) - Res._button.getWidth() / 2, (int) (camera.viewportHeight
                - Res._logo.getHeight() - 100));
        menu.addMenuItem("Join Game", Res._button, Res._buttonFont, () -> {
            game.setScreen(new JoinScreen(game, camera, batch));
        });
        menu.addMenuItem("Host Game", Res._button, Res._buttonFont, () -> {
            game.setScreen(new HostScreen(game, camera, batch));
        });
        menu.addMenuItem("Options", Res._button, Res._buttonFont, () -> {
            game.setScreen(new OptionMenuScreen(game, camera, batch));
        });
        menu.addMenuItem("Credits", Res._button, Res._buttonFont, () -> {
            game.setScreen(new StartScreen(game, camera, batch, StartType.credits));
        });
        menu.addMenuItem("Exit", Res._button, Res._buttonFont, () -> {
            Gdx.app.exit();
        });
    }

    @Override
    // A methods that render the screen, calls whenever the screen should be rendered
    public void render(float delta){
        game.setScreen(new GameScreen(game));
        update(delta);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        {
            batch.draw(Res._pattern.getTexture(), 0, 0, Res._pattern.getWidth() * dividedWidth, Res._pattern.getHeight() * dividedHeight, 0, dividedHeight, dividedWidth, 0);
            batch.draw(Res._logo.getTexture(), camera.viewportWidth / 2 - Res._logo.getWidth() / 2, camera.viewportHeight - Res._logo.getHeight() - 20);
            menu.draw(batch);
        }
        batch.end();
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
