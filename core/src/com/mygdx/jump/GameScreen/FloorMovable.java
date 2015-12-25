package com.mygdx.jump.GameScreen;

import com.mygdx.jump.Resource.Assets;

/**
 * A movable floor
 * @author Yao
 * Created by Yao on 15/12/21.
 */
public class FloorMovable extends Floor {

    static public final int HORIZONTAL_MOVE = 0;
    static public final int VERTICAL_MOVE = 1;
    static public final float HALF_RANGE = 4;
    static public final float RATE_BASE = 0.05f;

    private float movelb;
    private float moveub;
    private int moveType;

    public FloorMovable(float x, float y){
        this(x,y,HORIZONTAL_MOVE);
    }

    public FloorMovable(float x, float y, int moveT){
        super(x, y);
        keyFrame= Assets.getFloorMov();
        switch(moveT){
            case VERTICAL_MOVE:
                moveType = VERTICAL_MOVE;
                velocity.set(0, FLOOR_VELOCITY);
                movelb = y-HALF_RANGE;
                moveub = y+HALF_RANGE;
                break;
            default:
                moveType = HORIZONTAL_MOVE;
                velocity.set(FLOOR_VELOCITY, 0);
                movelb = 0;
                moveub = GameStage.WORLD_WIDTH - FLOOR_WIDTH;
                break;
        }

    }

    @Override
    public void update(float deltaTime){
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);
        // check the position of the floor
        switch (moveType) {
            case VERTICAL_MOVE:
                if (this.getY() < movelb || this.getY() > moveub)
                    velocity.y = -velocity.y;
                break;
            default:
                if (this.getX() < movelb || this.getX() > moveub)
                    velocity.x = -velocity.x;
                break;
        }
        stateTime += deltaTime;
    }

}
