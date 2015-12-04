package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.jump.GameScreen.GameItem.Item;
import com.mygdx.jump.Resource.Image;
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
    private Image image;    // image class
    private Animation animation;
    private int status = STATUS_FALL; // the status of doctor
    private float stateTime = 0;    // a timer that stores the time
    private Item item = null;  // the item that the doctor get with him

    // methods
    /**Default constructor*/
    public Doctor()
    {
        // copy fields from resources
        this.image = Resources._DOCTOR_NORMAL;
        this.animation = Resources._DOCTOR_FALL.getAnimation();
    }
    /**Constructor, setting the image and animation to a loaded Image and Animation*/
    public Doctor(Image im, Animation anim)
    {
        this.image = im;
        this.animation = anim;
    }

    /**Update Function, calls before draw*/
    public void update(){

    }

    /** override draw from Actor*/
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion keyFrame = animation.getKeyFrame(stateTime,true);

        batch.draw(keyFrame, getX(), getY(),    // position
                keyFrame.getRegionWidth() / 2, keyFrame.getRegionHeight() / 2, // rotate and scale center x,y
                keyFrame.getRegionWidth(), keyFrame.getRegionHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters

    }

}
