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
import com.mygdx.jump.Resource.Resource;
import net.dermetfan.gdx.physics.box2d.RotationController;

/**
 * @author zhu ao
 */
public class ShopScreen extends ScreenAdapter {
    private TsinghuaJump game;
    private Stage stage;
    private Array<Button> button;
    private Array<Button> dialog;
    private Image dialog_image;
    private Label dialog_label;
    private Label dialog_label_Yes;
    private Label dialog_label_No;
    private Array<Image> image_bought;

    private Button returnButton;
    private Button backButton;

    private Array<Image> doctors;
    private Array<Label> label_prices;
    private Array<Image> image_prices;
    private int[] prices;
    private Label dialog_label_bought;

    private Label.LabelStyle ls_totalCoins;
    private Label label_totalCoins;

    private int Counter = 0;

    private boolean Doctorflag = true;
    private int buyRec = 0;

    private float time;

    public ShopScreen(final TsinghuaJump agame){
        this.game = agame;
        time = 0;
        stage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        button = new Array<Button>();
        dialog = new Array<Button>();
        doctors = new Array<Image>();
        label_prices = new Array<Label>();
        image_prices = new Array<Image>();
        image_bought = new Array<Image>();
        prices = new int[6];

        loadAssets();
    }


    @Override
    public void render(float delta){
        update(delta);
        stage.draw();
    }

