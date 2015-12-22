package com.mygdx.jump.GameScreen.Monster;

import com.mygdx.jump.GameScreen.Doctor;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/22.
 */
public class MonsterHole extends Monster {

    public MonsterHole(float x, float y){
        super(x,y);
        animation = Assets.getMonsterHole();
        health = 100000;
    }

    @Override
    public boolean isShootable(){
        return false;
    }

    @Override
    protected void hitDoctor(Doctor doctor){
        doctor.hitMonster();
        doctor.updateScale = -0.05f;
        doctor.updateRotate = 20;
    }
}
