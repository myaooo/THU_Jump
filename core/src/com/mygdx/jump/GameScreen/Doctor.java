package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.Resource.Assets;

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
    private TextureRegion keyFrame;
    private Item item = null;  // the item that the doctor get with him
    private boolean shield = false;
    private float maxjumpheight = 0;

    // methods
    /**Default constructor*/
    public Doctor()
    {
        // copy fields from resources
        this(Assets.getDoctorFallAnim(), Assets.getDoctorJumpAnim(), Assets.getDoctorHitAnim());
    }
    /**Constructor, setting the image and animation to a loaded Image and Animation*/
    public Doctor(Animation anim_f, Animation anim_j, Animation anim_h)
    {
        this.setAnimation(anim_f,anim_j,anim_h);
        this.current_anim = animation_fall;
        this.acceleration = GameStage.GRAVITY;
        maxjumpheight = -JUMP_VELOCITY*JUMP_VELOCITY/(GameStage.GRAVITY.y * 2);
    }

    /**Set Animation*/
    public void setAnimation(Animation anim_f, Animation anim_j, Animation anim_h){
        this.animation_fall = anim_f;
        this.animation_jump = anim_j;
        this.animation_hit = anim_h;
    }

    /**Update Function, calls before draw*/
    @Override
    public void update(float deltaTime){
        // update velocity
        this.velocity.add(acceleration.scl(deltaTime));
        // update position
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);
        keyFrame = current_anim.getKeyFrame(stateTime,true);
        stateTime += deltaTime;
    }

    /**Reset stateTime to zero, calls whenever status is changed*/
    public void resetTime(){
        stateTime = 0;
    }

    /**If the doctor is falling, return true, else return false*/
    public boolean isFalling(){return status == STATUS_FALL;}

    /**If the doctor is shielded, return true, else return false*/
    public boolean isShielded(){return shield;}

    /**If the doctor is shielded, return true, else return false*/
    public boolean isHitted(){return status == STATUS_HIT;}

    /**Calls when the doctor hits a floor*/
    public void hitFloor(){
        // change status, current_animation, and y velocity
        status = STATUS_JUMP;
        current_anim = animation_jump;
        velocity.y = JUMP_VELOCITY;
        // resetTime;
        resetTime();
    }

    /**Calls when the doctor hits a monster*/
    public void hitMonster(){
        // change status, current_animation, and y velocity
        status = STATUS_HIT;
        current_anim = animation_hit;
        velocity.set(0,0);
        // resetTime;
        resetTime();
    }

    /**Change the current status to newStatus and reset the stateTime*/
    public void changeStatus(int newStatus){
        status = newStatus;
        // check status
        switch(status){
            case STATUS_FALL:
                current_anim = animation_fall;
                break;
            case STATUS_HIT:
                current_anim = animation_hit;
                break;
            case STATUS_JUMP:
                current_anim = animation_jump;
                velocity.y = JUMP_VELOCITY;
                break;
            default: break;
        }
        resetTime();
    }

    /** override draw from Actor*/
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                keyFrame.getRegionWidth() / 2, keyFrame.getRegionHeight() / 2, // rotate and scale center x,y
                keyFrame.getRegionWidth(), keyFrame.getRegionHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters

    }

}
