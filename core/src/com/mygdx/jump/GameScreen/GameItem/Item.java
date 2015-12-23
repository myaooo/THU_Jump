package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.GameScreen.GameObject;

/**
 * Created by Yao on 15/12/2.
 */

// A basic class of game item
public abstract class Item extends GameObject {
    // static fields
    static public final int STATUS_UNTOUCHED = 0;
    static public final int STATUS_TOUCHED = 1;
    static public final int STATUS_ACTIVE = 2;
    static public final int STATUS_POWER_OFF = -1;

    /**The size of the item when it is on a floor*/
    static public final float STATIC_WIDTH = 1f;
    static public final float STATIC_HEIGHT = 1f;

    static public final float HOLD_WIDTH = 1.5f;
    static public final float HOLD_HEIGHT =1.5f;
    // class fields
    public Doctor doctor = null;
    protected Floor attachedFloor;
    protected TextureRegion keyFrame;
    protected boolean usable = true;

    public Item(){
        super();
    }

    public Item(Floor floor){
        this(floor, STATIC_WIDTH, STATIC_HEIGHT);
    }

    public Item(Floor floor, float width, float height){
        super(floor.getX(Align.center)-width/2, floor.getTop(),width, height);
        attachedFloor = floor;
        status = STATUS_UNTOUCHED;
    }

    public boolean isUntouched(){
        return status == STATUS_UNTOUCHED;
    }

    public boolean isTouched(){
        return status == STATUS_TOUCHED;
    }

    public boolean isActive(){
        return status == STATUS_ACTIVE;
    }

    public boolean isPowerOff(){
        return status == STATUS_POWER_OFF;
    }

    public boolean isUsable(){
        return usable;
    }

    /**The item is hit by the doctor*/
    public void hitDoctor(Doctor doc){
        status = STATUS_TOUCHED;
        this.doctor = doc;
        if (this.isUsable()){
            doctor.getItem(this);
            setWidth(HOLD_WIDTH);
            setHeight(HOLD_HEIGHT);
        }
    }

    /**The item is activated by the doctor*/
    public void activate(){
        if (doctor == null)
            return;
        status = STATUS_ACTIVE;
        stateTime = 0;
    }

    /**The item is powered off*/
    public void powerOff(){
        status = STATUS_POWER_OFF;
        if (doctor != null)
            doctor.itemPowerOff();
    }

    /**Update function*/
    @Override
    public void update(float delta){
        switch(status){
            case STATUS_UNTOUCHED:
                updateUntouched(delta);
                break;
            case STATUS_TOUCHED:
                updateHold(delta);
                break;
            case STATUS_ACTIVE:
                updateActive(delta);
                break;
            default:
                break;
        }
    }

    public void updateUntouched(float delta){
        this.setPosition(attachedFloor.getX(Align.center)-getWidth()/2, attachedFloor.getTop());
    }

    public void updateActive(float delta){

    }

    public void updateHold(float delta){
        setPosition(0.3f+0.25f,doctor.currentHeight+0.3f+0.25f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters
    }

    public boolean checkHitDoctor(Doctor doc){
        if (isUntouched() && !doc.hasItem())
            return doc.overlaps(this);
        return false;
    }

}
