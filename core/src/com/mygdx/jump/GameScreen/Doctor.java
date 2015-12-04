package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    // class private fields
    private Image image;    // image class
    private Animation animation;
    private int status; // the status of doctor
    private float stateTime;    // a timer that stores the time

    public Doctor()
    {
        // copy fields from resources
        image = Resources._DOCTOR_NORMAL;
        animation = Resources._DOCTOR_FALL.getAnimation();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion keyFrame = animation.getKeyFrame(stateTime,true);
        // 这里要注意，我们添加的action只是改变actor的属性值，绘制的时候并没有
        // 自动给我们处理这些逻辑， 我们要做的就是取得这些值，然后自己处理
        batch.draw(textureRegion, getX(), getY(),
                textureRegion.getRegionWidth() / 2,
                textureRegion.getRegionHeight() / 2,
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(), getScaleX(), getScaleY(),
                getRotation());

    }

}
