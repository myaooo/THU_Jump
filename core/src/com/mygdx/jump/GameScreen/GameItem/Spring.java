package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor.Floor;
import com.mygdx.jump.GameScreen.GameStage;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/20.
 */
public class Spring extends Item{

    static public final float rate = 0.04f;
    // active
    private TextureRegion activeTexture;

    public Spring(Floor floor){
        super(floor);
        keyFrame = Assets.getSpring();
        activeTexture = Assets.getSpringAct();
        usable = false;
    }

    @Override
    public void hitDoctor(Doctor doc){
        if (doc.getVelocity().y > GameStage.NORMAL_JUMP_VELOCITY)
            return;
        super.hitDoctor(doc);
        activate();
    }

    @Override
    public void activate(){
        super.activate();
        keyFrame = activeTexture;
        this.doctor.jump(2f*doctor.jumpVelocity);
    }

    public static float getRate(){
        return rate;
    }

    @Override
    public boolean checkHitDoctor(Doctor doctor){
        if (doctor.isFalling() && doctor.getY() > this.getY()){
            return doctor.overlaps(this);
        }
        return false;
    }

}
