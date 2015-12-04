package com.mygdx.jump.Resource;

import com.mygdx.jump.Settings;
/**
 * This is a Resources class, which loaded the needed resources such as bitmaps and animation when the game is initialized.
 * The game objects and other objects load image and font data from resource when they are created.
 *
 * @author Yao
 */
public class Resources {
    ////////////////
    // GameScreen Resources
    ////////////////
    public static Image _DOCTOR_NORMAL;
    public static AnimatedImage _DOCTOR_JUMP;
    public static AnimatedImage _DOCTOR_FALL;
    public static AnimatedImage _DOCTOR_HIT;

    // Loading process
    static{
        _DOCTOR_NORMAL = new Image("doctor/doctor_normal.png");

        _DOCTOR_JUMP = new AnimatedImage("doctor/doctor_jump.png",2,Settings.ANIMATION_INTERVAL);
        _DOCTOR_FALL = new AnimatedImage("doctor/doctor_fall.png",2,Settings.ANIMATION_INTERVAL);
        _DOCTOR_HIT = new AnimatedImage("doctor/doctor_hit.png",2,Settings.ANIMATION_INTERVAL);
    }


}
