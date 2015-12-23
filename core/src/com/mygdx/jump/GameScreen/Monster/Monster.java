package com.mygdx.jump.GameScreen.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.GameScreen.Bullet;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.GameObject;
import com.mygdx.jump.GameScreen.GameStage;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/2.
 */
public class Monster extends GameObject {
    // Fields
    // Static fields
    public static final float MONSTER_WIDTH = 2;
    public static final float MONSTER_HEIGHT = 2;
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_DIED = 1;
    public static final float MOVE_SPEED = 5;

    // Class fields
    protected Animation animation;
    protected TextureRegion keyFrame;
    protected float health;
    protected float moveLb = 0;
    protected float moveUb = GameStage.WORLD_WIDTH-MONSTER_WIDTH;
    private Sound monstersound = Gdx.audio.newSound(Gdx.files.internal("data/sound/monster.mp3"));

    protected Monster(float x, float y) {
        super(x, y, MONSTER_WIDTH, MONSTER_HEIGHT);
        status = STATUS_NORMAL;
        animation = Assets.getMonsterNorm();
        stateTime = 0;
        keyFrame = animation.getKeyFrame(stateTime);
    }

    public Monster(float x, float y, boolean movable){
        this(x,y);
        health = 1;
        if (movable){
            velocity.set(MOVE_SPEED,0);
        }
    }

    /**
     * Update Function, calls before draw
     */
    @Override
    public void update(float deltaTime) {
        // update position
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);
        keyFrame = animation.getKeyFrame(stateTime, true);
        if (this.getX() < moveLb || this.getX() > moveUb)
            velocity.x = -velocity.x;
        stateTime += deltaTime;
    }

    /**
     * override draw from Actor
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters
    }

    public void hitBullet(){
        health--;
        if (health <= 0)
            status = STATUS_DIED;
    }

    public boolean checkHitDoctor(Doctor doctor){
        if (!doctor.isHit() && this.overlaps(doctor)) {
            {
                hitDoctor(doctor);
                Assets.playSound(monstersound);
                return true;
            }
        }
        return false;
    }

    public boolean overlaps(Doctor doctor){
        float x1 = getX()+getWidth()*0.15f;
        float x2 = x1 + getWidth()*0.7f;
        float y1 = getY() +getHeight()*0.15f;
        float y2 = y1 + getHeight()*0.7f;
        float _x1 = doctor.getX();
        float _x2 = _x1 + doctor.getWidth();
        float _y1 = doctor.getY();
        float _y2 = _y1 + doctor.getHeight();
        return x1 < _x2 && _x1 < x2 && y1 < _y2 && _y1 < y2;
    }
    protected void hitDoctor(Doctor doctor){
        doctor.hitMonster();
        doctor.setAcceleration(0,0);
        doctor.setVelocity(0,-10);
    }

    public boolean isShootable(){
        return true;
    }

    public boolean isDied(){
        return status == STATUS_DIED;
    }


}
