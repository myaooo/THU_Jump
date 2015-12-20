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
    public Image REVERSOR;
    public AnimatedImage REVERSOR_ACTIVE;
    public Image SPRING;
    public Image SPRING_ACTIVE;
    public Image ROCKET;
    // Rocket animation?
    public AnimatedImage ROCKET_ACTIVE;
    public Image SHIELD;
    public Image SHIELD_ACTIVE;
    public Image FLOATER;
    public AnimatedImage FLOATER_ACTIVE;
    public Image JUMPER;
    public Image JUMPER_ACTIVE;

    // coin
    public AnimatedImage COIN;

    public Skin(){
        this("data/doctor/");
    }

    public Skin(String folderPath){
        BACKGROUND = new Image(folderPath+"background.png");
        DOCTOR_JUMP = new AnimatedImage(folderPath+"doctor_jump2.png", 2);
        DOCTOR_FALL = new AnimatedImage(folderPath+"doctor_fall2.png", 2);
        DOCTOR_HIT = new AnimatedImage(folderPath+"doctor_hit2.png", 2);
        FLOOR_NORMAL = new Image(folderPath+"floor_n.png");
        FLOOR_MOVABLE = new Image(folderPath+"floor_m.png");
        FLOOR_BREAKABLE = new Image(folderPath+"floor_bkb.png");
        FLOOR_BREAKING = new AnimatedImage(folderPath+"floor_bkg3.png",3);
        MONSTER_NORMAL = new AnimatedImage(folderPath+"monster_n2.png",2);
        MONSTER_HOLE = new AnimatedImage(folderPath+"monster_h2.png",2);
        //FLOOR_WITH_SPRING = new AnimatedImage(folderPath+"floor_s2.png",2);
        COIN = new AnimatedImage(folderPath+"coin4.png",4);
        REVERSOR = new Image(folderPath+"reversor.png");
        REVERSOR_ACTIVE = new AnimatedImage(folderPath+"reversor_active2.png",2);
        SPRING = new Image(folderPath+"spring.png");
        SPRING_ACTIVE = new Image(folderPath+"spring_active.png");
        ROCKET = new Image(folderPath+"rocket.png");
        ROCKET_ACTIVE = new AnimatedImage(folderPath+"rocket_active4.png",4);
        SHIELD = new Image(folderPath+"shield.png");
        SHIELD_ACTIVE = new Image(folderPath+"shield_active.png");
        FLOATER = new Image(folderPath+"floater.png");
        FLOATER_ACTIVE = new AnimatedImage(folderPath+"floater_active2.png",2);
        JUMPER = new Image(folderPath+"jumper.png");
        JUMPER_ACTIVE = new Image(folderPath+"jumper_active.png");

    }
}
