package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/20.
 */
public class Jumper extends Item {
    static public final float JUMP_TIME = 5f;
    static public final float JUMPER_WIDTH = 1.5f;
    static public final float JUMPER_HEIGHT = 0.5f;
    static public final float rate = 0.1f;

    private float originVelocity;

    public Jumper(Floor floor){
        super(floor);
        keyFrame = Assets.getJumper();
    }

    @Override
    public void activate(){
        super.activate();
        originVelocity = doctor.JUMP_VELOCITY;
        doctor.JUMP_VELOCITY = 1.5f*originVelocity;
        keyFrame = Assets.getJumperAct();
        this.setWidth(JUMPER_WIDTH);
        this.setHeight(JUMPER_HEIGHT);
        this.setPosition(doctor.getX(),doctor.getY());
    }

    @Override
    public void powerOff(){
        doctor.JUMP_VELOCITY = originVelocity;
        super.powerOff();
    }

    @Override
    public void updateActive(float delta){
        float mark = JUMP_TIME-stateTime;
        if (mark > 0) {
            this.setColor(1,1,1,1 < mark ? 1 : (mark));
            this.setPosition(doctor.getX(),doctor.getY());
            this.setScale(doctor.getScaleY(),1);
            stateTime += delta;
        }
        else this.powerOff();
    }
}
