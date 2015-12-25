package com.mygdx.jump.MenuScreen;

/**
 * @author Wang Yuehan
 */

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.jump.GameScreen.GameScreen;
import com.mygdx.jump.Record.Recorder;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;
import com.mygdx.jump.Resource.Font;

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
//import com.sun.javadoc.Doc;
import com.sun.javaws.Main;

import java.lang.Math.*;
import com.mygdx.jump.Settings;
import com.mygdx.jump.Resource.Resource;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.BattleScreen;

// MainMenuScreen
public class MainMenuScreen implements Screen{

    private TsinghuaJump game;
    private Stage stage;
    private Array<Button> button;

    private Image image_balloon;
    private Image image_doctor;
    private Image image_doctor1;
    private Image image_doctor2;
    private Image image_doctor3;

    private float time;

    private static float xPPosition;
    private static float yPPosition;

    private float XpositionN;
    private float YpositionN;
    private float XpositionN1;
    private float YpositionN1;
    private float timeN;
    private int counter = 0;
    private int counter1 = 0;
    private int counter2 = 0;
    private int Counter = 0;
    private static int ScreenCounter = 0;
    private boolean flag = false;

    private float[] R = new float[3];
    private int[] buttonCounter = new int[5];
    private float startTime = 0;

    public MainMenuScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        button = new Array<Button>();
        ScreenCounter = ScreenCounter + 1;
        loadAssets();
        if(ScreenCounter==1)
        {
            Recorder.load();
            Settings.load();
        }
    }

    @Override
    // A methods that render the screen, calls whenever the screen should be rendered
    public void render(float delta){
        update(delta);
        stage.draw();

    }

    private void update(float delta){

        time = time + delta/2;

/*
        boolean flag1 = false;

        for(int i=0;i<button.size;i++)
        {
            if(Bbutton[i])
            {
                flag1 = true;
            }
        }

        System.out.println(flag1);
        if(flag1==false)
        {
            for(int i=0;i<button.size;i++)
            {
               Bbutton[i] = button.get(i).isPressed();
                System.out.println(Bbutton[i]);
            }
        }

     //   System.out.println(Bbutton[0]);
        if (flag==true)
        {
        if(Bbutton[0])
        {
            buttonCounter[0] = buttonCounter[0]+ 1;
            if(buttonCounter[0]==1)
            {
                startTime = time;

                System.out.println(startTime);
            }

            R = Resource.SolveEquation(xPPosition,yPPosition,300,540,(300+xPPosition)/2);
            System.out.println(R);
            Bbutton[0]= Resource.DoctorJump(R,stage,image_doctor1,image_doctor3,time,startTime,0.01f,xPPosition,yPPosition,300,540,(300+xPPosition)/2);
           // GameScreen gameScreen = new GameScreen(game);
           // game.setScreen(gameScreen);
        }

        */
        if (flag==true)
        {
            if(button.get(0).isPressed()==true)
            {
                GameScreen gameScreen = new GameScreen(game);
                game.setScreen(gameScreen);
            }

            else if (button.get(1).isPressed()==true)
            {
                BattleScreen battleScreen = new BattleScreen(game);
                game.setScreen(battleScreen);
            }
        else if (button.get(2).isPressed()==true)
        {
            ScoreScreen scoreScreen = new ScoreScreen(game);
            game.setScreen(scoreScreen);
        }
        else if (button.get(3).isPressed()==true)
        {
            ShopScreen shopScreen = new ShopScreen(game);
            game.setScreen(shopScreen);
        }
        else if (button.get(4).isPressed()==true)
        {
            SettingsScreen settingsScreen = new SettingsScreen(game);
            game.setScreen(settingsScreen);
        }
        else if (button.get(5).isPressed()==true)
        {
            Gdx.app.exit();
        }
        else{

            Counter = Counter + 1;
            if(Counter==1)
            {
                image_doctor1.remove();
                image_doctor.remove();
            }

            if(Counter%200<100) {
                image_doctor1.remove();
                stage.addActor(image_doctor);
                Resource.lineMove(image_doctor, time, xPPosition, yPPosition, 20);
            }
            else
            {
                image_doctor.remove();
                stage.addActor(image_doctor1);
                Resource.lineMove(image_doctor1, time, xPPosition, yPPosition, 20);

            }

        }

        }
        else
        {
            if(ScreenCounter==1) {
                DoctorMove(time, 423, 700, 20, 3, 250);
            }
            else
            {
                Counter = Counter + 1;
                flag = true;
                if(Counter%200<100) {
                    image_doctor1.remove();
                    stage.addActor(image_doctor);
                    Resource.lineMove(image_doctor, time, xPPosition,yPPosition, 20);
                }
                else
                {
                    image_doctor.remove();
                    stage.addActor(image_doctor1);
                    Resource.lineMove(image_doctor1, time, xPPosition, yPPosition, 20);

                }
            }

        }

        Resource.lineMove(button.get(0),time,30,580,20);
        Resource.lineMove(button.get(1),time,200,480,20);
        Resource.lineMove(button.get(2),time,30,380,20);
        Resource.lineMove(button.get(3),time,200,280,20);
        Resource.lineMove(button.get(4),time,30,180,20);
        Resource.lineMove(button.get(5),time,200,80,20);

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
        Texture actor = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = Resource.font.getFont();
        tbs.fontColor = new Color(44/255f,114/255f,227/255f,0.5f);
        tbs.up = new TextureRegionDrawable( new TextureRegion(actor) );

        button.add(new TextButton("New game", tbs));
        button.add(new TextButton("Battle game", tbs));
        button.add(new TextButton("High scores", tbs));
        button.add(new TextButton("Shop", tbs));
        button.add(new TextButton("Settings", tbs));
        button.add(new TextButton("Quit",tbs));

        for(int i =0;i<button.size;i++) {
            button.get(i).setSize(200,130);
            stage.addActor(button.get(i));
        }


        //balloon and doctor
        Texture logo_balloon = new Texture(Gdx.files.internal("data/balloon.png"));
        image_balloon = new Image(new TextureRegionDrawable( new TextureRegion(logo_balloon) ));
        image_balloon.setSize(100,120);
        if(ScreenCounter==1) {
            stage.addActor(image_balloon);
        }
        Texture logo_doctor = new Texture(Gdx.files.internal("data/image_doctor1.png"));
        image_doctor = new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor) ));
        image_doctor.setSize(50,60);
        stage.addActor(image_doctor);


        Texture logo_doctor1 = new Texture(Gdx.files.internal("data/image_doctor.png"));
        image_doctor1 = new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor1) ));
        image_doctor1.setSize(50,60);

        Texture logo_doctor2 = new Texture(Gdx.files.internal("data/image_doctor3.png"));
        image_doctor2 = new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor2) ));
        image_doctor2.setSize(50,60);

        Texture logo_doctor3 = new Texture(Gdx.files.internal("data/image_doctor2.png"));
        image_doctor3 = new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor3) ));
        image_doctor3.setSize(50,60);
    }

    private boolean DoctorMove(float time, int positionX, int positionY, int Yrange,float Yfrequence,float Xspeed)
    {
        float x = positionX-Xspeed*time;


        if( x < 120)
        {
            counter = counter + 1;

            if(counter==1)
            {
                XpositionN = x;
                YpositionN = positionY + Yrange * (float) (Math.sin(Yfrequence * (double) (time)));
                timeN = time;
            }

            if(time-timeN>0.1)
            {
                counter1 = counter1 + 1;

                if(counter1==1)
                {
                    image_doctor.remove();
                    stage.addActor(image_doctor2);
                }
                if (YpositionN - (time - timeN) * 2500 * (time - timeN) + 500 * (time - timeN) > 650) {
                    image_doctor2.setPosition(XpositionN - 100 * (time - timeN), YpositionN - 2500 * (time - timeN) * (time - timeN) + 500 * (time - timeN));
                }
                else
                {
                    counter2 = counter2 + 1;

                    if(counter2==1)
                    {
                        XpositionN1 = XpositionN - 100 * (time - timeN);
                        YpositionN1 = 650;
                        image_doctor2.remove();
                        stage.addActor(image_doctor);
                        stage.addActor(image_doctor1);
                    }

                    if(counter2%200<100) {
                        image_doctor1.remove();
                        stage.addActor(image_doctor);
                        Resource.lineMove(image_doctor, time, (int) XpositionN1, (int) YpositionN1, 20);

                    }
                    else
                    {
                        image_doctor.remove();
                        stage.addActor(image_doctor1);
                        Resource.lineMove(image_doctor1, time, (int) XpositionN1, (int) YpositionN1, 20);

                    }
                }
            }
            else {
                if (YpositionN - (time - timeN) * 2500 * (time - timeN) + 500 * (time - timeN) > 650) {
                    image_doctor.setPosition(XpositionN - 100 * (time - timeN), YpositionN - 2500 * (time - timeN) * (time - timeN) + 500 * (time - timeN));
                }
            }

            image_balloon.setPosition(XpositionN - 43 -Xspeed*(time - timeN ),YpositionN + 10 + (time - timeN)*Xspeed*(time - timeN));
        }
        else {
            image_doctor.setPosition(x, positionY + Yrange * (float) (Math.sin(Yfrequence * (double) (time))));
            image_balloon.setPosition(x - 43, positionY + 10 + Yrange * (float) (Math.sin(Yfrequence * (double) (time))));

        }

        if (image_balloon.getY()>900)
        {
            flag = true;
            xPPosition = XpositionN1;
            yPPosition = YpositionN1;
            image_balloon.remove();
        }
        return flag;


    }
}

