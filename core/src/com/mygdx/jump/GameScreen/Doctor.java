package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.Resource.Resources;

/**
 * Class Doctor, which is the main character in the game, represents the doctor in Tsinghua
 * @author Ming Yao
 */

public class Doctor extends GameObject {
    // Fields
    // Static fields
    /** This value means that Doctor is jumping now*/
    public static final int STATUS_JUMP = 0;
    /** This value means that Doctor has jumped, and is falling now*/
    public static final int STATUS_FALL = 1;
    /** This value means that Doctor just get hit by a monster*/
    public static final int STATUS_HIT = 2;
    /** The jumping velocity of Doctor*/
    public static final float JUMP_VELOCITY = 11;
    /** The moving velocity of Doctor, when moving key was pressed*/
    public static final float MOVE_VELOCITY = 20;
    /** The width of Doctor*/
    public static final float WIDTH = 0.8f;
    /** The height of Doctor*/
    public static final float HEIGHT = 0.8f;

    // private fields
    private Animation animation_fall;   // the animation that the doctor falls
    private Animation animation_jump;   // the animation that the doctor jumps
    private Animation animation_hit;    // the animation that the doctor gets hit
    private Animation current_anim;     // reference to the current animation
    private int status = STATUS_FALL; // the status of doctor
    private float stateTime = 0;    // a timer that stores the time
    private Item item = null;  // the item that the doctor get with him

    // methods
    /**Default constructor*/
    public Doctor()
    {
        // copy fields from resources
        this(Resources._DOCTOR_FALL.getAnimation(),Resources._DOCTOR_JUMP.getAnimation(),Resources._DOCTOR_HIT.getAnimation());
    }
    /**Constructor, setting the image and animation to a loaded Image and Animation*/
    public Doctor(Animation anim_f, Animation anim_j, Animation anim_h)
    {
        this.acceleration = GameStage.GRAVITY;
        this.setAnimation(anim_f,anim_j,anim_h);
        this.current_anim = animation_fall;
    }

    /**Set Animation*/
    public void setAnimation(Animation anim_f, Animation anim_j, Animation anim_h){
        this.animation_fall = anim_f;
        this.animation_jump = anim_j;
        this.animation_hit = anim_h;
    }

    /**Update Function, calls before draw*/
    public void update(float deltaTime){
        // update velocity
        this.velocity.add(acceleration.scl(deltaTime));
        // update position
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);

    }

    /**Reset stateTime to zero, calls whenever status is changed*/
    public void resetTime(){
        stateTime = 0;
    }

    public int getStatus(){
        return status;
    }

    /**Change the current status to newStatus and reset the stateTime*/
    public void changeStatus(int newStatus){
        status = newStatus;
        // check status
        switch(status){
            case STATUS_FALL: current_anim = animation_fall; break;
            case STATUS_HIT: current_anim = animation_hit; break;
            case STATUS_JUMP: current_anim = animation_jump; break;
            default: break;
        }
        resetTime();
    }

    /** override draw from Actor*/
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        update(Gdx.graphics.getDeltaTime());
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion keyFrame = current_anim.getKeyFrame(stateTime,true);

        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                keyFrame.getRegionWidth() / 2, keyFrame.getRegionHeight() / 2, // rotate and scale center x,y
                keyFrame.getRegionWidth(), keyFrame.getRegionHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters

    }

}
