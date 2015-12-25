package com.mygdx.jump;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.jump.Resource.Font;
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
    public static boolean SOUNDOPEN =true;

    // Constant fields
    public static final Color myGoldYellow = new Color(1, 222/255f,0,1);
    public static final Color myDarkBlue = new Color(0f,0.3f,0.5f,0.9f);

    // Keys

    public static int KEY_LEFT = Keys.LEFT;
    public static int KEY_LEFT2 = Keys.A;
    public static int KEY_RIGHT = Keys.RIGHT;
    public static int KEY_RIGHT2 = Keys.D;
    public static int KEY_SHOOT = Keys.SPACE;
    public static int KEY_SHOOT2 = Keys.F;
    public static int KRY_USE_ITEM = Keys.ENTER;
    public static int KEY_USE_ITEM2 = Keys.G;
    public static int KEY_UP = Keys.UP;
    public static int KEY_DOWN = Keys.DOWN;

    //Sound
    public static boolean soundOpen = true;

    //levels
    public static enum Levels{
        EASY,NORMAL,HARD
    }

    public static Levels level = Levels.NORMAL;

    public static Preferences preferences =  Gdx.app.getPreferences(Settings.class.getName());

    //TotalCoins
    public static int TotalCoins;

    //bought

    public static boolean[] bought = new boolean[6];


    // Methods

    /** Load settings from path*/
    public static void load(){

        TotalCoins = preferences.getInteger("TotalCoins",0);
        SCREEN_WIDTH = preferences.getInteger("SCREEN_WIDTH",480);
        SCREEN_HEIGHT = preferences.getInteger("SCREEN_HEIGHT",800);

        KEY_LEFT = preferences.getInteger("KEY_LEFT",Keys.LEFT);
        KEY_RIGHT = preferences.getInteger("KEY_RIGHT",Keys.RIGHT);
        KEY_SHOOT = preferences.getInteger("KEY_SHOOT",Keys.SPACE);

        bought[0] = preferences.getBoolean("bought0",true);
        bought[1] = preferences.getBoolean("bought1",false);
        bought[2] = preferences.getBoolean("bought2",false);
        bought[3] = preferences.getBoolean("bought3",false);
        bought[4] = preferences.getBoolean("bought4",false);
        bought[5] = preferences.getBoolean("bought5",false);


    }

    /** Save settings into path*/
    public static void save(){

        preferences.putInteger("TotalCoins",TotalCoins);
        preferences.putInteger("SCREEN_WIDTH",SCREEN_WIDTH);
        preferences.putInteger("SCREEN_HEIGHT",SCREEN_HEIGHT);

        preferences.putInteger("KEY_LEFT",KEY_LEFT);
        preferences.putInteger("KEY_RIGHT",KEY_RIGHT);
        preferences.putInteger("KEY_SHOOT",KEY_SHOOT);

        preferences.putBoolean("bought0",bought[0]);
        preferences.putBoolean("bought1",bought[1]);
        preferences.putBoolean("bought2",bought[2]);
        preferences.putBoolean("bought3",bought[3]);
        preferences.putBoolean("bought4",bought[4]);
        preferences.putBoolean("bought5",bought[5]);

        preferences.flush();
    }

    public static void setScreen(int width, int height){
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }
}
