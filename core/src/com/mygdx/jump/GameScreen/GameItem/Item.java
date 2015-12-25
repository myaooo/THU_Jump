package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor.Floor;
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
    public Doctor targetDoctor = null;
    protected Floor attachedFloor;
    protected TextureRegion keyFrame;
    protected boolean usable = true;

    /**Default Constructor*/
    public Item(){
        super();
    }

    /**Constructor with a floor, set the item's position the the up-center to the floor*/
    public Item(Floor floor){
        this(floor, STATIC_WIDTH, STATIC_HEIGHT);
    }

    /**Constructor with a floor and a specific dimension*/
    public Item(Floor floor, float width, float height){
        super(floor.getX(Align.center)-width/2, floor.getTop(),width, height);
        attachedFloor = floor;
        status = STATUS_UNTOUCHED;
    }

    /**Test whether the item is untouched by a doctor or not*/
    public boolean isUntouched(){
        return status == STATUS_UNTOUCHED;
    }

    /**Test whether the item is hold by a doctor or not*/
    public boolean isTouched(){
        return status == STATUS_TOUCHED;
    }

    /**Test whether the item is active or not*/
    public boolean isActive(){
        return status == STATUS_ACTIVE;
    }

    /**Test whether the item has powered off or not*/
    public boolean isPowerOff(){
        return status == STATUS_POWER_OFF;
    }

    /**Test whether the item is usable*/
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

    /**default update function, the item is still untouched*/
    public void updateUntouched(float delta){
        this.setPosition(attachedFloor.getX(Align.center)-getWidth()/2, attachedFloor.getTop());
    }

    /**Active update function, the item is active now*/
    public void updateActive(float delta){

    }

    /**Hold update function, the item has been touched by a doctor and is now holding by it */
    public void updateHold(float delta){
        setPosition(0.3f+0.25f,doctor.currentHeight+0.3f+0.25f);
    }

    /**Override draw function*/
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

    /**Check whether the item is touched by a doctor*/
    public boolean checkHitDoctor(Doctor doc){
        if (isUntouched() && !doc.hasItem())
            return doc.overlaps(this);
        return false;
    }

}
