package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
    public float stateTime = 0;

    public GameOverActor(){
        this(40,350,400,300);
    }

    public GameOverActor(float x, float y, float width, float height){
        anim = Assets.getGameOverAnim();
        setBounds(x,y,width,height);
    }

    public void update(float delta){
        if (stateTime <= 1.5) {
            keyFrame = anim.getKeyFrame(stateTime, true);
            setColor(1,1,1,stateTime);
        }
        else{
            keyFrame = anim.getKeyFrame(stateTime, false);
            setColor(1,1,1,1);
        }
        stateTime +=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        update(Gdx.graphics.getDeltaTime());
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters
    }
}
