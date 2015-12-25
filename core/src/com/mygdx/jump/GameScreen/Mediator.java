package com.mygdx.jump.GameScreen;

/**
 * A mediator class which listen the application's input and transfer the params to game stage
 * @author Yao
 * Created by Yao on 15/12/11.
 */
public class Mediator {
    private int xDirection;
    private int yDirection;
    private boolean shootBullet;
    private boolean activateItem;

    public Mediator(){
        reset();
    }

    public void reset(){
        xDirection = 0;
        yDirection = 0;
        activateItem = false;
        shootBullet = false;
    }

    public void doctorShoot(){
        shootBullet = true;
    }

    public boolean isShootBullet(){
        return shootBullet;
    }

    public int getxDirection(){
        return xDirection;
    }

    public boolean isRight(){
        return 0 < xDirection;
    }

    public boolean isLeft(){
        return xDirection < 0;
    }

    public void setRight(){
        xDirection = 1;
    }

    public void setLeft(){
        xDirection = -1;
    }

    public void setUp(){
        yDirection = 1;
    }

    public void setDown(){
        yDirection = -1;
    }

    public void useItem(){
        activateItem = true;
    }

    public boolean isActivateItem(){
        return activateItem;
    }
}
