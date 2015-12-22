package com.mygdx.jump.GameScreen.GameItem;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.GameScreen.Floor;
import com.mygdx.jump.GameScreen.GameObject;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/20.
 */
public class Spring extends Item{

    static public final float GENERATE_RATE = 0.05f;
    // active
    private TextureRegion activeTexture;

    public Spring(Floor floor){
        super(floor);
        keyFrame = Assets.getSpring();
        activeTexture = Assets.getSpringAct();
    }

    @Override
    public void hitDoctor(Doctor doc){
        super.hitDoctor(doc);
        activate();
    }

    @Override
    public void activate(){
        keyFrame = activeTexture;
        this.doctor.jump(2f*doctor.JUMP_VELOCITY);
}

    public static float getRate(){
        return GENERATE_RATE;
    }

    @Override
    public boolean checkHitDoctor(Doctor doctor){
        if (doctor.isFalling() && doctor.getY() > this.getY()){
            return doctor.overlaps(this);
        }
        return false;
    }

}
