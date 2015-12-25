package com.mygdx.jump.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.GameScreen;
import com.mygdx.jump.GameScreen.GameStage;
import com.mygdx.jump.MenuScreen.StartScreen;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;
import com.mygdx.jump.MenuScreen.MainMenuScreen;

/**
 * @author wang yuehan
 */
public class StartScreen implements Screen{

    private TsinghuaJump game;
    private Stage stage;
    private Array<Button> button;
    private Image barimg;
    private Doctor doctor;

    private float time;

    public StartScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        loadAssets();
    }

    @Override
    // A methods that render the screen, calls whenever the screen should be rendered
    public void render(float delta){
        update(delta);
        stage.draw();
    }

    private void update(float delta){

        time = time + delta/3;
        if (time<=1) {
            sizemove(barimg,time);
        }
        else{
            MainMenuScreen MainMenuScreen = new MainMenuScreen(game);
            game.setScreen(MainMenuScreen);
        }
        if (doctor.getY() <= 270)
            doctor.jump(600);
        doctor.update(delta,0);
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
        Texture logo = new Texture(Gdx.files.internal("data/startscreen/qinghuayuan.png"));
        Image image = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image.setSize(480,800);
        stage.addActor(image);

        Texture platform = new Texture(Gdx.files.internal("data/startscreen/platform.png"));
        Image platformimg = new Image(new TextureRegionDrawable( new TextureRegion(platform) ));
        platformimg.setSize(310,35);
        platformimg.setPosition(85, 195);
        stage.addActor(platformimg);

        Texture bar = new Texture(Gdx.files.internal("data/startscreen/bar.png"));
        barimg = new Image(new TextureRegionDrawable( new TextureRegion(bar) ));
        barimg.setSize(300,25);
        barimg.setPosition(90,200);
        stage.addActor(barimg);

        doctor = new Doctor(null);
        doctor.XMax = 400;
        doctor.setBounds(200,300,80,80);
        doctor.setAcceleration(0,-1500);
        stage.addActor(doctor);

        //labels
        BitmapFont font1 =
                new BitmapFont();
        Label.LabelStyle ls = new Label.LabelStyle(font1,
                Color.YELLOW);
        Label label = new Label("Tsinghua Jump",ls);
        label.setColor(0f,1f,0f,1f);
        label.setPosition((480-label.getWidth())/2,600);
    }

    private void sizemove(Image barimage,float length)
    {
            barimage.setSize(300*(length),25);
    }
}