    public void update(float delta){

        time = time + delta/2;




        //Counter Update
        if(dialog.get(0).isPressed() == true)
        {
            Counter = Counter + 1;
        }
        else
        {
            Counter = 0;
        }

        //dialog
        for(int i=0;i<button.size;i++) {
            if (button.get(i).isPressed() == true) {
                Doctorflag = false;
                stage.addActor(dialog_image);
                stage.addActor(dialog.get(0));
                stage.addActor(dialog.get(1));
                stage.addActor(dialog_label);
                buyRec = i;
            }


            if (dialog.get(0).isPressed() == true) {
                dialog.get(0).remove();
                dialog.get(1).remove();
                dialog_label.remove();

                if(Counter==1) {
                    if(Settings.bought[buyRec]==true){
                        stage.addActor(dialog_label_bought);
                        stage.addActor(backButton);
                    }else if (Settings.TotalCoins < prices[buyRec]) {
                        stage.addActor(dialog_label_No);
                        stage.addActor(backButton);
                    }
                    else
                    {
                        stage.addActor(dialog_label_Yes);
                        Settings.TotalCoins = Settings.TotalCoins - prices[buyRec];
                        Settings.bought[buyRec] = true;
                        Settings.save();
                        stage.addActor(backButton);
                        Counter = Counter + 1;
                    }
                }
            }

            if(backButton.isPressed()==true)
            {
                Doctorflag = true;
                dialog_image.remove();
                dialog_label_Yes.remove();
                dialog_label_No.remove();
                dialog_label_bought.remove();
                backButton.remove();
            }

            if (dialog.get(1).isPressed() == true) {
                dialog.get(0).remove();
                dialog.get(1).remove();
                dialog_image.remove();
                dialog_label.remove();
                Doctorflag = true;
            }
        }

        //bought

        if(Doctorflag) {
            for (int i = 0; i < 6; i++) {
                if (Settings.bought[i] == true) {
                    stage.addActor(image_bought.get(i));
                }
            }
        }
        //doctors

        if(Doctorflag) {
            DoctorsMove(doctors.get(0), doctors.get(1), time * 3, 30 + (240 - button.get(0).getWidth()) / 2 + 50, 22 + 500 - 160 * 0);

            DoctorsMove(doctors.get(2), doctors.get(3), time * 3, -30 + (720 - button.get(3).getWidth()) / 2 + 50, 22 + 500 - 160 * (3 - 3));

            DoctorsMove(doctors.get(4), doctors.get(5), time * 3, 30 + (240 - button.get(0).getWidth()) / 2 + 50, 22 + 500 - 160 * 1);
            DoctorsMove(doctors.get(6), doctors.get(7), time * 3, -30 + (720 - button.get(3).getWidth()) / 2 + 50, 22 + 500 - 160 * (4 - 3));
            DoctorsMove(doctors.get(8), doctors.get(9), time * 3, 30 + (240 - button.get(0).getWidth()) / 2 + 50, 22 + 500 - 160 * 2);
            DoctorsMove(doctors.get(10), doctors.get(11), time * 3, -30 + (720 - button.get(3).getWidth()) / 2 + 50, 22 + 500 - 160 * (5 - 3));
        }


        //TotalCoins

        label_totalCoins.remove();
        label_totalCoins = new Label(Settings.TotalCoins+" ",ls_totalCoins);
        label_totalCoins.setPosition(400,750+7);
        stage.addActor(label_totalCoins);

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
        Texture logo = new Texture(Gdx.files.internal("data/background_BuildingC.png"));
        Image image = new Image(new TextureRegionDrawable( new TextureRegion(logo) ));
        image.setSize(480,800);
        stage.addActor(image);

        //buttons
        Texture actor = new Texture(Gdx.files.internal("data/TV_frame.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = Resource.font.getFont();
        tbs.fontColor = new Color(44/255f,114/255f,227/255f,0.5f);
        tbs.up = new TextureRegionDrawable( new TextureRegion(actor) );


        button.add(new TextButton("", tbs));
        button.add(new TextButton("", tbs));
        button.add(new TextButton("", tbs));
        button.add(new TextButton("", tbs));
        button.add(new TextButton("", tbs));
        button.add(new TextButton("", tbs));


        for(int i =0;i<button.size;i++)
        {
            stage.addActor(button.get(i));
            button.get(i).setSize(150,150);
            button.get(i).setColor(1f,1f,1f,0.8f);
            if(i<button.size/2)
            {
                button.get(i).setPosition(30 + (240-button.get(i).getWidth())/2, 500-160*i);
            }
            else
            {
                button.get(i).setPosition(-30 + (720-button.get(i).getWidth())/2, 500-160*(i-3));
            }
        }

        //doctors

        //first doctor
        Texture logo_doctor = new Texture(Gdx.files.internal("data/image_doctor.png"));
        doctors.add(new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor) )));
        logo_doctor = new Texture(Gdx.files.internal("data/image_doctor2.png"));
        doctors.add(new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor) )));

        //second doctor
        Texture logo_doctor1 = new Texture(Gdx.files.internal("data/doctor_blue1.png"));
        doctors.add(new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor1) )));
        logo_doctor = new Texture(Gdx.files.internal("data/doctor_blue2.png"));
        doctors.add(new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor) )));

        for(int i=0;i<4;i++)
        {
            doctors.add(new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor1) )));
            doctors.add(new Image(new TextureRegionDrawable( new TextureRegion(logo_doctor) )));

        }

        for(int i=0;i<doctors.size;i++)
        {
            doctors.get(i).setSize(50,60);
        }

        //price

        for(int i=0;i<6;i++)
        {
            prices[i] = 10*i;
        }

        //image_price && labels
        Texture logo_price = new Texture(Gdx.files.internal("data/image_price.png"));
        Label.LabelStyle ls_price = new Label.LabelStyle(Resource.Bfont.getFont(), Color.BLACK);
        for(int i=0;i<6;i++) {
            image_prices.add(new Image(new TextureRegionDrawable(new TextureRegion(logo_price))));
            label_prices.add(new Label(prices[i]+" ",ls_price));
        }



        for(int i =0;i<6;i++)
        {
            stage.addActor(image_prices.get(i));
            stage.addActor(label_prices.get(i));
            image_prices.get(i).setSize(100,40);
            image_prices.get(i).setColor(1f,1f,1f,1f);
            label_prices.get(i).setColor(0f,0f,0f,1f);
            if(i<image_prices.size/2)
            {
                image_prices.get(i).setPosition(20+30 + (240-button.get(i).getWidth())/2, 3-10-20 + 500-160*i);
                label_prices.get(i).setPosition(60+30 + (240-button.get(i).getWidth())/2, -20 + 500-160*i);

            }
            else
            {
                image_prices.get(i).setPosition(20 -30 + (720-button.get(i).getWidth())/2,3-10 -20 + 500-160*(i-3));
                label_prices.get(i).setPosition(60 -30 + (720-button.get(i).getWidth())/2, -20 + 500-160*(i-3));

            }
        }


        //TotalCoins
        Texture logo_totalCoins = new Texture(Gdx.files.internal("data/image_price.png"));
        ls_totalCoins = new Label.LabelStyle(Resource.Bfont.getFont(), Color.BLACK);

        Image image_totalCoins = new Image(new TextureRegionDrawable(new TextureRegion(logo_totalCoins)));
        label_totalCoins = new Label(Settings.TotalCoins+" ",ls_totalCoins);

        stage.addActor(image_totalCoins);
        stage.addActor(label_totalCoins);

        label_totalCoins.setColor(0f,0f,0f,1f);
        image_totalCoins.setSize(100,40);
        image_totalCoins.setPosition(360,750);
        label_totalCoins.setPosition(400,750+7);


        //对话框
        //dialog_image
        Texture logo_dialog = new Texture(Gdx.files.internal("data/dialog.png"));
        dialog_image = new Image(new TextureRegionDrawable( new TextureRegion(logo_dialog) ));
        dialog_image.setSize(400,200);
        dialog_image.setPosition((480-dialog_image.getWidth())/2,(800-dialog_image.getHeight())/2);
        dialog_image.setColor(1,1,1,0.9f);


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

        //dialog_labels
        Label.LabelStyle ls = new Label.LabelStyle(Resource.font.getFont(), Color.YELLOW);
        dialog_label = new Label("Do you want to buy this skin?",ls);
        dialog_label.setColor(1f,99/255f,71/255f,1f);
        dialog_label.setPosition((480-dialog_label.getWidth())/2, 50 + (800-dialog_label.getHeight())/2);

        //dialog_label_Yes && No
        dialog_label_Yes = new Label("You have owned a new skin!",ls);
        dialog_label_Yes.setColor(1f,99/255f,71/255f,1f);
        dialog_label_Yes.setPosition((480-dialog_label.getWidth())/2, 50 + (800-dialog_label.getHeight())/2);

        dialog_label_No = new Label("You do not have enough coins!",ls);
        dialog_label_No.setColor(1f,99/255f,71/255f,1f);
        dialog_label_No.setPosition((480-dialog_label.getWidth())/2, 50 + (800-dialog_label.getHeight())/2);

        dialog_label_bought = new Label("You have already bought this skin!",ls);
        dialog_label_bought.setColor(1f,99/255f,71/255f,1f);
        dialog_label_bought.setPosition((480-dialog_label.getWidth())/2, 50 + (800-dialog_label.getHeight())/2);

        //returnButton
        Texture actor3 = new Texture(Gdx.files.internal("data/cloud.png"));
        TextButton.TextButtonStyle tbs3 = new TextButton.TextButtonStyle();
        tbs3.font = tbs.font;
        tbs3.fontColor = tbs.fontColor;
        tbs3.up = new TextureRegionDrawable( new TextureRegion(actor3) );

        returnButton = new TextButton("return",tbs3);
        returnButton.setSize(200,130);
        stage.addActor(returnButton);

        //backButton
        backButton = new TextButton("done",tbs3);
        backButton.setSize(100,70);
        backButton.setPosition(240+dialog_image.getWidth()/4-dialog.get(0).getWidth()/2,20+400-dialog_image.getHeight()/4-dialog.get(0).getHeight()/2);

        //image_bought
        Texture logo_bought = new Texture(Gdx.files.internal("data/bought.png"));
        for(int i=0;i<6;i++) {
            image_bought.add(new Image(new TextureRegionDrawable(new TextureRegion(logo_bought))));
            image_bought.get(i).setSize(80, 60);

            if(i<3)
            {
                image_bought.get(i).setPosition(60+20+30 + (240-button.get(i).getWidth())/2, 120-10-20 + 500-160*i);

            }
            else
            {
                image_bought.get(i).setPosition(60+20 -30 + (720-button.get(i).getWidth())/2,120-10 -20 + 500-160*(i-3));

            }
        }
    }

    private void DoctorsMove(Image doctorJump,Image doctorFall,float time,float Xposition,float Yposition){

        float Dtime = (float)Math.floor(time);

        if(time-Dtime<0.5)
        {
            doctorFall.remove();
            stage.addActor(doctorJump);
            doctorJump.setPosition(Xposition,Yposition - 200 * (time-Dtime)*(time-Dtime) + 200 * (time-Dtime));
        }
        else
        {
            doctorJump.remove();
            stage.addActor(doctorFall);
            doctorFall.setPosition(Xposition,Yposition - 200 * (time-Dtime)*(time-Dtime) + 200 * (time-Dtime));
        }

    }
}
