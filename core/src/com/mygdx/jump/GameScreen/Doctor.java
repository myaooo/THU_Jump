package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class Doctor, which is the main character in the game, represents the doctor in Tsinghua
 * @author Ming Yao
 */

public class Doctor extends GameObject {
    // Fields
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

    private Pixmap mPixmap;
    private Texture mTexture;
    private TextureRegion mTextureRegion;
    private Sprite mSprite;

    private float timer;

    public Doctor()
    {
        mPixmap = new Pixmap(Gdx.files.internal("doctor.png"));
        mTexture = new Texture(mPixmap);
        mTextureRegion = new TextureRegion(mTexture, 0, 0, 30, 30);
        mSprite = new Sprite(mTextureRegion);
        mSprite.setPosition(800 / 2, 480 / 2);
    }
}
