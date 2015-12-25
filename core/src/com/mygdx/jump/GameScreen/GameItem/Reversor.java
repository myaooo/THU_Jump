package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jump.GameScreen.BattleStage;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/20.
 */
public class Reversor extends Item {

    static public final float REVERSE_TIME = 5f;
    static public final float REVERSOR_WIDTH = 1.5f;
    static public final float REVERSOR_HEIGHT = 0.8f;
    static public final float rate = 0.01f; //Generating rate

    private float originVelocity;
    private Animation animation;
    private BattleStage stage = null;

    /**Constructor*/
    public Reversor(Floor floor){
        super(floor);
        keyFrame = Assets.getReversor();
    }

    /**Constructor with a stage as param*/
    public Reversor(BattleStage stg, Floor floor){
        super(floor);
        keyFrame = Assets.getReversor();
        stage = stg;

    }

    /**A reversor reverses a doctor's moving velocity, if this is in a battle stage, set the target different to its holder*/
    @Override
    public void activate(){
        if (stage != null){
            if (doctor == stage.doctor)
                doctor = stage.doctor2;
            else    doctor = stage.doctor;
        }
        super.activate();
        originVelocity = doctor.moveVelocity;
        doctor.moveVelocity = -originVelocity;
        animation = Assets.getReversorAct();
        keyFrame = animation.getKeyFrame(0,true);
        this.setWidth(REVERSOR_WIDTH);
        this.setHeight(REVERSOR_HEIGHT);
        this.setPosition(doctor.getX(),doctor.getTop());
    }

    /**Override powerOff function, set the doctor's velocity to its original one*/
    @Override
    public void powerOff(){
        doctor.moveVelocity = originVelocity;
        super.powerOff();
    }

    /**Override updateActive function, set the item's position to attach doctor*/
    @Override
    public void updateActive(float delta){
        float mark = REVERSE_TIME-stateTime;
        if (mark > 0) {
            this.setColor(1,1,1,2 < mark ? 1 : (mark)/2);
            this.setPosition(doctor.getX(),doctor.getTop());
            keyFrame = animation.getKeyFrame(stateTime,true);
            stateTime += delta;
        }
        else this.powerOff();
    }
}
