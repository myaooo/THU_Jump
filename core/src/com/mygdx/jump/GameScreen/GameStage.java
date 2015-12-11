package com.mygdx.jump.GameScreen;

/**
 * GameStage, wrapped game logic in the stage
 * @author Ming Yao
 */

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.GameScreen.GameItem.Mediator;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;

// This is a class that contains all the objects in a game
public class GameStage extends Stage {

    // fields
    // static fields
    /**
     * Gravity in the stage, changes the velocity of objects
     */
    public static final float WORLD_WIDTH = 12;
    public static final float WORLD_HEIGHT = 20;
    public static final float MAX_JUMP_HEIGHT = 8;
    public static final float GRAVITY_ABS = 10;
    public static final float NORMAL_JUMP_VELOCITY = (float)Math.sqrt(2*MAX_JUMP_HEIGHT*GRAVITY_ABS);
    static final Vector2 GRAVITY = new Vector2(0, -GRAVITY_ABS);
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_GAME_OVER = 1;
    public static final float HEIGHT_LEVEL_BASE = WORLD_HEIGHT * 10;

    // class fields
    private final Doctor doctor = new Doctor();
    private final ArrayList<Floor> floors = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Monster> monsters = new ArrayList<>();
    public int score = 0;
    public float currentHeight = 0;
    public float floorHeight = 0;
    public int level = 1;
    public float next_level_height = HEIGHT_LEVEL_BASE;
    public int coins = 0;
    private int status = STATUS_RUNNING;
    private Random rand = new Random();
    private TextureRegion background;

    /**
     * Default constructor set the viewport to scalingViewport and screen dimension
     */
    public GameStage() {
        OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        FitViewport viewport =
                new FitViewport(WORLD_WIDTH, WORLD_HEIGHT,camera);
        viewport.setScreenBounds(0,0,Settings.SCREEN_WIDTH,Settings.SCREEN_HEIGHT);
        this.setViewport(viewport);
        initializeFloor();
        int debugScreenY = viewport.getScreenHeight();
        int debugScreenX = viewport.getScreenWidth();
        float debugWorldY = viewport.getWorldHeight();
        float debugWorldX = viewport.getWorldWidth();
        background = Assets.getBackground();

    }

    /**
     * Update
     */
    public void update(float deltaTime, Mediator mediator) {
        int moveDirection = mediator.getDirection();
        updateDoctor(deltaTime, moveDirection);
        updateFloors(deltaTime);
        updateMonsters(deltaTime);
        updateItems(deltaTime);
        updateLevel();
        if (doctor.isHit())
            checkCollisions();
        isGameOver();
    }

    /**
     * Generate Floors
     */
    private void initializeFloor() {
        Floor fl = new Floor(Floor.FLOOR_STATUS_NORMAL,getWidth()/2,0.5f);
        floors.add(fl);
        /*while (floorHeight < currentHeight + this.getHeight()) {

        }*/
        generateFloor();
    }

    private void generateFloor(){

    }

    /**
     * Update Floors
     */
    public void updateFloors(float deltaTime) {
        // update each floor
        int len = floors.size();
        for (int i = 0; i < len; ++i) {
            Floor fli = floors.get(i);
            fli.update(deltaTime);
            if (fli.isBroken()) {
                // the floor is broken and should be removed.
                floors.remove(fli);
                len--;
            }
        }
    }

    /**
     * Update Monsters
     */
    public void updateMonsters(float deltaTime) {
        int len = monsters.size();
        for(int i = 0; i < len; ++i){
            Monster ms = monsters.get(i);
            // do updates here;
        }
    }

    /**
     * Update Items
     */
    public void updateItems(float deltaTime) {

    }

    /**
     * Update level
     */
    public void updateLevel() {
        if (currentHeight > next_level_height) {
            level++;
            next_level_height *= 2;
        }
    }

    /**
     * Update Doctor
     */
    public void updateDoctor(float deltaTime, int moveDirection) {
        doctor.setVelocityX(moveDirection);
        doctor.update(deltaTime);
    }

    /**
     * Check all kinds of collisions
     */
    public void checkCollisions(){
        isHittingFloor();
        isHittingMonster();
    }


    /**
     * Check whether doctor hits a floor and update doctor and floor status
     */
    public boolean isHittingFloor() {
        if (doctor.isFalling())
            return false;
        for (Floor fl : floors) {
            if (doctor.getY() > fl.getY()) {
                if (doctor.overlaps(fl)) {
                    // doctor hit a floor
                    doctor.hitFloor();
                    if (fl.isBreakable())
                        fl.floorBreak();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether doctor hits a monster and update doctor status
     */
    public boolean isHittingMonster() {
        if (doctor.isShielded())
            // the doctor is shielded
            return false;
        for (Monster mst : monsters) {
            if (doctor.getY() > mst.getY()) {
                if (doctor.overlaps(mst)) {
                    // doctor hit a floor
                    doctor.hitMonster();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the game is over (the doctor falls under current height) and update the status
     */
    public boolean isGameOver() {
        if (doctor.getY() < currentHeight) {
            status = STATUS_GAME_OVER;
            return true;
        }
        return false;
    }

    @Override
    public void draw(){
        Camera camera = getCamera();
        camera.update();

        Batch batch = getBatch();
        if (batch != null) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            doctor.draw(batch,1);
            for (Floor fl:floors){
                fl.draw(batch,1);
            }
            for(Monster ms:monsters){
                ms.draw(batch,1);
            }
            for(Item it:items){
                it.draw(batch,1);
            }
            float w = getWidth();
            float h = getHeight();
            batch.draw(background,0,0,w,h);
            batch.end();
        }
    }
}
