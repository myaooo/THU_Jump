package com.mygdx.jump;

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
    // the width and height in coordinates of game world in stage
    public static final float WORLD_WIDTH = 12;
    public static final float WORLD_HEIGHT = 20;


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
