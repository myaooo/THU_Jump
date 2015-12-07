package com.mygdx.jump.GameScreen;

/**
 * GameStage, wrapped game logic in the stage
 * Created by Yao on 15/12/2.
 */

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.Settings;

// This is a class that contains all the objects in a game
public class GameStage extends Stage{

    // fields
    // static fields
    /**Gravity in the stage, changes the velocity of objects*/
    static final Vector2 GRAVITY = new Vector2(0,-10f);
    public static final int STATE_RUNNING = 0;
    public static final int STATE_NEXT_LEVEL = 1;
    public static final int STATE_GAME_OVER = 2;

    // private fields
    private final Doctor doctor = new Doctor();
    private final ArrayList<Floor> floors = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Monster> monsters = new ArrayList<>();
    public int score = 0;
    public float currentHeight = 0;
    private int state = STATE_RUNNING;


    public GameStage(){
        ScalingViewport viewport =
                new ScalingViewport(Scaling.fit, Settings.SCREEN_WIDTH,Settings.SCREEN_HEIGHT);
        this.setViewport(viewport);

    }

    public GameStage(Viewport viewport){
        super(viewport);
    }

    private void AddFloor(){

    }

    /**Check whether the game is over (the doctor falls under current height)*/
    public boolean isGameOver(){
        if (doctor.getY() < currentHeight){
            state = STATE_GAME_OVER;
            return true;
        }
        return false;
    }

}
