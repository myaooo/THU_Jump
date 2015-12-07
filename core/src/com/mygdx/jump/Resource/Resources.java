package com.mygdx.jump.Resource;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;

/**
 * This is a Resources class, which loaded the needed resources such as bitmaps and animation when the game is initialized.
 * The game objects and other objects load image and font data from resource when they are created.
 *
 * @author Yao
 */
public class Resources {

    ////////////////
    // GameScreen Skins
    ////////////////
    /**A list that contains all the skins*/
    public static ArrayList<Skin> skinList;
    /**current Skin*/
    public static Skin curSkin;
    ////////////////
    // GameScreen Resources
    ////////////////
    /*
    Other Resources here
     */
    // Loading process
    static{
        skinList.add(new Skin());
        /*
         *  Add more Skins here
         */
        curSkin = skinList.get(0);
    }

    public static Animation getDoctorJumpAnim(){
        return curSkin.DOCTOR_JUMP.getAnimation();
    }

    public static Animation getDoctorFallAnim(){
        return curSkin.DOCTOR_FALL.getAnimation();
    }

    public static Animation getDoctorHitAnim(){
        return curSkin.DOCTOR_HIT.getAnimation();
    }

    public static Image getFloorNorm(){
        return curSkin.FLOOR_NORMAL;
    }

    public static Image getFloorMov(){
        return curSkin.FLOOR_MOVABLE;
    }

    public static Image getFloorBreakable(){
        return curSkin.FLOOR_BREAKABLE;
    }

    public static Animation getFloorBreaking(){
        return curSkin.FLOOR_BREAKING.getAnimation();
    }

}
