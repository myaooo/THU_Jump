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
import com.mygdx.jump.MenuScreen.MainMenuScreen;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;
import com.mygdx.jump.TsinghuaJump;

/**
 * Created by Yao on 15/12/23.
 */

public class BattleScreen extends GameScreen {

    public static float SCREEN_WIDTH = 480;
    public static float SCREEN_HEIGHT = 800;
    public static int STATUS_DIED = 1;

    protected Label scoreLabel2;
    protected Label coinLabel2;
    protected Image itemPackage2;
    protected Image background2;
    protected Mediator mediator2;
    private GameOverActor gameOverActor = new GameOverActor();
    private GameOverActor gameOverActor2 = new GameOverActor();
    private int status1 = GAME_RUNNING;
    private int status2 = GAME_RUNNING;

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
            mediator2.setLeft();
        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT))
            mediator2.setRight();
        if (Gdx.input.isKeyPressed(Settings.KEY_SHOOT))
            mediator2.doctorShoot();
        if (Gdx.input.isKeyPressed(Settings.KRY_USE_ITEM))
            mediator2.useItem();

        if (Gdx.input.isKeyPressed(Settings.KEY_LEFT2))
            mediator.setLeft();
        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT2))
            mediator.setRight();
        if (Gdx.input.isKeyPressed(Settings.KEY_SHOOT2))
            mediator.doctorShoot();
        if (Gdx.input.isKeyPressed(Settings.KEY_USE_ITEM2))
            mediator.useItem();

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
        if (status1 == GAME_RUNNING && gameStage.status == GameStage.STATUS_GAME_OVER){
            this.setGameOver1();
            //System.out.print(gameStage.status2);
        }
        if (status2 == GAME_RUNNING && gameStage.status2 == GameStage.STATUS_GAME_OVER){
            this.setGameOver2();
        }
        if (status1 == STATUS_DIED && status2 == STATUS_DIED) {
            this.setGameOver();
        }
        stateTime += delta;
    }

    @Override
    public void updateOver(){
        if (Gdx.input.isTouched()){
            Gdx.graphics.setDisplayMode(480,800,false);
            Gdx.gl.glDisable(GL20.GL_SCISSOR_BOX);
            game.setScreen(new MainMenuScreen(game));
        }
    }

    /**Calls when the game is over, set game over actor*/
    @Override
    public void setGameOver(){
        status = GAME_OVER;
        Assets.stopMusic(BGM);
        gameOverActor.stateTime = 0;
        gameOverActor2.stateTime = 0;
        if (gameStage.getScore() == gameStage.getScore2()) {
            gameOverActor.anim = Assets.getYouWinAinm();
            gameOverActor2.anim = Assets.getYouWinAinm();
        }
        else if (gameStage.getScore() < gameStage.getScore2()){
            gameOverActor2.anim = Assets.getYouWinAinm();
        }
        else{
            gameOverActor.anim = Assets.getYouWinAinm();
        }
        scoreLabel.setFontScale(2.5f,2f);
        float strwidth = scoreLabel.getPrefWidth();
        scoreLabel.setColor(Settings.myGoldYellow);
        scoreLabel.setPosition(240-strwidth/2,240);

        scoreLabel2.setFontScale(2.5f,2f);
        float strwidth2 = scoreLabel2.getPrefWidth();
        scoreLabel2.setColor(Settings.myGoldYellow);
        scoreLabel2.setPosition(240-strwidth2/2,240);
    }

    /**Calls when the game is over, set game over actor*/
    public void setGameOver1(){
        status1 = STATUS_DIED;
        coverStage.addActor(gameOverActor);
        scoreLabel.setFontScale(2.5f,2f);
        float strwidth = scoreLabel.getPrefWidth();
        scoreLabel.setColor(Settings.myGoldYellow);
        scoreLabel.setPosition(240-strwidth/2,240);
        itemPackage.remove();
    }

    public void setGameOver2(){
        this.status2 = STATUS_DIED;
        coverStage2.addActor(gameOverActor2);
        scoreLabel2.setFontScale(2.5f,2f);
        float strwidth = scoreLabel2.getPrefWidth();
        scoreLabel2.setColor(Settings.myGoldYellow);
        scoreLabel2.setPosition(240-strwidth/2,240);
        itemPackage2.remove();
    }




}
