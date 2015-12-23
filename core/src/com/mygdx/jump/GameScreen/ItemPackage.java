package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/23.
 */
public class ItemPackage extends Actor {

    public static float WIDTH = 2f;
    public static float HEIGHT = 2f;
    TextureRegion keyFrame;
    GameStage theGame;

    public ItemPackage(GameStage game){
        this(game,0.3f,0.3f);
    }

    public ItemPackage(GameStage game, float x, float y){
        theGame = game;
        keyFrame = Assets.getItemPackage();
        setBounds(x,y,WIDTH,HEIGHT);
    }

    public void update(){
        setY(theGame.getCurrentHeight()+0.3f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        update();
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters
    }
}
