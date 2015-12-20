package com.mygdx.jump.GameScreen.GameItem;

import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/20.
 */
public class Reversor extends Item {

    static final float REVERSE_TIME = 5f;

    private float originVelocity;

    public Reversor(Floor floor){
        super(floor);
        keyFrame = Assets.getReversor();
    }

    @Override
    public void activate(){
        super.activate();
        originVelocity = doctor.MOVE_VELOCITY;
        doctor.MOVE_VELOCITY = -originVelocity;
    }

    @Override
    public void powerOff(){
        doctor.MOVE_VELOCITY = originVelocity;
    }
}
