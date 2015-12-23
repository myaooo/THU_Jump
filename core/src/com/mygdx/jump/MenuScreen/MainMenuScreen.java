package com.mygdx.jump.MenuScreen;

/**
 * @author Wang Yuehan
 */

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.jump.GameScreen.GameScreen;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;

/**
 * @author zhu ao
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.*;

import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sun.javaws.Main;

import java.lang.Math.*;

// MainMenuScreen
public class MainMenuScreen implements Screen{

    private TsinghuaJump game;
    private Stage stage;
    private Array<Button> button;
    private Music BGM =Gdx.audio.newMusic(Gdx.files.internal("data/sound/background.mp3"));

    private float time;

    public MainMenuScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        button = new Array<Button>();
        loadAssets();
        Assets.playMusic(BGM);

    }

    @Override
    // A methods that render the screen, calls whenever the screen should be rendered
    public void render(float delta){
        update(delta);
        stage.draw();

    }

    private void update(float delta){

        time = time + delta/2;

        if(button.get(0).isPressed()==true)
        {
            GameScreen gameScreen = new GameScreen(game);
            game.setScreen(gameScreen);
            Assets.stopMusic(BGM);
        }
        else if (button.get(1).isPressed()==true)
        {
            ScoreScreen scoreScreen = new ScoreScreen(game);
            game.setScreen(scoreScreen);
            Assets.stopMusic(BGM);
        }
        else if (button.get(2).isPressed()==true)
        {
            ShopScreen shopScreen = new ShopScreen(game);
            game.setScreen(shopScreen);
            Assets.stopMusic(BGM);
        }
        else if (button.get(3).isPressed()==true)
        {
            SettingsScreen settingsScreen = new SettingsScreen(game);
            game.setScreen(settingsScreen);
            Assets.stopMusic(BGM);
        }
        else if (button.get(4).isPressed()==true)
        {
            Gdx.app.exit();
            Assets.stopMusic(BGM);
        }
        else
        {
            lineMove(button.get(0),time,30,600,20);
            lineMove(button.get(1),time,200,500,20);
            lineMove(button.get(2),time,30,400,20);
            lineMove(button.get(3),time,200,300,20);
            lineMove(button.get(4),time,30,200,20);
        }
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

    private void loadAssets(){
        //background picture
        Texture logo = new Texture(Gdx.files.internal("data/background1.png"));
        Image image = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image.setSize(480,800);
        stage.addActor(image);

        //buttons
        BitmapFont font = new BitmapFont();
        Texture actor = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = font;
        tbs.up = new TextureRegionDrawable( new TextureRegion(actor) );


        button.add(new TextButton("Start new game", tbs));
        button.add(new TextButton("Achievements", tbs));
        button.add(new TextButton("Shop", tbs));
        button.add(new TextButton("Settings", tbs));
        button.add(new TextButton("Quit", tbs));

        for(int i =0;i<button.size;i++) {
            stage.addActor(button.get(i));
        }

        //labels
        BitmapFont font1 =
                new BitmapFont();
        Label.LabelStyle ls = new Label.LabelStyle(font1,
                Color.YELLOW);
        Label label = new Label("Tsinghua Jump",ls);
        label.setColor(0f,1f,0f,1f);
        label.setPosition((480-label.getWidth())/2,600);
    }

    private void lineMove(Button textButton,float time,int positionX,int positionY,int range)
    {
        float Dtime = (float)Math.floor(time);
          if(Dtime%2==0)
          {
              textButton.setPosition(positionX+2*range*(time-Dtime-0.5f),positionY);
          }
          else
          {
              textButton.setPosition(positionX-2*range*(time-Dtime-0.5f),positionY);
          }
    }
}

