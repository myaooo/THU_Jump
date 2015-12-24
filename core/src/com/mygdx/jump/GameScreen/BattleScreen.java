package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;

/**
 * Created by Yao on 15/12/23.
 */

public class BattleScreen extends GameScreen {

    public static float SCREEN_WIDTH = 480;
    public static float SCREEN_HEIGHT = 800;

    protected Label scoreLabel2;
    protected Label coinLabel2;
    protected Image itemPackage2;
    protected Image background2;
    protected Mediator mediator2;

    public BattleScreen(TsinghuaJump inGame){
        super(inGame);
        Gdx.graphics.setDisplayMode(Settings.SCREEN_WIDTH*2,Settings.SCREEN_HEIGHT,false);
    }

    @Override
    public void drawRunning(){
        gameStage.draw();
    }

    @Override
    public void drawOver(){
        gameStage.draw();
    }

    @Override
    protected void initialzeGame(){
        mediator = new Mediator();
        mediator2 = new Mediator();
        gameStage = new BattleStage(this, mediator,mediator2);
    }

    /**Initializing Cover Stage*/
    @Override
    protected void initializeCover(){
        coverStage = new Stage(new ScalingViewport(Scaling.stretch, SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera()));
        coverStage2 = new Stage(new ScalingViewport(Scaling.stretch, SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera()));
        // Set Button
        Button.ButtonStyle stl = new Button.ButtonStyle();
        stl.up = new TextureRegionDrawable(Assets.getPauseUp());
        stl.checked = new TextureRegionDrawable(Assets.getPauseUp());
        stl.down = new TextureRegionDrawable(Assets.getPauseDown());
        pauseButton = new Button(stl);
        pauseButton.setBounds(410, 730, 50, 50);
        coverStage.addActor(pauseButton);
        // set item package
        itemPackage = new Image(Assets.getItemPackage());
        itemPackage.setBounds(12,12,80,80);
        coverStage.addActor(itemPackage);
        itemPackage2 = new Image(Assets.getItemPackage());
        itemPackage2.setBounds(12,12,80,80);
        coverStage2.addActor(itemPackage2);
        addScoreLabel();
    }

    /**Add a score label which is in the top left corner of the screen to the stage*/
    @Override
    public void addScoreLabel(){
        super.addScoreLabel();
        BitmapFont font1 = Assets.getDefaultFont();
        Label.LabelStyle ls = new Label.LabelStyle(font1, Color.WHITE);
        scoreLabel2 = new Label(SCORE+0,ls);
        scoreLabel2.setColor(Settings.myDarkBlue);
        scoreLabel2.setFontScale(1.2f,1);
        scoreLabel2.setPosition(10, 760);
        scoreLabel2.setAlignment(Align.bottomLeft);
        coverStage2.addActor(scoreLabel2);

        coinLabel2 = new Label(COIN+0,ls);
        coinLabel2.setAlignment(Align.bottomLeft);
        coinLabel2.setColor(Settings.myGoldYellow);
        coinLabel2.setFontScale(1.2f,1);
        coinLabel2.setPosition(200, 760);
        coverStage2.addActor(coinLabel2);
    }

    @Override
    public void updateLabels(){
        super.updateLabels();
        scoreLabel2.setText(SCORE+gameStage.getScore2());
        coinLabel2.setText(COIN+gameStage.getCoins2());
    }

    @Override
    public void updateRunning(float delta){
        // check inputs
        if (Gdx.input.isKeyPressed(Settings.KEY_LEFT))
            mediator.setLeft();
        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT))
            mediator.setRight();
        //if (Gdx.input.isKeyPressed(Settings.KEY_UP))
            //mediator.setUp();
        //if (Gdx.input.isKeyPressed(Settings.KEY_DOWN))
            //mediator.setDown();
        if (Gdx.input.isKeyPressed(Settings.KEY_SHOOT))
            mediator.doctorShoot();
        if (Gdx.input.isKeyPressed(Settings.KRY_USE_ITEM))
            mediator.useItem();

        if (Gdx.input.isKeyPressed(Settings.KEY_LEFT2))
            mediator2.setLeft();
        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT2))
            mediator2.setRight();
        if (Gdx.input.isKeyPressed(Settings.KEY_SHOOT2))
            mediator2.doctorShoot();
        if (Gdx.input.isKeyPressed(Settings.KEY_USE_ITEM2))
            mediator2.useItem();
        if (pauseButton.isChecked() && stateTime > 0.5f) {
            pauseButton.toggle();
            status = GAME_PAUSE;
            stateTime = 0;
        }
        // update game stage
        gameStage.update(delta);
        mediator.reset();
        mediator2.reset();

        // update score and coin label
        updateLabels();

        // check game over
        if (gameStage.isGameOver()) {
            this.setGameOver();
        }
        stateTime += delta;
    }

    /**Calls when the game is over, set game over actor*/
    public void setGameOver(){
        status = GAME_OVER;
        Assets.stopMusic(BGM);
        GameOverActor gameOverActor = new GameOverActor();
        if (gameStage.status == BattleStage.STATUS_GAME_OVER)
            coverStage.addActor(gameOverActor);
        else    coverStage2.addActor(gameOverActor);
        scoreLabel.setFontScale(2.5f,2f);
        float strwidth = scoreLabel.getPrefWidth();
        scoreLabel.setColor(Settings.myGoldYellow);
        scoreLabel.setPosition(240-strwidth/2,240);

        scoreLabel2.setFontScale(2.5f,2f);
        float strwidth2 = scoreLabel2.getPrefWidth();
        scoreLabel2.setColor(Settings.myGoldYellow);
        scoreLabel2.setPosition(240-strwidth/2,240);
        //record.setRecord(gameStage.getScore(),gameStage.getCoins());
    }



}
