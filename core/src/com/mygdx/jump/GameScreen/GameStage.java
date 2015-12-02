package com.mygdx.jump.GameScreen;

/**
 * Created by Yao on 15/12/2.
 */

import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.mygdx.jump.GameScreen.GameItem.Item;

// This is a class that contains all the objects in a game
public class GameStage extends Stage{

    // fields
    //private final ArrayList<Doctor> doodles = new ArrayList<>();
    private final Doctor doctor = new Doctor();
    private final ArrayList<Floor> floors = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Monster> monsters = new ArrayList<>();

    public GameStage(){

    }

    private void AddFloor(){

    }


}
