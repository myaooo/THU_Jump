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
    public static final float GRAVITY_ABS = 30;
    public static final float NORMAL_JUMP_VELOCITY = (float) Math.sqrt(2 * GRAVITY_ABS * MAX_JUMP_HEIGHT);
    public static final float[] HEIGHT_INTERVAL = {0.4f,0.65f,0.8f,0.9f,1f};
    static final Vector2 GRAVITY = new Vector2(0, -GRAVITY_ABS);
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_GAME_OVER = 1;
    public static final float HEIGHT_LEVEL_BASE = WORLD_HEIGHT * 10;

    // class fields
    private final Doctor doctor = new Doctor();
    private final ArrayList<Floor> floors = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Monster> monsters = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    public int score = 0;
    public float currentHeight = 0;
    public float floorHeight = 0;
    public int level = 1;
    public float next_level_height = HEIGHT_LEVEL_BASE;
    public int coins = 0;
    private int status = STATUS_RUNNING;
    private Random rand = new Random();
    private TextureRegion background;
    private float stateTime = 0;

    private Mediator mediator;

    /**
     * Default constructor set the viewport to scalingViewport and screen dimension
     */
    public GameStage(Mediator media) {
        OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        FitViewport viewport =
                new FitViewport(WORLD_WIDTH, 1000*WORLD_HEIGHT,camera);
        viewport.setScreenBounds(0,0,Settings.SCREEN_WIDTH,Settings.SCREEN_HEIGHT);
        this.setViewport(viewport);
        mediator = media;
        initializeFloor();
        background = Assets.getBackground();

    }

    /**
     * Update
     */
    public void update(float deltaTime) {
        updateCamera();
        updateDoctor(deltaTime);
        updateFloors(deltaTime);
        updateMonsters(deltaTime);
        updateItems(deltaTime);
        updateBullets(deltaTime);
        updateLevel();
        if (!doctor.isHit())
            checkCollisions();
        isGameOver();
        stateTime += deltaTime;
    }

    /**
     * Generate Floors
     */
    private void initializeFloor() {
        Floor fl = new Floor(Floor.FLOOR_TYPE_STATIC,getWidth()/2,0.5f);
        floors.add(fl);
        /*while (floorHeight < currentHeight + this.getHeight()) {

        }*/
        floorHeight = fl.getHeight();
        generateFloor();
    }

    private void generateFloor(){
        while (floorHeight < currentHeight + WORLD_HEIGHT){
            float floorX = rand.nextFloat() * WORLD_WIDTH;
            float floorY = MAX_JUMP_HEIGHT;
            int type;
            if (level < 5){
                floorY *= HEIGHT_INTERVAL[level-1];
            }
            floorHeight += floorY;
            Floor fl = new Floor(Floor.FLOOR_TYPE_STATIC,floorX,floorHeight);
            floors.add(fl);

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
        generateFloor();
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
    public void updateDoctor(float deltaTime) {
        int moveDirection = mediator.getDirection();
        doctor.setVelocityX(moveDirection);
        doctor.update(deltaTime);
    }

    /**
     * Update Bullets
     */
    public void updateBullets(float deltaTime) {
        int len = bullets.size();
        for (int i = 0; i < len; ++i) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (!bullet.isNormal()) {
                // the floor is broken and should be removed.
                bullets.remove(bullet);
                len--;
            }
        }
        genBullet();
    }

    /**
     * Update Camera
     */
    public void updateCamera() {
        Camera camera = getCamera();
        if ( camera.position.y < doctor.getY()){
            camera.position.y = doctor.getY();
            currentHeight =  camera.position.y - WORLD_HEIGHT/2;
        }
    }

    private void genBullet(){
        if (mediator.isShootBullet()){
            Bullet blt;
            if (monsters.size()!=0) blt = new Bullet(doctor, monsters.get(0));
            else blt = new Bullet(doctor);
            bullets.add(blt);
        }
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
        if (!doctor.isFalling())
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
//        if (doctor.getY() < currentHeight) {
//            status = STATUS_GAME_OVER;
//            return true;
//        }
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
            float w = getWidth();
            float h = getHeight();
            batch.draw(background,0,currentHeight,WORLD_WIDTH,WORLD_HEIGHT);
            for (Floor fl:floors){
                fl.draw(batch,1);
            }
            for(Monster ms:monsters){
                ms.draw(batch,1);
            }
            for(Item it:items){
                it.draw(batch,1);
            }
            for (Bullet blt:bullets){
                blt.draw(batch,1);
            }
            doctor.draw(batch,1);
            batch.end();
        }
    }
}
