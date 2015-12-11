package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jump.GameScreen.GameObject;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/9.
 */
public class Bullet extends GameObject {
    // static fields
    // the width and height of a bullet
    public static final float WIDTH = 0.2f;
    public static final float HEIGHT = 0.2f;
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_HIT_MONSTER = 1;
    public static final float BULLET_VELOCITY = 15;

    //private fields
    private TextureRegion keyFrame;

    // methods
    /**Constructor, a doctor's bullet shooting to an objectr*/
    public Bullet(Doctor doctor, GameObject object){
        super(doctor.getX(Align.center),doctor.getY(Align.center),WIDTH,HEIGHT);
        createBullet();
        // set bullet velocity
        float vx = this.getX(Align.center)-object.getX(Align.center);
        float vy = this.getY(Align.center) - object.getY(Align.center);
        velocity.set(vx,vy);
        velocity.nor();
        velocity.scl(BULLET_VELOCITY);
    }

    /**Constructor a doctor's bullet shooting to the sky*/
    public Bullet(Doctor doctor){
        super(doctor.getX(Align.center),doctor.getY(Align.center),WIDTH,HEIGHT);
        createBullet();
        // set bullet velocity
        velocity.set(0,BULLET_VELOCITY);
    }

    protected void createBullet(){
        stateTime = 0;
        status = STATUS_NORMAL;
        keyFrame = Assets.getBullet();
    }


    public void hitMonster(){
        status = STATUS_HIT_MONSTER;
    }

    /**
     * Update Function, calls before draw
     */
    public void update(float deltaTime){
        // update position
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);
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
}
