package com.mygdx.jump.Resource;

import com.mygdx.jump.Settings;

/**
 * A Skin class contains a suit of skins including Fonts, Images and AnimatedImages
 * Created by Yao on 15/12/7.
 */
public class Skin {

    public AnimatedImage DOCTOR_JUMP;
    public AnimatedImage DOCTOR_FALL;
    public AnimatedImage DOCTOR_HIT;

    public Image BACKGROUND;

    // types of floors
    public Image FLOOR_NORMAL;
    public Image FLOOR_MOVABLE;
    public Image FLOOR_BREAKABLE;
    public AnimatedImage FLOOR_BREAKING;
    //public AnimatedImage FLOOR_WITH_SPRING;

    // monsters
    public AnimatedImage MONSTER_NORMAL;
    public AnimatedImage MONSTER_HOLE;
    // types of items

    // coin
    public AnimatedImage COIN;

    public Skin(){
        this("data/doctor/");
    }

    public Skin(String folderPath){
        BACKGROUND = new Image(folderPath+"background.png");
        DOCTOR_JUMP = new AnimatedImage(folderPath+"doctor_jump2.png", 2, Settings.ANIMATION_INTERVAL);
        DOCTOR_FALL = new AnimatedImage(folderPath+"doctor_fall2.png", 2, Settings.ANIMATION_INTERVAL);
        DOCTOR_HIT = new AnimatedImage(folderPath+"doctor_hit2.png", 2, Settings.ANIMATION_INTERVAL);
        FLOOR_NORMAL = new Image(folderPath+"floor_n.png");
        FLOOR_MOVABLE = new Image(folderPath+"floor_m.png");
        FLOOR_BREAKABLE = new Image(folderPath+"floor_bkb.png");
        FLOOR_BREAKING = new AnimatedImage(folderPath+"floor_bkg3.png",3,Settings.ANIMATION_INTERVAL);
        MONSTER_NORMAL = new AnimatedImage(folderPath+"monster_n2.png",2,Settings.ANIMATION_INTERVAL);
        MONSTER_HOLE = new AnimatedImage(folderPath+"monster_h2.png",2,Settings.ANIMATION_INTERVAL);
        //FLOOR_WITH_SPRING = new AnimatedImage(folderPath+"floor_s2.png",2,Settings.ANIMATION_INTERVAL);
        COIN = new AnimatedImage(folderPath+"coin4.png",4,Settings.ANIMATION_INTERVAL);

    }

}
