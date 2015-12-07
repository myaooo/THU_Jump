package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Resource.Image;

/**
 * Created by Yao on 15/12/2.
 */
public class Monster extends GameObject {
    // Fields
    // Static fields
    public static final float MONSTER_WIDTH = 2;
    public static final float MONSTER_HEIGHT = 2;
    public static final int MONSTER_TYPE_NORMAL = 0;
    public static final int MONSTER_TYPE_HOLE = 1;
    // Class fields
    private Animation animation;
    private TextureRegion keyFrame;
    private int type;
    private float stateTime;

    public Monster(int itype, float x, float y) {
        super(x, y, MONSTER_WIDTH, MONSTER_HEIGHT);
        type = itype;
        switch (type) {
            case MONSTER_TYPE_NORMAL:
                animation = Assets.getMonsterNorm();
                break;
            case MONSTER_TYPE_HOLE:
                animation = Assets.getMonsterHole();
                break;
            default:
                break;
        }
        stateTime = 0;
    }

    /**
     * Update Function, calls before draw
     */
    @Override
    public void update(float deltaTime) {
        // update velocity
        this.velocity.add(acceleration.scl(deltaTime));
        // update position
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);
        keyFrame = animation.getKeyFrame(stateTime, true);
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

}
