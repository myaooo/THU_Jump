package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/20.
 */
public class Floater extends Item{
    static public final float FLOAT_TIME = 5f;
    static public final float FLOATER_WIDTH = 1.5f;
    static public final float FLOATER_HEIGHT = 0.5f;
    static public final float rate = 0.02f;

    private float originAcceleration;
    private Animation animation;

    public Floater(Floor floor){
        super(floor);
        keyFrame = Assets.getFloater();
    }

    @Override
    public void activate(){
        super.activate();
        doctor.Floating = true;
        originAcceleration = doctor.getAcceleration().y;
        doctor.setAcceleration(0,0);
        doctor.changeStatus(Doctor.STATUS_JUMP);
        animation = Assets.getFloaterAct();
        keyFrame = animation.getKeyFrame(0,true);
        this.setWidth(FLOATER_WIDTH);
        this.setHeight(FLOATER_HEIGHT);
        this.setPosition(doctor.getX(),doctor.getY());
    }

    @Override
    public void powerOff(){
        doctor.Floating = false;
        doctor.setAcceleration(0,originAcceleration);
        super.powerOff();
    }

    @Override
    public void updateActive(float delta){
        float mark = FLOAT_TIME-stateTime;
        if (mark > 0) {
            this.setColor(1,1,1,1 < mark ? 1 : (mark));
            this.setPosition(doctor.getX(),doctor.getY());
            //this.setScale(doctor.getScaleX(), 1);
            stateTime += delta;
        }
        else this.powerOff();
    }
}
