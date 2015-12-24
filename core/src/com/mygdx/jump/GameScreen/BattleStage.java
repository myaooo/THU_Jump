package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.GameScreen.GameItem.Reversor;
import com.mygdx.jump.GameScreen.Monster.Monster;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;

import javax.print.Doc;

/**
 * Created by Yao on 15/12/24.
 */
public class BattleStage extends GameStage {

    private static float FRUSTUM_WIDHT = Settings.SCREEN_WIDTH;
    private static float FRUSTUM_HEIGHT = Settings.SCREEN_HEIGHT;

    public static float MAX_HEIGHT_DIFF = 500;

    //public static final int STATUS_GAME_OVER2 = 2;
    //public static final int

    protected OrthographicCamera camera2;
    public Doctor doctor2;
    protected int score2;
    protected Mediator mediator2;

    public BattleStage(BattleScreen screen, Mediator media, Mediator media2){
        super(screen, media);
        mediator2 = media2;
        camera2 = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        camera2.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
    }

    @Override
    protected void initialize(){
        super.initialize();
        doctor2 = new Doctor(this);

    }

    @Override
    public int getScore2(){
        return score2;
    }

    @Override
    public float getCurrentHeight2(){
        return doctor2.currentHeight;
    }

    @Override
    public int getCoins2(){
        return doctor2.coins;
    }

    @Override
    public void draw() {

        Gdx.gl.glEnable(GL20.GL_SCISSOR_BOX);
        Gdx.gl.glScissor(0, 0, (int)FRUSTUM_WIDHT, (int)FRUSTUM_HEIGHT);
        Gdx.gl.glViewport(0, 0, (int)FRUSTUM_WIDHT, (int)FRUSTUM_HEIGHT);
        gameScreen.backStage.draw();
        draw(camera,doctor);
        gameScreen.coverStage.draw();

        Gdx.gl.glEnable(GL20.GL_SCISSOR_BOX);
        Gdx.gl.glScissor((int) FRUSTUM_WIDHT, 0, (int)FRUSTUM_WIDHT, (int)FRUSTUM_HEIGHT);
        Gdx.gl.glViewport((int) FRUSTUM_WIDHT, 0, (int)FRUSTUM_WIDHT, (int)FRUSTUM_HEIGHT);
        gameScreen.backStage.draw();
        draw(camera2, doctor2);
        gameScreen.coverStage2.draw();
    }

    public void draw(Camera cm, Doctor doc){
        cm.update();

        Batch batch = getBatch();
        if (batch != null) {
            batch.setProjectionMatrix(cm.combined);
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
            doc.draw(batch, 1);
            if (doc == doctor)
                doctor2.draw(batch,1);
            else
                doctor.draw(batch,1);
            for(Item it:items){
                if (it.doctor != null && it.doctor != doc)
                    continue;
                it.draw(batch, 1);
            }
            batch.end();
        }
    }

    protected void updateDoctor2(float deltaTime){
        int moveDirection = 0;
        if (!doctor2.isDied()) {
            moveDirection = mediator2.getxDirection();
            if (mediator2.isActivateItem())
                doctor2.useItem();
        }
        doctor2.update(deltaTime, moveDirection);
    }

    @Override
    public void update(float deltaTime) {
        updateHeights(deltaTime);
        updateDoctor(deltaTime);
        updateDoctor2(deltaTime);
        updateFloors(deltaTime);
        updateMonsters(deltaTime);
        updateItems(deltaTime);
        updateBullets(deltaTime);
        updateCoins(deltaTime);
        if (!doctor.isHit()) {
            checkCollisions(doctor);
        }
        if (!doctor2.isHit()) {
            checkCollisions(doctor2);
        }
        updateStatus();
        // generate objects
        generateObjects();
        stateTime += deltaTime;
    }

    /**
     * Update Camera2's position
     */
    public void updateCamera2() {
        camera2.position.y = doctor2.getY()+2;
        doctor2.currentHeight =  camera2.position.y - WORLD_HEIGHT/2;

    }

    /**
     * Update level and score, and update the score display
     */
    @Override
    public void updateLevelAndLabel() {
        super.updateLevelAndLabel();
        score2 = (int) (getCurrentHeight2()*SCORE_SCALE);
    }

    /**Calls when height changed*/
    @Override
    protected void updateHeights(float deltaTime){
        if ( camera.position.y < doctor.getY()+2){
            updateCamera();
            updateLevelAndLabel();
        }
        if ( camera2.position.y < doctor2.getY()+2){
            updateCamera2();
            updateLevelAndLabel();
        }
    }

    /**Calls when objects should be generate*/
    @Override
    protected void generateObjects(){
        float maxHeight = getCurrentHeight();
        maxHeight = maxHeight < getCurrentHeight2() ? getCurrentHeight2() : maxHeight;
        while (floorHeight < maxHeight + WORLD_HEIGHT) {
            genFloor();
            genMonster();
            genItem();
            genCoin();
        }
    }

    protected void genReversor(){
        Floor fl = floors.get(floors.size()-1);
        Item spr = new Reversor(this,fl);
        items.add(spr);
    }

    /**
     * Check whether the game is over (the doctor falls under current height) and update the status
     */
    @Override
    public void updateStatus(){
        if (status == STATUS_RUNNING && (doctor.getTop() < getCurrentHeight() || doctor.isDied())) {
            Assets.playSound(FALLSOUND);
            status = STATUS_GAME_OVER;
            //System.out.print(status);
        }
        if (this.status2 == STATUS_RUNNING && (doctor2.getTop() < getCurrentHeight2() || doctor2.isDied())) {
            Assets.playSound(FALLSOUND);
            this.status2 = STATUS_GAME_OVER;
            System.out.print(status2);
        }
        if (Math.abs(getCurrentHeight2()-getCurrentHeight()) > MAX_HEIGHT_DIFF){
            status = STATUS_GAME_OVER;
            status2 = STATUS_GAME_OVER;
        }
    }

    /**
     * Return true if the game is over.
     */
    @Override
    public boolean isGameOver() {
        return status == STATUS_GAME_OVER && status2 == STATUS_GAME_OVER;
    }


}
