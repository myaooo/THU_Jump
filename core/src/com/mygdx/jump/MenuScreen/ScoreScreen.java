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
import com.mygdx.jump.Settings;
import com.mygdx.jump.Resource.Resource;
import com.mygdx.jump.Record.Recorder;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by ordly on 15/12/15.
 */
public class ScoreScreen extends ScreenAdapter{
    private TsinghuaJump game;
    private Stage stage;
    private Button returnButton;
    private Button scoreButton;

    private float time;

    private Array<Label> scores;
    private Array<Label> coins;

    private Image image_doctor;
    private Image image_balloon;

    private int scoresShow = 5;

    public ScoreScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        scores = new Array<Label>();
        coins = new Array<Label>();
        loadAssets();
    }

    @Override
    public void render(float delta){
        update(delta);
        stage.draw();
    }

    private void update(float delta){

        time = time + delta/2;

        //update the data


        //doctor

        DoctorMove(time,400,650,20,4);

        //reuturnButton
        Resource.lineMove(returnButton,time,(int)(450-returnButton.getWidth()),30,20);
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


        //balloon
        Texture logo_balloon = new Texture(Gdx.files.internal("data/balloon.png"));
        image_balloon = new Image(new TextureRegionDrawable( new TextureRegion(logo_balloon) ));
        image_balloon.setSize(100,120);
        stage.addActor(image_balloon);

        //doctor

        Texture logo_doctor = new Texture(Gdx.files.internal("data/image_doctor1.png"));
        image_doctor = new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor) ));
        image_doctor.setSize(50,60);
        stage.addActor(image_doctor);


        //scores
        logo = new Texture(Gdx.files.internal("data/cloud_background.png"));
        Image image_scores = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image_scores.setSize(420,500);
        image_scores.setPosition(30,150);
        stage.addActor(image_scores);

        //scoresbutton
        Texture actor = new Texture(Gdx.files.internal("data/cloud_button.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = Resource.font.getFont();
        tbs.fontColor = new Color(44/255f,114/255f,227/255f,0.5f);
        tbs.up = new TextureRegionDrawable( new TextureRegion(actor) );

        scoreButton = new TextButton("High Scores",tbs);
       // scoreButton.setSize(200,130);
        scoreButton.setPosition((480-scoreButton.getWidth())/2,600);
        stage.addActor(scoreButton);

        //returnButtons

        Texture actor1 = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
        tbs1.font = Resource.font.getFont();
        tbs1.fontColor = new Color(44/255f,114/255f,227/255f,0.5f);
        tbs1.up = new TextureRegionDrawable( new TextureRegion(actor1) );

        returnButton = new TextButton("return",tbs1);
        returnButton.setSize(200,130);
        stage.addActor(returnButton);

        //scores ranking

        for(int i=0;i< 5;i++)
        {
            Label.LabelStyle ls = new Label.LabelStyle(Resource.Bfont.getFont(),
                    Color.YELLOW);
            Label label = new Label(Recorder.records.get(i).score + " ",ls);
            label.setColor(0f,0f,0f,0.8f);

            Label.LabelStyle ls1 = new Label.LabelStyle(Resource.Bfont.getFont(),
                    Color.YELLOW);
            Label label1 = new Label(Recorder.records.get(i).coin + " ",ls1);
            label1.setColor(0f,0f,0f,0.8f);

            if(Recorder.records.get(i).score==0)
            {
                scoresShow = i;
                break;
            }

            scores.add(label);
            coins.add(label1);
            label.setPosition((480-label.getWidth())/2,450- 50*i);
            label1.setPosition((480-label.getWidth())/2 + 100,450- 50*i);

        }

        for( int i=0;i<scoresShow;i++)
        {

            Label.LabelStyle ls = new Label.LabelStyle(Resource.Bfont.getFont(),
                    Color.YELLOW);
            Label label = new Label(i+1+" ",ls);
            label.setColor(254/255f,254/255f,65/255f,1f);

            label.setPosition((480-label.getWidth())/2 - 100,450- 50*i);

            stage.addActor(label);
            stage.addActor(scores.get(i));
            stage.addActor(coins.get(i));
        }


        Label.LabelStyle ls1 = new Label.LabelStyle(Resource.fontX.getFont(),
                Color.YELLOW);
        Label label1 = new Label("Rank",ls1);
        label1.setColor(44/255f,114/255f,227/255f,0.5f);
        stage.addActor(label1);
        label1.setPosition((480-scores.get(0).getWidth())/2 - 100, 500);

        Label.LabelStyle ls2 = new Label.LabelStyle(Resource.fontX.getFont(),
                Color.YELLOW);
        Label label2 = new Label("Scores",ls2);
        label2.setColor(44/255f,114/255f,227/255f,0.5f);
        stage.addActor(label2);
        label2.setPosition((480-scores.get(0).getWidth())/2, 500);

        Label.LabelStyle ls3 = new Label.LabelStyle(Resource.fontX.getFont(),
                Color.YELLOW);
        Label label3 = new Label("Coins",ls3);
        label3.setColor(44/255f,114/255f,227/255f,0.5f);
        stage.addActor(label3);
        label3.setPosition((480-scores.get(0).getWidth())/2 + 100, 500);

    }

    private void DoctorMove(float time, int positionX, int positionY, int Yrange,float Yfrequence){

        image_doctor.setPosition(positionX,positionY + Yrange*(float) (Math.sin(Yfrequence * (double) (time))));
        image_balloon.setPosition(positionX - 43,positionY + 10 + Yrange*(float) (Math.sin(Yfrequence * (double) (time))));
    }
}
