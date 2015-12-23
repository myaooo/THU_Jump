package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
 * Created by Yao on 15/12/2.
 */

/**
 * This is the main game screen, which contains a gamestage to operate game logic
 */
public class GameScreen extends ScreenAdapter {

    // fields
    // static fields
    final static int GAME_READY = 0;
    final static int GAME_RUNNING = 1;
    final static int GAME_OVER = 2;
    final static int GAME_PAUSE = 3;

    final static String SCORE = "SCORE ";
    final static String COIN = "COIN ";

    // private class fields
    private Stage backStage;
    private GameStage gameStage;
    private Stage coverStage;
    private TsinghuaJump game;
    private int status;
    private Mediator mediator;
    private Button pauseButton;
    private float stateTime = 0;
    private Image background;

    private Label scoreLabel;
    private Label coinLabel;


    /**constructor*/
    public GameScreen(TsinghuaJump inGame){
        this.game = inGame;
        mediator = new Mediator();
        gameStage = new GameStage(mediator);
        coverStage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        backStage = new Stage(new ScalingViewport(Scaling.stretch, 480, 800, new OrthographicCamera()));
        Gdx.input.setInputProcessor(coverStage);
        status = GAME_RUNNING;
        initializeCover();
        initializeBack();
    }

    /**Initializing Cover Stage*/
    private void initializeCover(){
        // Set Button
        Button.ButtonStyle stl = new Button.ButtonStyle();
        stl.up = new TextureRegionDrawable(Assets.getPauseUp());
        stl.checked = new TextureRegionDrawable(Assets.getPauseUp());
        stl.down = new TextureRegionDrawable(Assets.getPauseDown());
        pauseButton = new Button(stl);
        pauseButton.setBounds(410, 730, 50, 50);
        coverStage.addActor(pauseButton);
        addScoreLabel();
    }

    /**Initializing Back Stage*/
    private void initializeBack(){
        background = new Image(Assets.getBackground());
        background.setBounds(0,0,480,800);
        backStage.addActor(background);
    }

    /**Add a score label which is in the top left corner of the screen to the stage*/
    public void addScoreLabel(){
        BitmapFont font1 = Assets.getDefaultFont();
        Label.LabelStyle ls = new Label.LabelStyle(font1, Color.WHITE);
        scoreLabel = new Label(SCORE+0,ls);
        scoreLabel.setColor(Settings.myDarkBlue);
        scoreLabel.setFontScale(1.2f,1);
        scoreLabel.setPosition(10, 760);
        scoreLabel.setAlignment(Align.bottomLeft);
        coverStage.addActor(scoreLabel);

        coinLabel = new Label(COIN+0,ls);
        coinLabel.setAlignment(Align.bottomLeft);
        coinLabel.setColor(Settings.myGoldYellow);
        coinLabel.setFontScale(1.2f,1);
        coinLabel.setPosition(200, 760);
        coverStage.addActor(coinLabel);
    }


    /**Calls before rendering*/
    private void update(float delta){
        switch(status){
            case GAME_RUNNING:
                updateRunning(delta);
                break;
            case GAME_PAUSE:
                updatePause(delta);
                break;
            case GAME_OVER:
                updateOver();
                break;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        switch(status){
            case GAME_OVER:
                drawOver();
                break;
            default:
                drawRunning();
                break;
        }
    }

    public void updateRunning(float delta){
        // check inputs
        if (Gdx.input.isKeyPressed(Settings.KEY_LEFT))
            mediator.setLeft();
        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT))
            mediator.setRight();
        if (Gdx.input.isKeyPressed(Settings.KEY_SHOOT))
            mediator.doctorShoot();
        if (Gdx.input.isKeyPressed(Settings.KRY_USE_ITEM))
            mediator.useItem();
        if (pauseButton.isChecked() && stateTime > 0.5f) {
            pauseButton.toggle();
            status = GAME_PAUSE;
            stateTime = 0;
        }
        // update game stage
        gameStage.update(delta);
        mediator.reset();

        // update score and coin label
        scoreLabel.setText(SCORE+gameStage.getScore());
        coinLabel.setText(COIN+gameStage.getCoins());

        // check game over
        if (gameStage.isGameOver()) {
            this.setGameOver();
        }
        stateTime += delta;
    }

    public void updatePause(float delta){
        if (pauseButton.isChecked() && stateTime > 0.5f) {
            pauseButton.toggle();
            status = GAME_RUNNING;
            stateTime = 0;
        }
        gameStage.update(0);
        stateTime += delta;
    }

    public void updateOver(){
        if (Gdx.input.isTouched())
            game.setScreen(new MainMenuScreen(game));
    }

    public void drawRunning(){
        backStage.draw();
        gameStage.draw();
        coverStage.draw();
    }

    public void drawOver(){
        backStage.draw();
        gameStage.draw();
        coverStage.draw();
    }

    public void setGameOver(){
        status = GAME_OVER;
        GameOverActor gameOverActor = new GameOverActor();
        coverStage.addActor(gameOverActor);
        scoreLabel.setFontScale(2.5f,2f);
        float strwidth = scoreLabel.getPrefWidth();
        scoreLabel.setColor(Settings.myGoldYellow);
        scoreLabel.setPosition(240-strwidth/2,240);
    }

    @Override
    public void resize(int width, int height) {

        Settings.setScreen(width, height);
    }

    @Override
    public void dispose(){
        gameStage.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide(){

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }



}
