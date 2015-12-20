package com.mygdx.jump.Resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.Settings;

/**
 * AnimatedImage class, Load png files as libGDX Animation class
 * @author Ming Yao
 */
public class AnimatedImage {
    private Texture fullTexture;
    private TextureRegion[] keyFrames;
    private Animation animation;

    public AnimatedImage(String internalPath, int num){
        this(internalPath, num, Settings.ANIMATION_INTERVAL);
    }

    public AnimatedImage(String internalPath, int num, float cycleTime) {
        if (num<1) //illegal input
            num = 1;
        fullTexture = new Texture(Gdx.files.internal(internalPath));
        int width = fullTexture.getWidth()/num;
        int height = fullTexture.getHeight();
        keyFrames = new TextureRegion[num];
        for (int i = 0; i < num; ++i){
            // partition the full texture into animation regions
            keyFrames[i] = new TextureRegion(fullTexture, width*i, 0, width, height);
        }
        animation = new Animation(cycleTime, keyFrames);
    }
    /**Get the animation objects*/
    public Animation getAnimation(){
        return animation;
    }

    /**Get the fullTexture*/
    public Texture getFullTexture(){
        return fullTexture;
    }

}
