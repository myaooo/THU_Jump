package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/18.
 */
public class Coin extends GameObject {

    public static final float WIDTH = 0.5f;
    public static final float HEIGHT = 0.5f;

    private Animation animation;
    private TextureRegion keyFrame;
    private float stateTime = 0;

    public Coin(float x, float y){
        super(x,y,WIDTH,HEIGHT);
        animation = Assets.getCoin();
        keyFrame = animation.getKeyFrame(stateTime, true);
    }

    /**
     * Update Function, calls before draw
     */
    @Override
    public void update(float deltaTime) {
        keyFrame = animation.getKeyFrame(stateTime, true);
        stateTime += deltaTime;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters

    }
}
