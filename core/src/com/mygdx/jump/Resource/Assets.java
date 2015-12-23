package com.mygdx.jump.Resource;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * This is a Assets class, which loaded the needed resources such as bitmaps and animation when the game is initialized.
 * The game objects and other objects load image and font data from resource when they are created.
 *
 * @author Yao
 */
public class Assets {

    // static fields
    ////////////////
    // GameScreen Skins
    ////////////////
    /**A list that contains all the skins*/
    //public static ArrayList<Skin> skinList;
    /**current Skin*/
    public static Skin curSkin;
    ////////////////
    // GameScreen Assets
    ////////////////
    public static Image BULLET;
    public static Font defaultFont;
    public static AnimatedImage GameOverAnimation;
    public static Image ITEM_PACKAGE;
    public static AnimatedImage PAUSE;
    /*
    Other Assets here
     */
    /**Load resources from data*/
    public static void load(){
        // load Skins
        curSkin= new Skin("data/doctor/");
        //skinList.add(skin);
        /*
         *  Add more Skins here
         */
        //curSkin = skinList.get(0);
        //curSkin = skin;
        // load other data
        BULLET = new Image("data/bullet.png");
        defaultFont = new Font("data/4.ttf",50, Color.BLACK);
        GameOverAnimation = new AnimatedImage("data/game_over3.png",3);
        ITEM_PACKAGE = new Image("data/item_pack.png");
        PAUSE = new AnimatedImage("data/pause2.png",2);
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

    public static TextureRegion getFloorNorm(){
        return curSkin.FLOOR_NORMAL.getTextureRegion();
    }

    public static TextureRegion getFloorMov(){
        return curSkin.FLOOR_MOVABLE.getTextureRegion();
    }

    public static TextureRegion getFloorBreakable(){
        return curSkin.FLOOR_BREAKABLE.getTextureRegion();
    }

    public static TextureRegion getBullet(){
        return BULLET.getTextureRegion();
    }

    public static TextureRegion getBackground(){
        return curSkin.BACKGROUND.getTextureRegion();
    }

    public static Animation getFloorBreaking(){
        return curSkin.FLOOR_BREAKING.getAnimation();
    }

    public static Animation getMonsterNorm(){
        return curSkin.MONSTER_NORMAL.getAnimation();
    }

    public static Animation getMonsterHole(){
        return curSkin.MONSTER_HOLE.getAnimation();
    }

    public static Animation getMonsterBoss(){
        // add code here
        return curSkin.MONSTER_NORMAL.getAnimation();
    }

    public static Animation getCoin(){
        return curSkin.COIN.getAnimation();
    }

    public static TextureRegion getReversor(){
        return curSkin.REVERSOR.getTextureRegion();
    }

    public static Animation getReversorAct(){
        return curSkin.REVERSOR_ACTIVE.getAnimation();
    }

    public static TextureRegion getSpring(){
        return curSkin.SPRING.getTextureRegion();
    }

    public static TextureRegion getSpringAct(){
        return curSkin.SPRING_ACTIVE.getTextureRegion();
    }

    public static TextureRegion getFloater(){
        return curSkin.FLOATER.getTextureRegion();
    }

    public static Animation getFloaterAct(){
        return curSkin.FLOATER_ACTIVE.getAnimation();
    }

    public static TextureRegion getRocket(){
        return curSkin.ROCKET.getTextureRegion();
    }

    public static Animation getRocketAct(){
        return curSkin.ROCKET_ACTIVE.getAnimation();
    }

    public static TextureRegion getJumper(){
        return curSkin.JUMPER.getTextureRegion();
    }

    public static TextureRegion getJumperAct(){
        return curSkin.JUMPER_ACTIVE.getTextureRegion();
    }

    public static TextureRegion getShield(){
        return curSkin.SHIELD.getTextureRegion();
    }

    public static TextureRegion getShieldAct(){
        return curSkin.SHIELD_ACTIVE.getTextureRegion();
    }

    public static BitmapFont getDefaultFont(){
        return defaultFont.getFont();
    }

    public static Animation getGameOverAnim(){return GameOverAnimation.getAnimation();}

    public static TextureRegion getItemPackage(){
        return ITEM_PACKAGE.getTextureRegion();
    }

    public static TextureRegion getPauseUp(){
        return PAUSE.getAnimation().getKeyFrame(0f);
    }
    public static TextureRegion getPauseDown(){
        return PAUSE.getAnimation().getKeyFrame(0.2f);
    }
}
