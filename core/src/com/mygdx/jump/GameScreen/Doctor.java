package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.Resource.Assets;

import java.util.ArrayList;

/**
 * Class Doctor, which is the main character in the game, represents the doctor in Tsinghua
 *
 * @author Ming Yao
 */

public class Doctor extends GameObject {
    // Fields
    // Static fields
    /**
     * This value means that Doctor is jumping now
     */
    public static final int STATUS_JUMP = 0;
    /**
     * This value means that Doctor has jumped, and is falling now
     */
    public static final int STATUS_FALL = 1;
    /**
     * This value means that Doctor just get hit by a monster
     */
    public static final int STATUS_HIT = 2;

    /**
     * The jumping velocity of Doctor. max_v = sqrt(2*g*h)
     */
    public static final float JUMP_VELOCITY = GameStage.NORMAL_JUMP_VELOCITY;
    /**
     * The moving velocity of Doctor, when moving key was pressed
     */
    public static float MOVE_VELOCITY = 10;

    public static final float MOVE_ACCELERATION = 30;
    /**
     * The width of Doctor
     */
    public static final float WIDTH = GameStage.WORLD_WIDTH/8;
    /**
     * The height of Doctor
     */
    public static final float HEIGHT = WIDTH*13/12;

    // private fields
    private Animation animation_fall;   // the animation that the doctor falls
    private Animation animation_jump;   // the animation that the doctor jumps
    private Animation animation_hit;    // the animation that the doctor gets hit
    private Animation current_anim;     // reference to the current animation
    //private int status = STATUS_FALL; // the status of doctor inherited from object
    //private float stateTime = 0;    // a timer that stores the time
    private TextureRegion keyFrame;
    private ArrayList<Item> itemPacakage;
    private Item item = null;  // the item that the doctor get with him
    private boolean shield = false;
    private float maxjumpheight = 0;
    private float XMin;
    private float XMax;
    private Sound jumpsound = Gdx.audio.newSound(Gdx.files.internal("data/sound/jump.wav"));
    private Sound fallsound = Gdx.audio.newSound(Gdx.files.internal("data/sound/fall.wav"));

    // public fields
    public float currentHeight = 0;
    public int coins = 0;
    public float updateScale = 0;
    public float updateRotate = 10;
    // methods

    /**
     * Default constructor
     */
    public Doctor() {
        // copy fields from resources
        this(Assets.getDoctorFallAnim(), Assets.getDoctorJumpAnim(), Assets.getDoctorHitAnim());
    }

    /**
     * Constructor, setting the image and animation to a loaded Image and Animation
     */
    public Doctor(Animation anim_f, Animation anim_j, Animation anim_h) {
        super(GameStage.WORLD_WIDTH/2, 5f, WIDTH,HEIGHT);
        this.setOrigin(WIDTH/2,HEIGHT/2);
        status = STATUS_FALL;
        stateTime = 0;
        this.setAnimation(anim_f, anim_j, anim_h);
        this.current_anim = animation_fall;
        this.acceleration = GameStage.GRAVITY;
        this.velocity.set(0,0);
        maxjumpheight = -JUMP_VELOCITY * JUMP_VELOCITY / (GameStage.GRAVITY.y * 2);
        XMin = -WIDTH/2;
        XMax = GameStage.WORLD_WIDTH-WIDTH/2;
    }

    /**
     * Set Animation
     */
    public void setAnimation(Animation anim_f, Animation anim_j, Animation anim_h) {
        this.animation_fall = anim_f;
        this.animation_jump = anim_j;
        this.animation_hit = anim_h;
    }

    /**
     * Update Function, calls before draw
     */
    public void update(float deltaTime, int moveDirection) {
        updateScale(deltaTime);
        // update position
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);
        // update velocity
        if (!isHit())
            setMoveDirection(moveDirection);
        this.velocity.add(acceleration.x*deltaTime,acceleration.y*deltaTime);

        // check for illegal X position
        if (getX() < XMin)  this.setX(XMax);
        if (getX() > XMax) this.setX(XMin);
        keyFrame = current_anim.getKeyFrame(stateTime, true);
        stateTime += deltaTime;
        if (isJump() && velocity.y<0) {
            fall();
        }
    }

    /**
     * Set the X direction's velocity into MOVE_VELOCITY*/
    public void setMoveDirection(int direction){
        velocity.x = direction * MOVE_VELOCITY;
        if (direction != 0) this.setScale(direction,1);
    }

    /**
     * Reset stateTime to zero, calls whenever status is changed
     */
    public void resetTime() {
        stateTime = 0;
    }

    /**
     * If the doctor is falling, return true, else return false
     */
    public boolean isFalling() {
        return status == STATUS_FALL;
    }

    /**
     * If the doctor is shielded, return true, else return false
     */
    public boolean isShielded() {
        return shield;
    }

    /**
     * If the doctor is shielded, return true, else return false
     */
    public boolean isHit() {
        return status == STATUS_HIT;
    }

    public boolean isJump(){
        return status == STATUS_JUMP;
    }

    public boolean isDied(){
        return isHit() && stateTime > 1f;
    }

    /**
     * Calls when the doctor hits a floor
     */
    public boolean hitFloor(Floor fl) {
        if (this.overlaps(fl)) {
            // change status, current_animation, and y velocity
            fl.hitDoctor();
            jumpsound.play(1.0f);
            if (fl.isBreakable())
                return false;
            jump(JUMP_VELOCITY);
            return true;
        }
        return false;
    }

    /**Calls when doctor jumps*/
    public void jump(float jumpVelocity){
        status = STATUS_JUMP;
        current_anim = animation_jump;
        velocity.y = jumpVelocity;
        resetTime();
    }

    /**
     * Calls when the doctor is falling (vy < 0)*/
    public void fall(){
        status = STATUS_FALL;
        current_anim = animation_fall;
        //fallsound.play(1.0f);
        resetTime();
    }

    /**
     * Calls when the doctor hits a monster
     */
    public void hitMonster() {
        // change status, current_animation, and y velocity
        status = STATUS_HIT;
        current_anim = animation_hit;
        velocity.set(0, 0);
        // resetTime;
        resetTime();
    }

    /**
     * Change the current status to newStatus and reset the stateTime
     */
    public void changeStatus(int newStatus) {
        status = newStatus;
        // check status
        switch (status) {
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
            default:
                break;
        }
        resetTime();
    }

    /**
     * Calls when the doctor hits an item and get the item*/
    public void getItem(Item it){
        itemPacakage.add(it);
    }

    /**
     * Calls when the doctor uses this item*/
    public void useItem(){
        item.activate();
    }

    /**Calls when the item is power off*/
    public void itemPowerOff(){
        item.powerOff();
    }

    public void changeShield(){
        shield = shield ? false : true;
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
        if (item != null)
            item.draw(batch, parentAlpha);

    }

    public boolean isLowerCurrentHeight(){
        return this.getY() < currentHeight;
    }

    private void updateScale(float deltaTime){
        if (isHit()) {
            this.scaleBy(updateScale*deltaTime);
            this.rotateBy(updateRotate);
        }
    }

}
