package com.mygdx.jump.GameScreen.Floor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.GameScreen.GameObject;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Resource.Image;
import com.mygdx.jump.Settings;

/**
 * Floor class which is also abase class for special floors
 * @author Yao
 * Created by Yao on 15/12/2.
 */
// This is a class floor which the doodle step on
public class Floor extends GameObject {

    // static fields
    public static final float FLOOR_WIDTH = 2;
    public static final float FLOOR_HEIGHT = 0.8f;

    public static final float FLOOR_VELOCITY = 4;

    // private class fields
    protected TextureRegion keyFrame;

    /**
     * Constructor, input floor type, and its position x,y
     */
    public Floor(float x, float y) {
        super(x, y, FLOOR_WIDTH, FLOOR_HEIGHT);
        stateTime = 0;
        keyFrame = Assets.getFloorNorm();
    }

    /**
     * Update the floor, input deltaTime
     */
    @Override
    public void update(float deltaTime) {
        //stateTime += deltaTime;
    }

    /**
     * override draw from Actor
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters

    }

    /**Hit by a doctor*/
    public void hitDoctor(){}

    /**
     * return true if the floor type is breakable
     */
    public boolean isBreakable() {
        return false;
    }

    /**
     * return true if the floor is broken and needs to be deleted
     */
    public boolean isBroken() {
        return false;
    }

}
