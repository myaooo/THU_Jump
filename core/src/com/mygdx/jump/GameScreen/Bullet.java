package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jump.GameScreen.Monster.Monster;
import com.mygdx.jump.Resource.Assets;

/**
 * A bullet class which is shoot by a doctor towards a monster
 * @author Yao
 * Created by Yao on 15/12/9.
 */
public class Bullet extends GameObject {
    // static fields
    // the width and height of a bullet
    public static final float WIDTH = 0.3f;
    public static final float HEIGHT = 0.3f;
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_HIT_MONSTER = 1;
    public static final int STATUS_MISSED = 2;
    public static final float BULLET_VELOCITY = 25;
    private Sound SHOOT = Gdx.audio.newSound(Gdx.files.internal("data/sound/shoot.mp3"));

    //private fields
    private TextureRegion keyFrame;
    private Monster target = null;

    // methods
    /**Constructor, a doctor's bullet shooting to an monster*/
    public Bullet(Doctor doctor, Monster monster){
        super(doctor.getX(Align.center),doctor.getY(Align.center),WIDTH,HEIGHT);
        target = monster;
        createBullet();
        // set bullet velocity
        float vx = monster.getX(Align.center) - this.getX(Align.center);
        float vy =  monster.getY(Align.center) - this.getY(Align.center);
        velocity.set(vx,vy);
        velocity.nor();
        velocity.scl(BULLET_VELOCITY);
    }

    /**Constructor a doctor's bullet shooting to the sky*/
    public Bullet(Doctor doctor){
        super(doctor.getX(Align.center),doctor.getY(Align.center),WIDTH,HEIGHT);
        createBullet();
        Assets.playSound(SHOOT);
        // set bullet velocity
        velocity.set(0,BULLET_VELOCITY);
    }

    /**Initialize function*/
    protected void createBullet(){
        stateTime = 0;
        status = STATUS_NORMAL;
        keyFrame = Assets.getBullet();
    }

    /**
     * Update Function, calls before draw
     */
    public void update(float deltaTime){
        // update position
        this.moveBy(velocity.x * deltaTime, velocity.y * deltaTime);
        if (getX() < 0 || getX() > GameStage.WORLD_WIDTH) status = STATUS_MISSED;
        this.checkHitMonster();
        stateTime += deltaTime;
    }

    /**
     * Return true if the bullet is still normal and needs to be rendered
     */
    public boolean isNormal(){
        return status == STATUS_NORMAL;
    }

    /**
     * Return true if the bullet has hit the monster
     */
    public boolean isHitMonster(){
        return status == STATUS_HIT_MONSTER;
    }

    /**
     * calls when the bullet has hit the monster, it sets the status of the monster into hit
     */
    public void checkHitMonster(){
        if (target == null)
            return;
        if (this.overlaps(target)) {
            status = STATUS_HIT_MONSTER;
            target.hitBullet();
        }
    }

    /**
     * override draw from Actor
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        // call draw function using batch
        batch.draw(keyFrame, getX(), getY(),    // position
                getOriginX(), getOriginY(), // rotate and scale center x,y
                getWidth(), getHeight(), // texture width and height
                getScaleX(), getScaleY(), getRotation());   // scale and rotation parameters

    }
}
