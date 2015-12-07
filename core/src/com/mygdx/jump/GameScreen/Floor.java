package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Resource.Image;
import com.mygdx.jump.Settings;

/**
 * Created by Yao on 15/12/2.
 */
// This is a class floor which the doodle step on
public class Floor extends GameObject {

    // static fields
    public static final float FLOOR_WIDTH = 2;
    public static final float FLOOR_HEIGHT = 0.5f;
    public static final int FLOOR_TYPE_STATIC = 0;
    public static final int FLOOR_TYPE_MOVABLE = 1;
    public static final int FLOOR_TYPE_BREAKABLE = 2;
    public static final int FLOOR_STATUS_NORMAL = 0;
    public static final int FLOOR_STATUS_BREAKING = 1;
    public static final float FLOOR_VELOCITY = 2;
    public static final float FLOOR_BREAKING_TIME = Settings.ANIMATION_INTERVAL * 3;

    // private class fields
    private int type;
    private int status;
    private float stateTime;
    private Image image;
    private TextureRegion keyFrame;
    private Animation imageBreak = null;

    /**
     * Constructor, input floor type, and its position x,y
     */
    public Floor(int itype, float x, float y) {
        super(x, y, FLOOR_WIDTH, FLOOR_HEIGHT);
        this.type = itype;
        this.status = FLOOR_STATUS_NORMAL;
        this.stateTime = 0;
        switch (type) {
            case FLOOR_TYPE_STATIC:
                image = Assets.getFloorNorm();
                velocity.set(0, 0);
                break;
            case FLOOR_TYPE_MOVABLE:
                image = Assets.getFloorMov();
                velocity.set(FLOOR_VELOCITY, 0);
                break;
            case FLOOR_TYPE_BREAKABLE:
                image = Assets.getFloorBreakable();
                imageBreak = Assets.getFloorBreaking();
                velocity.set(0, 0);
            default:
                break;
        }
        keyFrame = image.getTextureRegion();
    }

    /**
     * Update the floor, input deltaTime
     */
    @Override
    public void update(float deltaTime) {
        if (type == FLOOR_TYPE_MOVABLE) {
            this.moveBy(velocity.x * deltaTime, 0);
            // check the position of the floor
            if (this.getX() < FLOOR_WIDTH / 2) {
                velocity.x = -velocity.x;
                this.setX(FLOOR_WIDTH / 2);
            }
            if (this.getX() > Settings.SCREEN_WIDTH - FLOOR_WIDTH / 2) {
                velocity.x = -velocity.x;
                this.setX(Settings.SCREEN_WIDTH - FLOOR_WIDTH / 2);
            }
        }
        if (status == FLOOR_STATUS_BREAKING) {
            keyFrame = imageBreak.getKeyFrame(stateTime, false);
        }
        stateTime += deltaTime;
    }

    /**
     * override draw from Actor
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                keyFrame.getRegionWidth() / 2, keyFrame.getRegionHeight() / 2, // rotate and scale center x,y
                keyFrame.getRegionWidth(), keyFrame.getRegionHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters

    }

    /**
     * Calls when the floor is broken
     */
    public void floorBreak() {
        status = FLOOR_STATUS_BREAKING;
        stateTime = 0;
    }

    /**
     * return true if the floor type is breakable
     */
    public boolean isBreakable() {
        return type == FLOOR_TYPE_BREAKABLE;
    }

    /**
     * return true if the floor is broken and needs to be deleted
     */
    public boolean isBroken() {
        return status == FLOOR_STATUS_BREAKING && stateTime > FLOOR_BREAKING_TIME;
    }

}
