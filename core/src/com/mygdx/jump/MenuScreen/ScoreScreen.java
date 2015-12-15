package com.mygdx.jump.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.jump.TsinghuaJump;

/**
 * Created by ordly on 15/12/15.
 */
public class ScoreScreen extends ScreenAdapter{
    private TsinghuaJump game;
    private Stage stage;
    private Button returnButton;

    private float time;

    public ScoreScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        loadAssets();
    }

    @Override
    public void render(float delta){
        update(delta);
        stage.draw();
    }

    private void update(float delta){

        time = time + delta/3;

        //update the data

        //reuturnButton
        lineMove(returnButton,time,(int)(450-returnButton.getWidth()),30,20);
        if(returnButton.isPressed()==true)
        {
            MainMenuScreen mainMenuScreen = new MainMenuScreen(game);
            game.setScreen(mainMenuScreen);
        }
    }

    private void loadAssets(){
        //background picture
        Texture logo = new Texture(Gdx.files.internal("data/background_gym.png"));
        Image image = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image.setSize(480,800);
        stage.addActor(image);

        //returnButtons
        BitmapFont font = new BitmapFont();
        Texture actor = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = font;
        tbs.up = new TextureRegionDrawable( new TextureRegion(actor) );

        returnButton = new TextButton("return",tbs);
        stage.addActor(returnButton);
    }

    private void lineMove(Button textButton,float time,int positionX,int positionY,int range)
    {
        float Dtime = (float)Math.floor(time);
        if(Dtime%2==0)
        {
            textButton.setPosition(positionX+range*(time-Dtime),positionY);
        }
        else
        {
            textButton.setPosition(positionX-range*(time-Dtime),positionY);
        }
    }
}
