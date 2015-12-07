package com.mygdx.jump.GameScreen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A basic object class of game object, it extends Actor in libgdx. See https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/Actor.html
 * @author Ming Yao
 */

/*
 An actor has a position, rectangular size, origin, scale, rotation, Z index, and color.
 The position corresponds to the unrotated, unscaled bottom left corner of the actor.
 The position is relative to the actor's parent.
 The origin is relative to the position and is used for scale and rotation.
 */
public class GameObject extends Actor {
    // fields
    // The object's velocity and acceleration
    public Vector2 velocity;
    public Vector2 acceleration;
    // methods
    // constructors
    /** Default constructor */
    public GameObject() {
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
    }
    /** constrcutor, setting position to  (x,y), with a bounding rectangle with width and height*/
    public GameObject(float x, float y, float width, float height) {
        this.setBounds(x, y, width, height);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
    }

    /** get the bounding rectangle of the object*/
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /** set the Velocity to (x,y)*/
    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    /** set the Velocity to Vector2 v*/
    public void setVelocity(Vector2 v){ velocity = v;}

    /** set the Acceleration to (x,y) */
    public void setAcceleration(float x, float y) {
        acceleration.set(x, y);
    }

    /** set the Acceleration to Vector2 accel*/
    public void setAcceleration(Vector2 accel){ acceleration = accel;}

    /** get the object's velocity*/
    public Vector2 getVelocity() {
        return velocity;
    }

    /** get the acceleration of the object*/
    public Vector2 getAcceleration() {
        return acceleration;
    }
}
