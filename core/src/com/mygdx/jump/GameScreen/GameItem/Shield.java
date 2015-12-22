package com.mygdx.jump.GameScreen.GameItem;

import com.mygdx.jump.GameScreen.Floor;

/**
 * Created by Yao on 15/12/7.
 */
public class Shield extends Item {
    static public float rate = 0.01f;
    static public float SHIELD_TIME = 5f;

    public Shield(Floor floor, float width, float height){
        super(floor, width, height);
    }

    @Override
    public void activate(){
        if (doctor == null)
            return;
        doctor.changeShield();
    }
}
