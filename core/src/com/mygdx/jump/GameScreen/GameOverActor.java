package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/21.
 */
public class GameOverActor extends Actor {

    Animation anim;
    TextureRegion keyFrame;
    private float stateTime = 0;

    public GameOverActor(float height){
        anim = Assets.getGameOverAnim();
        setBounds(1,height,10,7.5f);
    }

    public void update(float delta){
        if (stateTime <= 1) {
            keyFrame = anim.getKeyFrame(stateTime, true);
            setColor(0,0,0,stateTime);
        }
        else{
            keyFrame = anim.getKeyFrame(stateTime, false);
            setColor(0,0,0,1);
        }
        stateTime +=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        update(Gdx.graphics.getDeltaTime());
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters
    }
}
