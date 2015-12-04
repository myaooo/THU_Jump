package com.mygdx.jump.Resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This is a image class with contains the image data and the sprite.
 * @author Ming Yao
 */

public class Image {

    private TextureRegion textureRegion;
    private Texture texture;
    private Sprite sprite;

    /**Constructor with a internal path, which should be in the asset folder*/
    public Image(String internalPath) {
        ImageConstructorHelper(internalPath, false);
    }

    /**Constructor with a path and a boolean repeat*/
    public Image(String internalPath, boolean repeat) {
        ImageConstructorHelper(internalPath, repeat);
    }

    /**Private function that load the image*/
    private void ImageConstructorHelper(String internalPath, boolean repeat){
        texture = new Texture(Gdx.files.internal(internalPath));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        if (repeat)
            texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        textureRegion = new TextureRegion(texture);
        sprite = new Sprite(textureRegion);
    }
    /**Set Image texture region*/
    public void setTextureRegion(int x, int y, int width, int height){
        textureRegion.setRegion(x,y,width,height);
    }

    /**get the image data*/
    public Sprite getImage() {
        return sprite;
    }

    /**get the texture data*/
    public Texture getTexture(){
        return texture;
    }

    /**get TextureRegion data*/
    public TextureRegion getTextureRegion(){return textureRegion;}

    /**get image width*/
    public float getWidth() {
        return sprite.getWidth();
    }

    /**get image height*/
    public float getHeight() {
        return sprite.getHeight();
    }

}