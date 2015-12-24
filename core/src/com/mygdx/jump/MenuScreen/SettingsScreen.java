package com.mygdx.jump.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
 * @author zhu ao
 */
public class SettingsScreen extends ScreenAdapter {

    private TsinghuaJump game;
    private Stage stage;
    private Array<Button> button;
    private Array<Button> dialog;
    private Array<Label> dialog_label;
    private Image dialog_image;
    //private Label dialog_label;
    private Button returnButton;

    private float time;

    public SettingsScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        button = new Array<Button>();
        dialog = new Array<Button>();
        dialog_label = new Array<Label>();
        loadAssets();
    }


    @Override
    public void render(float delta){
        update(delta);
        stage.draw();
    }

    public void update(float delta){

        time = time + delta/3;

        //buttons

        lineMove(button.get(0),time,40,500,20);
        lineMove(button.get(1),time,280,400,20);
        lineMove(button.get(2),time,40,300,20);
        lineMove(button.get(3),time,280,200,20);


        //dialog
        for(int i=0;i<button.size;i++)
        {
            if(button.get(i).isPressed()==true)
            {
                stage.addActor(dialog_image);
                stage.addActor(dialog.get(0));
                stage.addActor(dialog.get(1));
                stage.addActor(dialog_label.get(i));
            }
        }

        if (dialog.get(0).isPressed()==true)
        {
            dialog.get(0).remove();
            dialog.get(1).remove();
            dialog_image.remove();
            dialog_label.get(0).remove();
        }

        if (dialog.get(1).isPressed()==true)
        {
            dialog.get(0).remove();
            dialog.get(1).remove();
            dialog_image.remove();
            dialog_label.get(1).remove();
        }

        //returnButton
        lineMove(returnButton,time,(int)(450-returnButton.getWidth()),30,20);
        if(returnButton.isPressed()==true)
        {
            MainMenuScreen mainMenuScreen = new MainMenuScreen(game);
            game.setScreen(mainMenuScreen);
        }
    }

    private void loadAssets(){
        //background picture
        Texture logo = new Texture(Gdx.files.internal("data/background_library.png"));
        Image image = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image.setSize(480,800);
        stage.addActor(image);

        //buttons
        BitmapFont font = new BitmapFont();
        Texture actor = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = font;
        tbs.up = new TextureRegionDrawable( new TextureRegion(actor) );


        button.add(new TextButton("sounds", tbs));
        button.add(new TextButton("levels", tbs));
        button.add(new TextButton("helps", tbs));
        button.add(new TextButton("about", tbs));

        for(int i=0;i<button.size;i++)
        {
            stage.addActor(button.get(i));
        }
        //对话框
        //dialog_image
        Texture logo_dialog = new Texture(Gdx.files.internal("data/dialog.png"));
        dialog_image = new Image(new TextureRegionDrawable( new TextureRegion(logo_dialog) ));
        dialog_image.setSize(400,200);
        dialog_image.setPosition((480-dialog_image.getWidth())/2,(800-dialog_image.getHeight())/2);

        //dialog_button
        Texture actor1 = new Texture(Gdx.files.internal("data/dialog_button.png"));
        TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
        tbs1.font = font;
        tbs1.up = new TextureRegionDrawable( new TextureRegion(actor1) );

        dialog.add(new TextButton("Yes",tbs1));

        Texture actor2 = new Texture(Gdx.files.internal("data/dialog_button1.png"));
        TextButton.TextButtonStyle tbs2 = new TextButton.TextButtonStyle();
        tbs2.font = font;
        tbs2.up = new TextureRegionDrawable( new TextureRegion(actor2) );

        dialog.add(new TextButton("No",tbs2));


        dialog.get(0).setPosition(240-dialog_image.getWidth()/4-dialog.get(0).getWidth()/2,400-dialog_image.getHeight()/4-dialog.get(0).getHeight()/2);
        dialog.get(1).setPosition(240+dialog_image.getWidth()/4-dialog.get(0).getWidth()/2,400-dialog_image.getHeight()/4-dialog.get(0).getHeight()/2);

        //dialog_labels
        BitmapFont font0 = new BitmapFont();
        Label.LabelStyle ls0 = new Label.LabelStyle(font0, Color.YELLOW);
        dialog_label.add(new Label("Do you want to turn off the music?",ls0));
        dialog_label.get(0).setColor(1f, 99 / 255f, 71 / 255f, 1f);
        dialog_label.get(0).setPosition((480 - dialog_label.get(0).getWidth()) / 2, (800 - dialog_label.get(0).getHeight()) / 2);
/*
        BitmapFont font1 = new BitmapFont();
        Label.LabelStyle ls1 = new Label.LabelStyle(font1, Color.YELLOW);
        dialog_label.add(new Label("Do you want to turn off the sound?",ls1));
        dialog_label.get(1).setColor(1f, 99 / 255f, 71 / 255f, 1f);
        dialog_label.get(1).setPosition((480 - dialog_label.get(1).getWidth()) / 2, (800 - dialog_label.get(1).getHeight()) / 2);
*/
        //returnButton
        Texture actor3 = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs3 = new TextButton.TextButtonStyle();
        tbs3.font = font;
        tbs3.up = new TextureRegionDrawable( new TextureRegion(actor3) );

        returnButton = new TextButton("return",tbs3);
        stage.addActor(returnButton);
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
