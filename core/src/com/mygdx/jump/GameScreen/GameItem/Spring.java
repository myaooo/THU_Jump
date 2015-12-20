package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/20.
 */
public class Spring extends Item{

    static public final float GENERATE_RATE = 0.05f;
    // active
    private TextureRegion activeTexture;

    Spring(Floor floor){
        super(floor);
        keyFrame = Assets.getSpring();
        activeTexture = Assets.getSpringAct();
    }

    @Override
    public void hitDoctor(Doctor doc){
        super.hitDoctor(doc);

    }

    @Override
    public void activate(){
        keyFrame = activeTexture;
        this.doctor.setVelocity(0,2*doctor.JUMP_VELOCITY);
    }

    public static float getRate(){
        return GENERATE_RATE;
    }

}
