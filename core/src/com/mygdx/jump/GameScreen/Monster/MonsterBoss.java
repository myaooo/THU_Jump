package com.mygdx.jump.GameScreen.Monster;

import com.mygdx.jump.GameScreen.GameStage;
import com.mygdx.jump.Resource.Assets;

/**
 * Created by Yao on 15/12/22.
 */
public class MonsterBoss extends Monster {
    public static final float BOSS_WIDTH = 3;
    public static final float BOSS_HEIGHT = 3;

    public MonsterBoss(float x, float y){
        super(x,y);
        setHeight(BOSS_HEIGHT);
        setWidth(BOSS_WIDTH);
        animation = Assets.getMonsterBoss();
        health = 10;
        velocity.set(MOVE_SPEED*1.5f,0);
        moveUb = GameStage.WORLD_WIDTH-BOSS_WIDTH;
    }


}
