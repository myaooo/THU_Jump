package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor.Floor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/7.
 */
public class Rocket extends Item {

    static public final float ROCKET_TIME = 5f;
    static public final float ROCKET_WIDTH = 1.5f;
    static public final float ROCKET_HEIGHT = 1.5f;
    static public final float rate = 0.01f;
    static public final float ROCKET_VELOCITY = 60;
    static public final float ROCKET_ACCELERATION = 10;

    private float originAcceleration;
    //private float originVelocity;
    private Animation animation;

    public Rocket(Floor floor){
        super(floor);
        keyFrame = Assets.getRocket();
    }

    @Override
    public void activate(){
        super.activate();
        originAcceleration = doctor.getAcceleration().y;
        //originVelocity = doctor.getVelocity().y;
        doctor.setAcceleration(0,ROCKET_ACCELERATION);
        //doctor.setVelocity(0,100);
        doctor.changeShield();
        doctor.changeStatus(Doctor.STATUS_JUMP);
        animation = Assets.getRocketAct();
        keyFrame = animation.getKeyFrame(0,true);
        this.setWidth(ROCKET_WIDTH);
        this.setHeight(ROCKET_HEIGHT);
        this.setPosition(doctor.getX(),doctor.getY());
    }

    @Override
    public void powerOff(){
        doctor.setAcceleration(0,originAcceleration);
        doctor.changeShield();
        super.powerOff();
    }

    @Override
    public void updateActive(float delta){
        float mark = ROCKET_TIME-stateTime;
        if (mark > 0) {
            if (ROCKET_VELOCITY < doctor.getVelocity().y) {
                doctor.setVelocity(0, ROCKET_VELOCITY);
                doctor.setAcceleration(0,0);
            }
            this.setColor(1,1,1,1 < mark ? 1 : (mark));
            this.setPosition(doctor.getX(),doctor.getY());
            this.setScale(doctor.getScaleX(), 1);
            stateTime += delta;
        }
        else this.powerOff();
    }
}
