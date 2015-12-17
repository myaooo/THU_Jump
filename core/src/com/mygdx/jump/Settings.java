package com.mygdx.jump;

import com.badlogic.gdx.Input.Keys;

/**
 * A class settings that contains many modifiable settings of the game
 * @author Ming Yao
 */

// A settings class that contains many parameters
public class Settings {

    // Modifiable fields
    /**The width of the screen in screen coordinates*/
    public static int SCREEN_WIDTH = 480;
    /**The height of the screen in screen coordinates*/
    public static int SCREEN_HEIGHT = 800;
    public static final float ANIMATION_INTERVAL = 0.2f;
    public static final String settingsfile = ".settings";

    // Constant fields


    // Keys
    public static int KEY_LEFT = Keys.LEFT;
    public static int KEY_RIGHT = Keys.RIGHT;
    public static int KEY_SHOOT = Keys.SPACE;

    // Methods
    /** Load settings from path*/
    public static void load(){
        load(settingsfile);
    }

    /** Load settings from path*/
    public static void load(String path){

    }

    /** Save settings into path*/
    public static void saveSettings(String path){

    }

    public static void setScreen(int width, int height){
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }
}
