package com.mygdx.jump.GameScreen;

/**
 * GameStage, wrapped game logic in the stage
 * @author Ming Yao
 */

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.GameScreen.GameItem.Spring;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Resource.Font;
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
    public static final float MAX_JUMP_HEIGHT = 7;
    public static final float GRAVITY_ABS = 30;
    public static final float NORMAL_JUMP_VELOCITY = (float) Math.sqrt(2 * GRAVITY_ABS * MAX_JUMP_HEIGHT);
    public static final float[] HEIGHT_INTERVAL = {0.4f,0.65f,0.8f,0.95f};
    static final Vector2 GRAVITY = new Vector2(0, -GRAVITY_ABS);
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_GAME_OVER = 1;
    public static final float HEIGHT_LEVEL_BASE = WORLD_HEIGHT * 2;
    final static String SCORE = "SCORE: ";

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
    private String scoreString;
    private Label scoreLabel;
    private Mediator mediator;

    /**
     * Default constructor set the viewport to scalingViewport and screen dimension
     */
    public GameStage(Mediator media) {
        OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        FitViewport viewport =
                new FitViewport(WORLD_WIDTH, 10000*WORLD_HEIGHT,camera);
        viewport.setScreenBounds(0,0,Settings.SCREEN_WIDTH,Settings.SCREEN_HEIGHT);
        this.setViewport(viewport);
        mediator = media;
        initializeFloor();
        background = Assets.getBackground();
        addScoreLabel();
    }

    /**Several public get functions*/

    /**get Score*/
    public int getScore(){
        return score;
    }

    /**get Height*/
    public float getHeight(){
        return currentHeight;
    }

    /**get coins*/
    public int getCoins(){
        return coins;
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
        if (!doctor.isHit())
            checkCollisions();
        updateStatus();
        stateTime += deltaTime;
        while (floorHeight < currentHeight + WORLD_HEIGHT) {
            generateFloor();
            genItem();
        }
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
            float floorX = rand.nextFloat() * (WORLD_WIDTH-Floor.FLOOR_WIDTH);
            float floorY = MAX_JUMP_HEIGHT*0.95f;
            if (level < 4){
                floorY *= HEIGHT_INTERVAL[level-1];
            }
            int type = rand.nextInt(6);
            if (type < level-2) type = Floor.FLOOR_TYPE_MOVABLE;
            else    type = Floor.FLOOR_TYPE_STATIC;
            floorHeight += floorY;
            Floor fl = new Floor(type,floorX,floorHeight);
            floors.add(fl);
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
        int len = items.size();
        for (int i = 0; i < len; ++i) {
            Item it = items.get(i);
            it.update(deltaTime);
            if (it.getY() < currentHeight) {
                // the floor is broken and should be removed.
                floors.remove(it);
                len--;
            }
        }
    }

    /**
     * Update level
     */
    public void updateLevel() {
        if (currentHeight > next_level_height) {
            level++;
            next_level_height *= 2;
        }
        score = (int) currentHeight*2;
    }

    /**
     * Update Doctor
     */
    public void updateDoctor(float deltaTime) {
        int moveDirection = mediator.getDirection();
        doctor.setMoveDirection(moveDirection);
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
        if ( camera.position.y < doctor.getY()+2){
            camera.position.y = doctor.getY()+2;
            currentHeight =  camera.position.y - WORLD_HEIGHT/2;
            //
            updateLevel();
            //scoreLabel.setPosition(0,currentHeight+3f);
        }
    }

    private void genBullet(){
        if (mediator.isShootBullet()&&(stateTime > 0.5f)){
            Bullet blt;
            if (monsters.size()!=0) blt = new Bullet(doctor, monsters.get(0));
            else blt = new Bullet(doctor);
            bullets.add(blt);
            stateTime = 0;
        }
    }

    private void genItem(){
        // generate springs
        float rate = Spring.getRate();
        if (rand.nextFloat() < rate) genSpring();
    }

    private void genSpring(){
        Floor fl = floors.get(floors.size()-1);
        Spring spr = new Spring(fl);
        items.add(spr);
    }

    private void genJumper(){

    }

    private void genReversor(){

    }

    private void genRocket(){

    }

    private void genShield(){

    }
    private void genFloater(){

    }

    /**
     * Check all kinds of collisions
     */
    public void checkCollisions(){
        checkHittingFloor();
        checkHittingMonster();
        checkHittingItem();
    }


    /**
     * Check whether doctor hits a floor and update doctor and floor status
     */
    public boolean checkHittingFloor() {
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
    public boolean checkHittingMonster() {
        if (doctor.isShielded())
            // the doctor is shielded
            return false;
        for (Monster mst : monsters) {
            if (doctor.getY() > mst.getTop()) {
                if (doctor.overlaps(mst)) {
                    // doctor hit a floor
                    doctor.hitMonster();
                    return true;
                }
            }
        }
        return false;
    }

    public void checkHittingItem(){
        for (Item it: items){
            if (it.checkHitDoctor(doctor)) {
                it.hitDoctor(doctor);
                if (it.isUsable()){
                    doctor.getItem(it);
                }
            }
        }
    }

    /**
     * Return true if the game is over.
     */
    public boolean isGameOver() {
        return status == STATUS_GAME_OVER;
    }

    /**
     * Check whether the game is over (the doctor falls under current height) and update the status
     */
    public void updateStatus(){
        if (doctor.getY() < currentHeight || doctor.isHit()) {
            status = STATUS_GAME_OVER;
        }
    }

    @Override
    public void draw(){
        Camera camera = getCamera();
        camera.update();

        Batch batch = getBatch();
        if (batch != null) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
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
            scoreLabel.draw(batch,1);
            batch.end();
        }
    }

    public void drawScore(Batch batch){
        Assets.defaultFont.draw(batch,SCORE+score,6f,12f);
    }

    public void addScoreLabel(){
        BitmapFont font1 = Assets.getDefaultFont();
        Label.LabelStyle ls = new Label.LabelStyle(font1, Color.YELLOW);
        scoreLabel = new Label("Tsinghua Jump",ls);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setColor(0f,1f,0f,1f);
        scoreLabel.setFontScale(0.02f);
        scoreLabel.setPosition(0,12);

    }
}
