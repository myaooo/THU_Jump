package com.mygdx.jump.GameScreen;

/**
 * GameStage, wrapped game logic in the stage
 * @author Ming Yao
 */

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.Settings;

// This is a class that contains all the objects in a game
public class GameStage extends Stage {

    // fields
    // static fields
    /**
     * Gravity in the stage, changes the velocity of objects
     */
    static final Vector2 GRAVITY = new Vector2(0, -10f);
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_GAME_OVER = 1;
    public static final float HEIGHT_LEVEL_BASE = Settings.WORLD_HEIGHT * 10;

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


    /**
     * Default constructor set the viewport to scalingViewport and screen dimension
     */
    public GameStage() {
        ScalingViewport viewport =
                new ScalingViewport(Scaling.fit, Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT);
        this.setViewport(viewport);

    }

    /**
     * inherited constructor from stage
     */
    public GameStage(Viewport viewport) {
        super(viewport);
    }

    /**
     * Update
     */
    public void update(float deltaTime) {
        updateDoctor(deltaTime);
        updateFloors(deltaTime);
        updateMonsters(deltaTime);
        updateItems(deltaTime);
        updateLevel();
        if (doctor.isHitted())
            checkCollisions();
        isGameOver();
    }

    /**
     * Generate Floors
     */
    private void generateFloor() {
        while (floorHeight < currentHeight + this.getHeight()) {

        }
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
    public void updateDoctor(float deltaTime) {

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
}
