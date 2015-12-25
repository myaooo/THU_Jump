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
import com.mygdx.jump.Resource.Resource;
import com.mygdx.jump.TsinghuaJump;
import com.mygdx.jump.Settings;

/**
 * @author zhu ao
 */
public class SettingsScreen extends ScreenAdapter {

    private TsinghuaJump game;
    private Stage stage;
    private Array<Button> button;
    private Array<Button> dialog;
    private Array<Button> button_level;

    private Image dialog_image;
    private Label dialog_label_sound;
    private Label dialog_label_level;
    private Button returnButton;

    private Image image_doctor;
    private Image image_balloon;

    private Image image_help;
    private Array<Button> buttonHelp;
    private Button nextPage;
    private int nextPageCounter=0;
    private int nextPageCounter1 = 0;
    private Button button_help_back1;
    private Button button_help_back2;
    private Array<Image> HelpImages;


    private Image image_about;
    private Button button_about;


    private float time;



    public SettingsScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        button = new Array<Button>();
        dialog = new Array<Button>();
        button_level = new Array<Button>();
        buttonHelp = new Array<Button>();
        HelpImages = new Array<Image>();
        loadAssets();
    }


    @Override
    public void render(float delta){
        update(delta);
        stage.draw();
    }

    public void update(float delta){

        time = time + delta/2;

        //doctor

        DoctorMove(time,400,650,20,4);

        //buttons

        Resource.lineMove(button.get(0),time,40,500,20);
        Resource.lineMove(button.get(1),time,280,400,20);
        Resource.lineMove(button.get(2),time,40,300,20);
        Resource.lineMove(button.get(3),time,280,200,20);


        //dialog

        //music turn off
            if(button.get(0).isPressed()==true)
            {
                stage.addActor(dialog_image);
                stage.addActor(dialog.get(0));
                stage.addActor(dialog.get(1));
                stage.addActor(dialog_label_sound);
            }

        if (dialog.get(0).isPressed()==true)
        {
            dialog.get(0).remove();
            dialog.get(1).remove();
            dialog_image.remove();
            dialog_label_sound.remove();
            Settings.SOUNDOPEN = false;
        }

        if (dialog.get(1).isPressed()==true)
        {
            dialog.get(0).remove();
            dialog.get(1).remove();
            dialog_image.remove();
            dialog_label_sound.remove();
            Settings.SOUNDOPEN = true;
        }

        //level

        if(button.get(1).isPressed()==true)
        {
            stage.addActor(dialog_image);
            stage.addActor(dialog_label_level);
            for(int i=0;i<3;i++)
            {
                stage.addActor(button_level.get(i));
            }
        }


            if(button_level.get(0).isPressed()==true)
            {
                dialog_image.remove();
                dialog_label_level.remove();
                for(int j=0;j<3;j++)
                {
                    button_level.get(j).remove();
                }

                Settings.level = Settings.Levels.EASY;

            }


        if(button_level.get(1).isPressed()==true)
        {
            dialog_image.remove();
            dialog_label_level.remove();
            for(int j=0;j<3;j++)
            {
                button_level.get(j).remove();
            }

            Settings.level = Settings.Levels.NORMAL;

        }
        if(button_level.get(2).isPressed()==true)
        {
            dialog_image.remove();
            dialog_label_level.remove();
            for(int j=0;j<3;j++)
            {
                button_level.get(j).remove();
            }

            Settings.level = Settings.Levels.HARD;

        }


        //help

        if(button.get(2).isPressed()==true)
        {
            stage.addActor(image_help);
            for(int i=0;i<3;i++) {
                stage.addActor(buttonHelp.get(i));
            }
            stage.addActor(button_help_back1);
        }

        if(button_help_back1.isPressed()==true)
        {
            image_help.remove();
            for(int i=0;i<3;i++) {
                buttonHelp.get(i).remove();
            }
            button_help_back1.remove();
        }

        if(buttonHelp.get(0).isPressed()==true)
        {
            for(int i=0;i<3;i++) {
                buttonHelp.get(i).remove();
            }
            button_help_back1.remove();
            stage.addActor(button_help_back2);
            stage.addActor(nextPage);
            stage.addActor(HelpImages.get(0));
            nextPageCounter = 0;
        }


        if(nextPage.isPressed()==true){
            nextPageCounter1 = nextPageCounter1 + 1;
            if(nextPageCounter1==1)
            {

                if (nextPageCounter % 2 == 0)
                {
                    HelpImages.get(0).remove();
                    stage.addActor(HelpImages.get(1));
                    nextPageCounter = nextPageCounter + 1;
                } else
                {
                    HelpImages.get(1).remove();
                    stage.addActor(HelpImages.get(0));
                    nextPageCounter = nextPageCounter + 1;
                }
            }
        }
        else
        {
            nextPageCounter1 = 0;
        }

        if(buttonHelp.get(1).isPressed()==true)
        {
            for(int i=0;i<3;i++) {
                buttonHelp.get(i).remove();
            }
            button_help_back1.remove();
            stage.addActor(button_help_back2);
            stage.addActor(HelpImages.get(2));
        }

        if(buttonHelp.get(2).isPressed()==true)
        {
            for(int i=0;i<3;i++) {
                buttonHelp.get(i).remove();
            }
            button_help_back1.remove();
            stage.addActor(button_help_back2);
            stage.addActor(HelpImages.get(3));
        }

        if(button_help_back2.isPressed()==true)
        {
            //remove
            for(int i=0;i<4;i++)
            {
                HelpImages.get(i).remove();
            }
            nextPage.remove();
            button_help_back2.remove();

            //new
            for(int i=0;i<3;i++) {
                stage.addActor(buttonHelp.get(i));
            }
            stage.addActor(button_help_back1);

        }
        //about

        if(button.get(3).isPressed()==true)
        {
            stage.addActor(image_about);
            stage.addActor(button_about);
        }

        if(button_about.isPressed()==true)
        {
            image_about.remove();
            button_about.remove();
        }
        //returnButton
        Resource.lineMove(returnButton,time,(int)(450-returnButton.getWidth()),30,20);
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
        Texture actor = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = Resource.font.getFont();
        tbs.fontColor = new Color(44/255f,114/255f,227/255f,0.5f);
        tbs.up = new TextureRegionDrawable( new TextureRegion(actor) );


        button.add(new TextButton("sounds", tbs));
        button.add(new TextButton("levels", tbs));
        button.add(new TextButton("helps", tbs));
        button.add(new TextButton("about", tbs));

        for(int i=0;i<button.size;i++)
        {
            button.get(i).setSize(200,130);
            stage.addActor(button.get(i));
        }
        //对话框
        //dialog_image
        Texture logo_dialog = new Texture(Gdx.files.internal("data/dialog.png"));
        dialog_image = new Image(new TextureRegionDrawable( new TextureRegion(logo_dialog) ));
        dialog_image.setSize(400,200);
        dialog_image.setPosition((480-dialog_image.getWidth())/2,(800-dialog_image.getHeight())/2);
        dialog_image.setColor(1,1,1,0.5f);

        //dialog_button
        Texture actor1 = new Texture(Gdx.files.internal("data/dialog_button.png"));
        TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
        tbs1.font = Resource.font.getFont();
        tbs1.fontColor = new Color(0f,0f,0f,0.8f);
        tbs1.up = new TextureRegionDrawable( new TextureRegion(actor1) );

        dialog.add(new TextButton("Yes",tbs1));

        Texture actor2 = new Texture(Gdx.files.internal("data/dialog_button1.png"));
        TextButton.TextButtonStyle tbs2 = new TextButton.TextButtonStyle();
        tbs2.font = Resource.font.getFont();
        tbs2.fontColor = new Color(0f,0f,0f,0.8f);
        tbs2.up = new TextureRegionDrawable( new TextureRegion(actor2) );

        dialog.add(new TextButton("No",tbs2));

        dialog.get(0).setPosition(240-dialog_image.getWidth()/4-dialog.get(0).getWidth()/2,20+400-dialog_image.getHeight()/4-dialog.get(0).getHeight()/2);
        dialog.get(1).setPosition(240+dialog_image.getWidth()/4-dialog.get(0).getWidth()/2,20+400-dialog_image.getHeight()/4-dialog.get(0).getHeight()/2);

        //dialog_buttonlevel

        Texture actor3 = new Texture(Gdx.files.internal("data/cloud_button.png"));
        TextButton.TextButtonStyle tbs3 = new TextButton.TextButtonStyle();
        tbs3.font = Resource.font.getFont();
        tbs3.fontColor = new Color(0f,0f,0f,0.8f);
        tbs3.up = new TextureRegionDrawable( new TextureRegion(actor3) );

        button_level.add(new TextButton("EASY",tbs3));
        button_level.add(new TextButton("NORMAL",tbs3));
        button_level.add(new TextButton("HARD",tbs3));

        for(int i=0;i<3;i++)
        {
            button_level.get(i).setSize(100,70);
            button_level.get(i).setPosition(10+250-(dialog_image.getWidth()/2)+i*(dialog_image.getWidth()/3),350);
        }


        //dialog_label_sound
        Label.LabelStyle ls = new Label.LabelStyle(Resource.font.getFont(), Color.YELLOW);
        dialog_label_sound = new Label("Do you want to turn off the music?",ls);
        dialog_label_sound.setColor(1f,99/255f,71/255f,1f);
        dialog_label_sound.setPosition(5+(480-dialog_label_sound.getWidth())/2,50+(800-dialog_label_sound.getHeight())/2);

        //dialog_label_level
        Label.LabelStyle ls1 = new Label.LabelStyle(Resource.font.getFont(), Color.YELLOW);
        dialog_label_level = new Label("Which level do you want to choose?",ls1);
        dialog_label_level.setColor(1f,99/255f,71/255f,1f);
        dialog_label_level.setPosition((480-dialog_label_sound.getWidth())/2,50+(800-dialog_label_sound.getHeight())/2);

        //help_image
        logo = new Texture(Gdx.files.internal("data/cloud_background.png"));
        image_help = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image_help.setSize(420,500);
        image_help.setPosition(30,150);
        image_help.setColor(1f,1f,1f,0.8f);

        //help button

        Texture actor6 = new Texture(Gdx.files.internal("data/black_button.png"));
        TextButton.TextButtonStyle tbs6 = new TextButton.TextButtonStyle();
        tbs6.font = Resource.font.getFont();
        tbs6.fontColor = new Color(1f,1f,1f,1f);
        tbs6.up = new TextureRegionDrawable( new TextureRegion(actor6) );

        buttonHelp.add(new TextButton("Items",tbs6));
        buttonHelp.add(new TextButton("Monsters",tbs6));
        buttonHelp.add(new TextButton("Floors",tbs6));

        for(int i=0;i<3;i++)
        {
            buttonHelp.get(i).setSize(100,40);
            buttonHelp.get(i).setPosition((480-buttonHelp.get(i).getWidth())/2,450-80*i);
            buttonHelp.get(i).setColor(1f,1f,1f,0.7f);
        }

        // help next page button

        Texture actor7 = new Texture(Gdx.files.internal("data/cloud_button.png"));
        TextButton.TextButtonStyle tbs7 = new TextButton.TextButtonStyle();
        tbs7.font = Resource.font.getFont();
        tbs7.fontColor = new Color(0f,0f,0f,0.7f);
        tbs7.up = new TextureRegionDrawable( new TextureRegion(actor7) );


        nextPage = new TextButton("next page",tbs7);
        nextPage.setSize(100,40);
        nextPage.setPosition(320,530);

        //HelpImages
        logo = new Texture(Gdx.files.internal("data/item1.png"));
        HelpImages.add( new Image(new TextureRegionDrawable( new TextureRegion(logo) )));
        logo = new Texture(Gdx.files.internal("data/item2.png"));
        HelpImages.add( new Image(new TextureRegionDrawable( new TextureRegion(logo) )));
        logo = new Texture(Gdx.files.internal("data/monster.png"));
        HelpImages.add( new Image(new TextureRegionDrawable( new TextureRegion(logo) )));
        logo = new Texture(Gdx.files.internal("data/floor.png"));
        HelpImages.add( new Image(new TextureRegionDrawable( new TextureRegion(logo) )));

        for(int i=0;i<4;i++)
        {
            HelpImages.get(i).setSize(300,280);
            HelpImages.get(i).setPosition((480-HelpImages.get(i).getWidth())/2,240);
        }

        //about

        //about image
        logo = new Texture(Gdx.files.internal("data/cloud_background_about.png"));
        image_about = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image_about.setSize(420,500);
        image_about.setPosition(30,150);
        image_about.setColor(1f,1f,1f,0.8f);


        //about button

        Texture actor5 = new Texture(Gdx.files.internal("data/cloud_button.png"));
        TextButton.TextButtonStyle tbs5 = new TextButton.TextButtonStyle();
        tbs5.font = Resource.font.getFont();
        tbs5.fontColor = tbs.fontColor;
        tbs5.up = new TextureRegionDrawable( new TextureRegion(actor5) );

        button_about = new TextButton("back",tbs5);

        button_about.setSize(100,70);

        button_about.setPosition(310,180);

        //help back button

        tbs5.fontColor = new Color(0f,0f,0f,0.7f);
        button_help_back1 = new TextButton("back",tbs5);
        button_help_back2 = new TextButton("back",tbs5);

        button_help_back1.setSize(100,70);
        button_help_back1.setPosition(310,180);
        button_help_back2.setSize(100,70);
        button_help_back2.setPosition(310,180);


        //returnButton
        Texture actor4 = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs4 = new TextButton.TextButtonStyle();
        tbs4.font = Resource.font.getFont();
        tbs4.fontColor = tbs.fontColor;
        tbs4.up = new TextureRegionDrawable( new TextureRegion(actor4) );

        returnButton = new TextButton("return",tbs4);
        returnButton.setSize(200,130);
        stage.addActor(returnButton);

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
    }



    private void DoctorMove(float time, int positionX, int positionY, int Yrange,float Yfrequence){

        image_doctor.setPosition(positionX,positionY + Yrange*(float) (Math.sin(Yfrequence * (double) (time))));
        image_balloon.setPosition(positionX - 43,positionY + 10 + Yrange*(float) (Math.sin(Yfrequence * (double) (time))));
    }
}
