package com.mygdx.jump.GameScreen;

/**
 * GameStage, wrapped game logic in the stage
 * @author Ming Yao
 */

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.jump.GameScreen.GameItem.*;
import com.mygdx.jump.GameScreen.Monster.Monster;
import com.mygdx.jump.GameScreen.Monster.MonsterBoss;
import com.mygdx.jump.GameScreen.Monster.MonsterHole;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;

// This is a class that contains all the objects in a game
public class GameStage extends Stage {

    // fields
    // static fields
    // WORLD CONSTANT
    public static final float WORLD_WIDTH = 12;
    public static final float WORLD_HEIGHT = 20;
    public static final float MAX_JUMP_HEIGHT = 6;
    public static final float GRAVITY_ABS = 30;
    public static final float NORMAL_JUMP_VELOCITY = (float) Math.sqrt(2 * GRAVITY_ABS * MAX_JUMP_HEIGHT);

    // GAME PARAMETERS
    public static final float[] HEIGHT_INTERVAL = {0.5f,0.6f,0.7f,0.8f,0.9f};
    //static final Vector2 GRAVITY = new Vector2(0, -GRAVITY_ABS);
    public static final float HEIGHT_LEVEL_BASE = WORLD_HEIGHT * 3;
    public static float SHOOTING_SPEED = 0.3f;  // shooting speed of the doctor

    // STATUS PARAMS
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_GAME_OVER = 1;

    // OTHER CONSTANTS
    final static String SCORE = "SCORE ";
    final static String COIN = "COIN ";
    final static int SCORE_SCALE = 10;

    // class fields
    // Game Objects
    public Doctor doctor;
    protected final ArrayList<Floor> floors = new ArrayList<>();
    protected final ArrayList<Item> items = new ArrayList<>();
    protected final ArrayList<Monster> monsters = new ArrayList<>();
    protected final ArrayList<Bullet> bullets = new ArrayList<>();
    protected final ArrayList<Coin> coins = new ArrayList<>();
    // other params
    public Sound FALLSOUND = Gdx.audio.newSound(Gdx.files.internal("data/sound/fall.wav"));
    public int score = 0;
    public float floorHeight = 0;
    public float monsterHeight = 0;
    public int level = 1;
    public float next_level_height = HEIGHT_LEVEL_BASE;
    protected int status = STATUS_RUNNING;
    protected int status2 = STATUS_RUNNING;
    protected Random rand = new Random();
    protected float stateTime = 0;
    protected Mediator mediator;
    protected GameScreen gameScreen;
    protected OrthographicCamera camera;

    /**
     * Default constructor set the viewport to scalingViewport and screen dimension
     */
    public GameStage(GameScreen screen, Mediator media) {
        gameScreen = screen;
        camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        FitViewport viewport =
                new FitViewport(WORLD_WIDTH, 10000*WORLD_HEIGHT,camera);
        viewport.setScreenBounds(0,0,Settings.SCREEN_WIDTH,Settings.SCREEN_HEIGHT);
        this.setViewport(viewport);
        //status = STATUS_RUNNING;
        mediator = media;
        initialize();
    }

    /**Initialize function, calls when the stage should be initialize*/
    protected void initialize(){
        initializeFloor();
        doctor = new Doctor(this);
    }

    /**Several public get functions*/

    /**get Score*/
    public int getScore(){
        return score;
    }

    /**Empty api for battle stage*/
    public int getScore2(){return 0;}

    /**get Height*/
    public float getCurrentHeight(){
        return doctor.currentHeight;
    }

    /**Empty api for battle stage*/
    public float getCurrentHeight2(){
        return doctor.currentHeight;
    }

    /**get coins*/
    public int getCoins(){
        return doctor.coins;
    }

    /**Empty api for battle stage*/
    public int getCoins2(){return 0;}

