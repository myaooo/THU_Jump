package com.mygdx.jump.Resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * This is a font class, which can load font from .ttf file
 * Created by Yao on 15/12/3.
 */

public class Font {

    // fields
    private final BitmapFont font;  // BitmapFont that contains the font
    private final int fontSize; //size of the font
    private FreeTypeFontGenerator fontGenerator;  // a font generator

    /**
     * Load a font to memory
     *
     * @param internalPath
     *            path to the file
     * @param size
     *            size in pixels
     * @param color
     *            color
     */
    public Font(String internalPath, int size, Color color) {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(internalPath));
        font = fontGenerator.generateFont(size);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(color);
        fontSize = size;
    }

    /**
     * Load a font to memory
     *
     * @param internalPath
     *            path to the file
     * @param size
     *            size in pixels
     * @param hexColor
     *            color in hexadecimal format, without #. example: "f8f8f8"
     */
    public Font(String internalPath, int size, String hexColor) {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(internalPath));
        font = fontGenerator.generateFont(size);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.valueOf(hexColor));
        fontSize = size;
    }

    /** get the font size*/
    public int getSize(){
        return fontSize;
    }

    /** get the BitmapFont contained in the Font class*/
    public BitmapFont getFont() {
        return font;
    }

    /** simple font draw function
     * @see #draw(SpriteBatch, String, float, float, int, int, float, int, boolean)
     * */
    public void draw(SpriteBatch spriteBatch, String str, float x, float y) {
        font.draw(spriteBatch, str, x, y);// + font.getBounds(str).height);
    }

    /** a font draw function with alignment
     * @see #draw(SpriteBatch, String, float, float, int, int, float, int, boolean)
     * */
    public void draw(SpriteBatch spriteBatch, String str, float x, float y, int hAlign) {
        font.draw(spriteBatch, str, x, y, 0, hAlign, false);
    }

    /** a full font draw function with many parameters
     * @param spriteBatch the SpriteBatch that draw the font
     * @param str the clang string that you want to draw
     * @param x The x position for the left most character.
     * @param y The y position for the top of most capital letters in the font
     * @param start The first character of the string to draw.
     * @param end The last character of the string to draw (exclusive).
     * @param targetWidth The width of the area the text will be drawn, for wrapping or truncation.
     * @param hAlign Horizontal alignment of the text. Possible values are: bottom, bottomLeft, bottomRight, center, left, right, top, topRight, topLeft
     * @param wrap If true, the text will be wrapped within targetWidth.
     * */
    public void draw(SpriteBatch spriteBatch,
                     String str,
                     float x,
                     float y,
                     int start,
                     int end,
                     float targetWidth,
                     int hAlign,
                     boolean wrap) {
        font.draw(spriteBatch, str, x, y, start, end, targetWidth, hAlign, wrap);
    }

}
