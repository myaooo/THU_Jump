package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.utils.Align;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/7.
 */
public class Shield extends Item {
    static public float rate = 0.1f;
    static public float SHIELD_TIME = 5f;
    static public float SHIELD_WIDTH = 2* Doctor.WIDTH;
    static public float SHIELD_HEIGHT = SHIELD_WIDTH;

    public Shield(Floor floor){
        super(floor);
        usable = true;
        keyFrame = Assets.getShield();
    }

    @Override
    public void activate(){
        super.activate();
        keyFrame = Assets.getShieldAct();
        doctor.changeShield();
        this.setWidth(SHIELD_WIDTH);
        this.setHeight(SHIELD_HEIGHT);
        this.setPosition(doctor.getX(Align.center),doctor.getY(Align.center), Align.center);
    }

    @Override
    public void updateActive(float delta){
        float mark = SHIELD_TIME-stateTime;
        if (mark > 0) {
            this.setColor(1,1,1,2 < mark ? 1 : (mark)/2);
            this.setPosition(doctor.getX(Align.center), doctor.getY(Align.center), Align.center);
            stateTime += delta;
        }
        else this.powerOff();
    }

    @Override
    public void powerOff(){
        super.powerOff();
        doctor.item = null;
        doctor.changeShield();
    }


}