    /**
     * Update
     */
    public void update(float deltaTime) {
        updateHeights(deltaTime);
        updateDoctor(deltaTime);
        updateFloors(deltaTime);
        updateMonsters(deltaTime);
        updateItems(deltaTime);
        updateBullets(deltaTime);
        updateCoins(deltaTime);
        if (!doctor.isHit()) {
            checkCollisions(doctor);
        }
        updateStatus();
        // generate objects
        generateObjects();
        stateTime += deltaTime;
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
            ms.update(deltaTime);
            if (ms.isDied() || ms.getTop()< getCurrentHeight()) {
                monsters.remove(ms);
                len--;
            }
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
            if (it.isUntouched() && it.getY() < getCurrentHeight() || it.isPowerOff()) {
                // the item should be removed.
                items.remove(it);
                len--;
            }
        }
    }

    /**
     * Update Doctor's position and velocity
     */
    public void updateDoctor(float deltaTime) {
        int moveDirection = 0;
        if (!doctor.isDied()) {
            moveDirection = mediator.getxDirection();
            if (mediator.isActivateItem())
                doctor.useItem();
        }
        doctor.update(deltaTime, moveDirection);
    }

    /**
     * Update Bullets' position
     */
    public void updateBullets(float deltaTime) {
        int len = bullets.size();
        for (int i = 0; i < len; ++i) {
            Bullet bullet = bullets.get(i);
            bullet.update(deltaTime);
            if (!bullet.isNormal()) {
                bullets.remove(bullet);
                len--;
            }
        }
        genBullet();
    }

    /**
     * Update coins' position
     */
    public void updateCoins(float deltaTime) {
        int len = coins.size();
        for (int i = 0; i < len; ++i) {
            Coin cn = coins.get(i);
            cn.update(deltaTime);
            if (cn.isHit()) {
                coins.remove(cn);
                len--;
            }
        }
    }


    /**Calls when height changed*/
    protected void updateHeights(float deltaTime){
        if ( camera.position.y < doctor.getY()+2){
            updateCamera();
            updateLevelAndLabel();
        }
    }

    /**
     * Update level and score, and update the score display
     */
    public void updateLevelAndLabel() {
        if (getCurrentHeight() > next_level_height) {
            level++;
            next_level_height *= 2;
        }
        score = (int) (getCurrentHeight()*SCORE_SCALE);
    }


    /**
     * Update Camera's position
     */
    public void updateCamera() {
        camera.position.y = doctor.getY()+2;
        doctor.currentHeight =  camera.position.y - WORLD_HEIGHT/2;
    }

    /**
     * Initialize floors
     */
    protected void initializeFloor() {
        Floor fl = new Floor(getWidth()/2,0.5f);
        floors.add(fl);
        floorHeight = fl.getY();
        genFloor();
    }


    /**Calls when objects should be generate*/
    protected void generateObjects(){
        while (floorHeight < getCurrentHeight() + WORLD_HEIGHT) {
            genFloor();
            genMonster();
            genItem();
            genCoin();
        }
    }

    /**
     * Generate Coins*/
    protected void genCoin(){
        float toll = rand.nextFloat();
        if (toll < Coin.RATE){
            float X = rand.nextFloat()*(WORLD_WIDTH-Coin.WIDTH);
            float Y = rand.nextFloat()*5 + floorHeight;
            Coin c = new Coin(X,Y);
            coins.add(c);
        }
    }

    /**
     * Generate Floors
     */
    protected void genFloor(){
        float floorX = rand.nextFloat() * (WORLD_WIDTH-Floor.FLOOR_WIDTH);
        float floorY = MAX_JUMP_HEIGHT*0.9f;
        // Randomize floor's Y coordinates
        if (level < 5){
            floorY *= HEIGHT_INTERVAL[level-1];
        }
        floorY *= (1-rand.nextFloat()*0.2f);
        floorY += floorHeight;

        // Randomize floor's type
        float toll = rand.nextFloat();
        if (toll < FloorBreakable.RATE_BASE){   // Breakable Floor
            FloorBreakable fl = new FloorBreakable(rand.nextFloat()*(WORLD_WIDTH-Floor.FLOOR_WIDTH),floorY-2*rand.nextFloat()+1);
            floors.add(fl);
        }
        toll = rand.nextFloat();
        if (toll < (level < 6?(level - 2):4) * FloorMovable.RATE_BASE){   // Movable Floor
            FloorMovable fl = new FloorMovable(floorX,floorY-rand.nextFloat()/2,rand.nextInt(level<5?level:5) - 3);
            floors.add(fl);
            floorHeight = fl.getY();
        }
        else{   // Normal Floor
            Floor fl = new Floor(floorX,floorY);
            floors.add(fl);
            floorHeight = fl.getY();
        }
    }

    /**Generate Monster, calls when the currentHeight has been updated*/
    protected void genMonster(){
        if (monsterHeight + 20 < getCurrentHeight()) {
            float toll = rand.nextFloat();
            if (toll < 0.05f){
                float X = rand.nextFloat()*(WORLD_WIDTH-Monster.MONSTER_WIDTH);
                if (toll < 0.01f) {
                    MonsterBoss mstBoss = new MonsterBoss(X, getCurrentHeight() + WORLD_HEIGHT);
                    monsters.add(mstBoss);
                }
                else if (toll < 0.02f){
                    MonsterHole mst = new MonsterHole(X, getCurrentHeight() + WORLD_HEIGHT);
                    monsters.add(mst);
                }
                else{
                    Monster mst = new Monster(X, getCurrentHeight() + WORLD_HEIGHT, rand.nextFloat()>0.5f);
                    monsters.add(mst);
                }
                monsterHeight = monsters.get(monsters.size()-1).getY();
            }
        }
    }

    /**Generate Bullet, calls when the doctor shot a bullet*/
    protected void genBullet(){
        // in case that the doctor shoot bullet too quickly
        if (mediator.isShootBullet()&&(stateTime > SHOOTING_SPEED)){
            stateTime = 0; // reset time
            Bullet blt;
            int len = monsters.size();
            for (int i = len-1; i >= 0; --i) {
                Monster ms = monsters.get(i);
                if (ms.isShootable()) {
                    blt = new Bullet(doctor, monsters.get(0));
                    bullets.add(blt);
                    return;
                }
            }
            blt = new Bullet(doctor);
            bullets.add(blt);
        }
    }

    /**Generate Item*/
    protected void genItem(){
        // generate springs
        float rateRocket = Rocket.rate;
        float rateFloater = Floater.rate+rateRocket;
        float rateJumper = Jumper.rate + rateFloater;
        float rateSpring = Spring.rate+rateJumper;
        float rateShield = Shield.rate+rateSpring;
        float rateReversor = Reversor.rate+rateShield;
        float toll = rand.nextFloat();
        if (toll < rateReversor) {
            if (toll < rateRocket){
                genRocket();
            }
            else if (toll < rateFloater){
                genFloater();
            }
            else if (toll < rateJumper){
                genJumper();
            }
            else if (toll < rateSpring){
                genSpring();
            }
            else if (toll < rateShield){
                genShield();
            }
            else{
                genReversor();
            }
        }
    }

    /**Generate spring*/
    protected void genSpring(){
        Floor fl = floors.get(floors.size()-1);
        Item spr = new Spring(fl);
        items.add(spr);
    }

    /**Generate jumper*/
    protected void genJumper(){
        Floor fl = floors.get(floors.size()-1);
        Item jp = new Jumper(fl);
        items.add(jp);
    }

    /**Generate reversor*/
    protected void genReversor(){
        Floor fl = floors.get(floors.size()-1);
        Item spr = new Reversor(fl);
        items.add(spr);
    }

    /**Generate rocket*/
    protected void genRocket(){
        Floor fl = floors.get(floors.size()-1);
        Item rkt = new Rocket(fl);
        items.add(rkt);
    }

    /**Generate sheild*/
    protected void genShield(){
        Floor fl = floors.get(floors.size()-1);
        Shield shield = new Shield(fl);
        items.add(shield);
    }

    /**Generate floater*/
    protected void genFloater(){
        Floor fl = floors.get(floors.size()-1);
        Item fltr = new Floater(fl);
        items.add(fltr);

    }

    /**
     * Check all kinds of collisions
     */
    public void checkCollisions(Doctor doc){
        checkHittingFloor(doc);
        checkHittingMonster(doc);
        checkHittingItem(doc);
        checkHittingCoin(doc);
    }

    /**
     * Check whether doctor hits a floor and update doctor and floor status
     */
    public void checkHittingFloor(Doctor doc) {
        if (!doc.isFalling() || doc.isLowerCurrentHeight())
            return;
        for (Floor fl : floors) {
            if (doc.getY() > fl.getY()) {
                if(doc.hitFloor(fl))
                    return;
            }
        }
        return;
    }

    /**
     * Check whether doctor hits a monster and update doctor status
     */
    public boolean checkHittingMonster(Doctor doc) {
        if (doc.isShielded())
            // the doctor is shielded
            return false;
        for (Monster mst : monsters) {
            if(mst.checkHitDoctor(doc))
                return true;
        }
        return false;
    }

    /**
     * Check whether doctor hits a item
     */
    public void checkHittingItem(Doctor doc){
        for (Item it: items){
            if (it.checkHitDoctor(doc)) {
                it.hitDoctor(doc);

            }
        }
    }

    /**
     * Check whether doctor hits a coin
     */
    public void checkHittingCoin(Doctor doc){
        for (Coin cn: coins){
           cn.checkhitDoctor(doc);
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
        if (doctor.getTop() < getCurrentHeight() || doctor.isDied()) {
            Assets.playSound(FALLSOUND);
            status = STATUS_GAME_OVER;
        }
    }

    @Override
    public void draw(){
        camera.update();

        Batch batch = getBatch();
        if (batch != null) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for (Floor fl:floors){
                fl.draw(batch,1);
            }
            for(Monster ms:monsters){
                ms.draw(batch,1);
            }
            for (Coin cn:coins){
                cn.draw(batch,1);
            }
            for (Bullet blt:bullets){
                blt.draw(batch,1);
            }
            doctor.draw(batch,1);
            for(Item it:items){
                it.draw(batch,1);
            }
            batch.end();
        }
        super.draw();
    }

    @Override
    public void dispose(){
        super.dispose();
    }


}
