package com.mygdx.jump;

/**
 * A class settings that contains many modifiable settings of the game
 * @author Ming Yao
 */

// A settings class that contains many parameters
public class Settings {

    // Modifiable fields
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static final float ANIMATION_INTERVAL = 0.2f;

    // Constant fields



    // Methods
    /** Load settings from path*/
    public Settings(String path){

    }

    /** Save settings into path*/
    public void saveSettings(String path){

    }

    public static void setScreen(int width, int height){
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }
}
