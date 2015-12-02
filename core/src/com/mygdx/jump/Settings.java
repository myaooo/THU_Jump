package com.mygdx.jump;

/**
 * Created by Yao on 15/12/2.
 */

// A settings class that contains many parameters
public class Settings {

    // Modifiable fields
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    // Constant fields


    // Methods
    public static void setScreen(int width, int height){
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }
}
