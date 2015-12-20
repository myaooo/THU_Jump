package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.GameScreen.GameObject;

/**
 * Created by Yao on 15/12/2.
 */

// A basic class of game item
public class Item extends GameObject {
    // static fields
    static public final int STATUS_UNTOUCHED = 0;
    static public final int STATUS_TOUCHED = 1;
    static public final int STATUS_USING = 2;
    static public final int STATUS_POWER_OFF = -1;
    // class fields
    protected Doctor doctor = null;
    protected TextureRegion keyFrame;

    public Item(Floor floor, float width, float height){
        super(floor.getX(Align.center)-width/2, floor.getTop(),width, height);
        status = STATUS_UNTOUCHED;
    }

    public boolean isUntouched(){
        return status == STATUS_UNTOUCHED;
    }

    public boolean isTouched(){
        return status == STATUS_TOUCHED;
    }

    public boolean isUsing(){
        return status == STATUS_USING;
    }

    public boolean isPowerOff(){
        return status == STATUS_POWER_OFF;
    }

    public void hitDoctor(){
        status = STATUS_TOUCHED;
    }

    public void use(){

    }

    public void poweroff(){
        status = STATUS_POWER_OFF;
    }

}
