package com.mygdx.jump.GameScreen;

/**
 * Created by Yao on 15/12/11.
 */
public class Mediator {
    private int direction;
    private boolean shootBullet;

    public Mediator(){
        reset();
    }

    public void reset(){
        direction = 0;
        shootBullet = false;
    }

    public void doctorShoot(){
        shootBullet = true;
    }

    public boolean isShootBullet(){
        return shootBullet;
    }

    public int getDirection(){
        return direction;
    }

    public boolean isRight(){
        return 0 < direction;
    }

    public boolean isLeft(){
        return direction < 0;
    }

    public void setRight(){
        direction = 1;
    }

    public void setLeft(){
        direction = -1;
    }
}
